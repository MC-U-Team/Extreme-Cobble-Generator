package info.u_team.extreme_cobble_generator.tileentity;

import info.u_team.extreme_cobble_generator.config.CommonConfig;
import info.u_team.extreme_cobble_generator.container.CobbleGeneratorContainer;
import info.u_team.extreme_cobble_generator.init.ExtremeCobbleGeneratorTileEntityTypes;
import info.u_team.u_team_core.api.sync.*;
import info.u_team.u_team_core.energy.*;
import info.u_team.u_team_core.tileentity.UTickableTileEntity;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.*;

public class CobbleGeneratorTileEntity extends UTickableTileEntity implements IInitSyncedTileEntity {
	
	// Config values
	
	public final int capacity = CommonConfig.getInstance().capacity.get();
	public final int maxReceive = CommonConfig.getInstance().capacity.get();
	
	public final int costPerCobble = CommonConfig.getInstance().costPerCobble.get();
	public final int maxGeneration = CommonConfig.getInstance().maxGeneration.get();
	
	// Energy
	
	protected final BasicEnergyStorage internalEnergyStorage;
	
	protected final LazyOptional<BasicEnergyAcceptorDelegate> energyAcceptor;
	
	// Amount
	
	private int amount;
	
	// Cached external storage
	
	private LazyOptional<IItemHandler> externalStorage;
	
	// Working state
	
	private boolean powered;
	private boolean working;
	
	// Sync for container
	
	private final BufferReferenceHolder energyHolder;
	private final BufferReferenceHolder workingHolder;
	private final BufferReferenceHolder amountHolder;
	private final MessageHolder amountUpdateMessage;
	
	public CobbleGeneratorTileEntity() {
		super(ExtremeCobbleGeneratorTileEntityTypes.GENERATOR);
		internalEnergyStorage = new BasicEnergyStorage(capacity, maxReceive, maxReceive, 0) {
			
			@Override
			public void onEnergyChanged() {
				markDirty();
			}
		};
		energyAcceptor = LazyOptional.of(() -> new BasicEnergyAcceptorDelegate(internalEnergyStorage));
		externalStorage = LazyOptional.empty();
		
		energyHolder = internalEnergyStorage.createSyncHandler();
		workingHolder = BufferReferenceHolder.createBooleanHolder(() -> working, value -> working = value);
		amountHolder = BufferReferenceHolder.createIntHolder(() -> amount, value -> amount = value);
		amountUpdateMessage = new MessageHolder(packet -> {
			amount = Math.max(0, Math.min(packet.readInt(), maxGeneration));
			markDirty();
		});
	}
	
	// Neighbor update
	
	public void neighborChanged() {
		updateConnections();
	}
	
	// First tick
	
	@Override
	protected void firstTick() {
		if (!world.isRemote()) {
			updateConnections();
		}
	}
	
	// Update connections
	
	private void updateConnections() {
		powered = world.isBlockPowered(pos);
		final TileEntity tileEntity = world.getTileEntity(getPos().up());
		if (tileEntity != null) {
			externalStorage = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.DOWN);
		}
	}
	
	// Do logic
	
	@Override
	public void tickServer() {
		if (powered) {
			if (working) {
				working = false;
				sendChangesToClient();
			}
			return;
		}
		
		if (!externalStorage.isPresent()) {
			return;
		}
		
		if (amount == 0) {
			if (working) {
				working = false;
				sendChangesToClient();
			}
			return;
		}
		
		if (!working) {
			if (internalEnergyStorage.getEnergy() < costPerCobble * amount) {
				return;
			}
			working = true;
			sendChangesToClient();
		}
		
		if (internalEnergyStorage.getEnergy() < costPerCobble * amount) {
			working = false;
			sendChangesToClient();
			return;
		}
		
		final IItemHandler storage = externalStorage.orElseThrow(AssertionError::new);
		
		final int stacks = amount >> 6;
		final int rest = amount % 64;
		
		for (int i = 0; i < stacks; i++) {
			if (!addCobbleOutput(storage, 64)) {
				working = false;
				sendChangesToClient();
				return;
			}
		}
		
		if (rest > 0) {
			if (!addCobbleOutput(storage, rest)) {
				working = false;
				sendChangesToClient();
				return;
			}
		}
	}
	
	private boolean addCobbleOutput(IItemHandler handler, int count) {
		final ItemStack itemLeft = ItemHandlerHelper.insertItem(handler, new ItemStack(Blocks.COBBLESTONE, count), false);
		internalEnergyStorage.addEnergy(-(count - itemLeft.getCount()) * costPerCobble);
		if (!itemLeft.isEmpty()) {
			return false;
		}
		return true;
	}
	
	// Server side update
	// private void markUpdate() {
	// // world.markBlockRangeForRenderUpdate(pos, pos);
	// world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
	// // world.scheduleBlockUpdate(pos, this.getBlockType(), 0, 0);
	// markDirty();
	// }
	
	// Nbt
	
	@Override
	public void writeNBT(CompoundNBT compound) {
		compound.put("energy", internalEnergyStorage.serializeNBT());
		compound.putInt("amount", amount);
	}
	
	@Override
	public void readNBT(CompoundNBT compound) {
		internalEnergyStorage.deserializeNBT(compound.getCompound("energy"));
		amount = compound.getInt("amount");
	}
	
	// Capability
	
	@Override
	public void remove() {
		super.remove();
		energyAcceptor.invalidate();
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side) {
		if (capability == CapabilityEnergy.ENERGY) {
			return energyAcceptor.cast();
		}
		return super.getCapability(capability, side);
	}
	
	// Init synced
	
	@Override
	public void sendInitialDataBuffer(PacketBuffer buffer) {
		buffer.writeInt(internalEnergyStorage.getEnergy());
		buffer.writeBoolean(working);
		buffer.writeInt(amount);
	}
	
	@Override
	public void handleInitialDataBuffer(PacketBuffer buffer) {
		internalEnergyStorage.setEnergy(buffer.readInt());
		working = buffer.readBoolean();
		amount = buffer.readInt();
	}
	
	// Synced working state
	
	@Override
	public void sendUpdateStateData(CompoundNBT compound) {
		compound.putBoolean("working", working);
	}
	
	@Override
	public void handleUpdateStateData(CompoundNBT compound) {
		working = compound.getBoolean("working");
	}
	
	// Container
	
	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("container.extremecobblegenerator.generator");
	}
	
	@Override
	public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
		return new CobbleGeneratorContainer(id, playerInventory, this);
	}
	
	// Message holder
	
	public BufferReferenceHolder getEnergyHolder() {
		return energyHolder;
	}
	
	public BufferReferenceHolder getWorkingHolder() {
		return workingHolder;
	}
	
	public BufferReferenceHolder getAmountHolder() {
		return amountHolder;
	}
	
	public MessageHolder getAmountUpdateMessage() {
		return amountUpdateMessage;
	}
	
	// Getter
	
	public BasicEnergyStorage getInternalEnergyStorage() {
		return internalEnergyStorage;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public boolean isWorking() {
		return working;
	}
	
	// Setter
	
	// Client side setter
	
	@OnlyIn(Dist.CLIENT)
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
}
