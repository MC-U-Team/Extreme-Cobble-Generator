package info.u_team.extreme_cobble_generator.tileentity;

import info.u_team.extreme_cobble_generator.config.CommonConfig;
import info.u_team.extreme_cobble_generator.init.ExtremeCobbleGeneratorTileEntityTypes;
import info.u_team.u_team_core.energy.BasicEnergyStorage;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.*;

public class CobbleGeneratorTileEntity extends TickableTileEntity {
	
	private final int capacity = CommonConfig.getInstance().capacity.get();
	private final int maxReceive = CommonConfig.getInstance().capacity.get();
	
	private final int costPerCobble = CommonConfig.getInstance().costPerCobble.get();
	private final int maxGeneration = CommonConfig.getInstance().maxGeneration.get();
	
	protected final BasicEnergyStorage internalEnergyStorage;
	
	protected final LazyOptional<BasicEnergyStorage> internalEnergyStorageOptional;
	
	private LazyOptional<IItemHandler> externalStorage;
	
	private boolean powered;
	
	private boolean working;
	
	private int amount;
	
	public CobbleGeneratorTileEntity() {
		super(ExtremeCobbleGeneratorTileEntityTypes.GENERATOR);
		internalEnergyStorage = new BasicEnergyStorage(capacity, maxReceive, 0, 0);
		internalEnergyStorageOptional = LazyOptional.of(() -> internalEnergyStorage);
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
	}
	
	@Override
	public void readNBT(CompoundNBT compound) {
		internalEnergyStorage.deserializeNBT(compound.getCompound("energy"));
	}
	
	@Override
	public void remove() {
		super.remove();
		internalEnergyStorageOptional.invalidate();
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side) {
		if (capability == CapabilityEnergy.ENERGY) {
			return internalEnergyStorageOptional.cast();
		}
		return super.getCapability(capability);
	}
}
