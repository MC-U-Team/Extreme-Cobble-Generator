package info.u_team.extreme_cobble_generator.tileentity;

import info.u_team.extreme_cobble_generator.init.ExtremeCobbleGeneratorTileEntityTypes;
import info.u_team.u_team_core.energy.BasicEnergyStorage;
import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.*;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.*;

public class CobbleGeneratorTileEntity extends UTileEntity implements ITickableTileEntity {
	
	protected final BasicEnergyStorage internalEnergyStorage;
	
	protected final LazyOptional<BasicEnergyStorage> internalEnergyStorageOptional;
	
	private boolean powered;
	
	private boolean working;
	
	private int maxamount = 100000;
	private int multiplier = 4;
	
	private int amount;
	
	public CobbleGeneratorTileEntity() {
		super(ExtremeCobbleGeneratorTileEntityTypes.GENERATOR);
		internalEnergyStorage = new BasicEnergyStorage(1000000, maxamount * multiplier, maxamount * multiplier, 0);
		internalEnergyStorageOptional = LazyOptional.of(() -> internalEnergyStorage);
		working = false;
		amount = 1;
	}
	
	// Neighbor update
	public void neighborChanged() {
		powered = world.isBlockPowered(pos);
	}
	
	// Update
	@Override
	public void tick() {
		if (world.isRemote()) {
			return;
		}
		
		if (world.isBlockPowered(pos) && working) {
			working = false;
			markUpdate();
		}
		
		if (!world.isBlockPowered(pos)) {
			if (!working) {
				if (energy.getEnergyStored() >= amount * multiplier) {
					working = true;
					markUpdate();
				}
			}
			if (working) {
				int extract = energy.extractEnergy(amount * multiplier, true);
				if (extract < amount * multiplier) {
					working = false;
					return;
				}
				generateCobble();
				markUpdate();
			}
		}
		
	}
	
	private void generateCobble() {
		TileEntity tileentity = world.getTileEntity(getPos().up());
		
		LazyOptional<IItemHandler> capability = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.UP);
		
		if (tileentity == null || !capability.isPresent()) {
			working = false;
			return;
		}
		
		IItemHandler handler = capability.orElse(null);
		int stacks = amount >> 6;
		int rest = amount % 64;
		
		for (int i = 0; i < stacks; i++) {
			if (!addCobbleOutput(handler, 64)) {
				working = false;
				return;
			}
		}
		
		if (rest > 0) {
			if (!addCobbleOutput(handler, rest)) {
				working = false;
				return;
			}
		}
	}
	
	private boolean addCobbleOutput(IItemHandler handler, int size) {
		ItemStack errorstack = ItemHandlerHelper.insertItem(handler, new ItemStack(Blocks.COBBLESTONE, size), false);
		energy.extractEnergy((size - errorstack.getCount()) * multiplier, false);
		if (!errorstack.isEmpty()) {
			return false;
		}
		return true;
	}
	
	// Server side update
	private void markUpdate() {
		// world.markBlockRangeForRenderUpdate(pos, pos);
		world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
		// world.scheduleBlockUpdate(pos, this.getBlockType(), 0, 0);
		markDirty();
	}
	
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
