package info.u_team.extreme_cobble_generator.blockentity;

import info.u_team.extreme_cobble_generator.config.CommonConfig;
import info.u_team.extreme_cobble_generator.init.ExtremeCobbleGeneratorBlockEntityTypes;
import info.u_team.extreme_cobble_generator.menu.CobbleGeneratorMenu;
import info.u_team.u_team_core.api.block.MenuSyncedBlockEntity;
import info.u_team.u_team_core.api.sync.DataHolder;
import info.u_team.u_team_core.api.sync.MessageHolder;
import info.u_team.u_team_core.blockentity.UBlockEntity;
import info.u_team.u_team_core.energy.BasicEnergyAcceptorDelegate;
import info.u_team.u_team_core.energy.BasicEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

public class CobbleGeneratorBlockEntity extends UBlockEntity implements MenuSyncedBlockEntity {
	
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
	
	private final DataHolder energyHolder;
	private final DataHolder workingHolder;
	private final DataHolder amountHolder;
	private final MessageHolder amountUpdateMessage;
	
	// First tick
	
	private boolean first;
	
	public CobbleGeneratorBlockEntity(BlockPos pos, BlockState state) {
		super(ExtremeCobbleGeneratorBlockEntityTypes.GENERATOR.get(), pos, state);
		internalEnergyStorage = new BasicEnergyStorage(capacity, maxReceive, maxReceive, 0) {
			
			@Override
			public void onEnergyChanged() {
				setChanged();
			}
		};
		energyAcceptor = LazyOptional.of(() -> new BasicEnergyAcceptorDelegate(internalEnergyStorage));
		externalStorage = LazyOptional.empty();
		
		energyHolder = internalEnergyStorage.createSyncHandler();
		workingHolder = DataHolder.createBooleanHolder(() -> working, value -> working = value);
		amountHolder = DataHolder.createIntHolder(() -> amount, value -> amount = value);
		amountUpdateMessage = new MessageHolder(packet -> {
			amount = Math.max(0, Math.min(packet.readInt(), maxGeneration));
			setChanged();
		});
		
		first = true;
	}
	
	// Neighbor update
	
	public void neighborChanged() {
		updateConnections();
	}
	
	// First tick
	private void firstTick() {
		if (!level.isClientSide()) {
			updateConnections();
		}
	}
	
	// Update connections
	
	private void updateConnections() {
		powered = level.hasNeighborSignal(getBlockPos());
		final BlockEntity blockEntity = level.getBlockEntity(getBlockPos().above());
		if (blockEntity != null) {
			externalStorage = blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.DOWN);
		}
	}
	
	// Do logic
	
	public static void serverTick(Level level, BlockPos pos, BlockState state, CobbleGeneratorBlockEntity blockEntity) {
		if (blockEntity.first) {
			blockEntity.first = false;
			blockEntity.firstTick();
		}
		
		if (blockEntity.powered) {
			if (blockEntity.working) {
				blockEntity.working = false;
				blockEntity.sendChangesToClient();
			}
			return;
		}
		
		if (!blockEntity.externalStorage.isPresent()) {
			return;
		}
		
		if (blockEntity.amount == 0) {
			if (blockEntity.working) {
				blockEntity.working = false;
				blockEntity.sendChangesToClient();
			}
			return;
		}
		
		if (!blockEntity.working) {
			if (blockEntity.internalEnergyStorage.getEnergy() < blockEntity.costPerCobble * blockEntity.amount) {
				return;
			}
			blockEntity.working = true;
			blockEntity.sendChangesToClient();
		}
		
		if (blockEntity.internalEnergyStorage.getEnergy() < blockEntity.costPerCobble * blockEntity.amount) {
			blockEntity.working = false;
			blockEntity.sendChangesToClient();
			return;
		}
		
		final IItemHandler storage = blockEntity.externalStorage.orElseThrow(AssertionError::new);
		
		final int stacks = blockEntity.amount >> 6;
		final int rest = blockEntity.amount % 64;
		
		for (int i = 0; i < stacks; i++) {
			if (!blockEntity.addCobbleOutput(storage, 64)) {
				blockEntity.working = false;
				blockEntity.sendChangesToClient();
				return;
			}
		}
		
		if (rest > 0) {
			if (!blockEntity.addCobbleOutput(storage, rest)) {
				blockEntity.working = false;
				blockEntity.sendChangesToClient();
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
	
	// Nbt
	
	@Override
	public void saveNBT(CompoundTag tag) {
		tag.put("energy", internalEnergyStorage.serializeNBT());
		tag.putInt("amount", amount);
	}
	
	@Override
	public void loadNBT(CompoundTag tag) {
		internalEnergyStorage.deserializeNBT(tag.getCompound("energy"));
		amount = tag.getInt("amount");
	}
	
	// Capability
	
	@Override
	public void setRemoved() {
		super.setRemoved();
		energyAcceptor.invalidate();
	}
	
	@Override
	public void onChunkUnloaded() {
		super.onChunkUnloaded();
		energyAcceptor.invalidate();
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side) {
		if (capability == ForgeCapabilities.ENERGY) {
			return energyAcceptor.cast();
		}
		return super.getCapability(capability, side);
	}
	
	// Init synced
	
	@Override
	public void sendInitialMenuDataToClient(FriendlyByteBuf buffer) {
		buffer.writeInt(internalEnergyStorage.getEnergy());
		buffer.writeBoolean(working);
		buffer.writeInt(amount);
	}
	
	@Override
	public void handleInitialMenuDataFromServer(FriendlyByteBuf buffer) {
		internalEnergyStorage.setEnergy(buffer.readInt());
		working = buffer.readBoolean();
		amount = buffer.readInt();
	}
	
	// Synced working state
	
	@Override
	public void sendChunkLoadData(CompoundTag tag) {
		tag.putBoolean("working", working);
	}
	
	@Override
	public void handleChunkLoadData(CompoundTag tag) {
		working = tag.getBoolean("working");
	}
	
	@Override
	public void sendUpdateStateData(CompoundTag tag) {
		tag.putBoolean("working", working);
	}
	
	@Override
	public void handleUpdateStateData(CompoundTag tag) {
		working = tag.getBoolean("working");
	}
	
	// Container
	
	@Override
	public Component getDisplayName() {
		return Component.translatable("container.extremecobblegenerator.generator");
	}
	
	@Override
	public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
		return new CobbleGeneratorMenu(id, playerInventory, this);
	}
	
	// Message holder
	
	public DataHolder getEnergyHolder() {
		return energyHolder;
	}
	
	public DataHolder getWorkingHolder() {
		return workingHolder;
	}
	
	public DataHolder getAmountHolder() {
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
