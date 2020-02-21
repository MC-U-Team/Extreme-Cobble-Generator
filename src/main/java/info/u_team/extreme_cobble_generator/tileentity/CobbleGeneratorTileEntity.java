package info.u_team.extreme_cobble_generator.tileentity;

import info.u_team.extreme_cobble_generator.config.CommonConfig;
import info.u_team.extreme_cobble_generator.init.ExtremeCobbleGeneratorTileEntityTypes;
import info.u_team.u_team_core.energy.*;
import info.u_team.u_team_core.tileentity.UTickableTileEntity;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.*;

public class CobbleGeneratorTileEntity extends UTickableTileEntity {
	
	private final int capacity = CommonConfig.getInstance().capacity.get();
	private final int maxReceive = CommonConfig.getInstance().capacity.get();
	
	private final int costPerCobble = CommonConfig.getInstance().costPerCobble.get();
	private final int maxGeneration = CommonConfig.getInstance().maxGeneration.get();
	
	protected final BasicEnergyStorage internalEnergyStorage;
	
	protected final LazyOptional<BasicEnergyAcceptorDelegate> energyAcceptor;
	
	private int amount;
	
	private LazyOptional<IItemHandler> externalStorage;
	
	private boolean powered;
	private boolean working;
	
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
	
	// Update
	@Override
	public void tickServer() {
		if (powered) {
			working = false;
			return;
		}
		
		if (!externalStorage.isPresent()) {
			return;
		}
		
		if (amount == 0) {
			working = false;
			return;
		}
		
		if (!working) {
			if (internalEnergyStorage.getEnergy() < costPerCobble * amount) {
				return;
			}
			working = true;
		}
		
		if (internalEnergyStorage.getEnergy() < costPerCobble * amount) {
			working = false;
			return;
		}
		
		final IItemHandler storage = externalStorage.orElseThrow(AssertionError::new);
		
		final int stacks = amount >> 6;
		final int rest = amount % 64;
		
		for (int i = 0; i < stacks; i++) {
			if (!addCobbleOutput(storage, 64)) {
				working = false;
				return;
			}
		}
		
		if (rest > 0) {
			if (!addCobbleOutput(storage, rest)) {
				working = false;
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
		return super.getCapability(capability);
	}
}
