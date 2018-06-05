package info.u_team.extreme_cobble_generator.tileentity;

import info.u_team.extreme_cobble_generator.energy.Energy;
import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.*;

public class TileEntityCobbleGenerator extends UTileEntity implements ITickable {
	
	private Energy energy;
	
	private boolean working;
	
	private int amount;
	
	public TileEntityCobbleGenerator() {
		energy = new Energy(500000, 50000, 50000, 0);
		working = false;
		amount = 512;
	}
	
	// Update
	@Override
	public void update() {
		if (world == null || world.isRemote) {
			return;
		}
		int workingenergy = amount;
		
		if (working) {
			int extract = energy.extractEnergy(workingenergy, true);
			if (extract < workingenergy) {
				working = false;
				return;
			}
			generateCobble();
		} else {
			if (energy.getEnergyStored() >= workingenergy) {
				working = true;
			}
		}
	}
	
	// NBT
	
	private void generateCobble() {
		TileEntity tileentity = world.getTileEntity(getPos().up());
		if (tileentity == null || !tileentity.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP)) {
			working = false;
			return;
		}
		IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
		
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
		energy.extractEnergy(size - errorstack.getCount(), false);
		if (!errorstack.isEmpty()) {
			return false;
		}
		return true;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		writeNBT(compound);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		readNBT(compound);
	}
	
	public void readNBT(NBTTagCompound compound) {
		energy.readFromNBT(compound);
	}
	
	public void writeNBT(NBTTagCompound compound) {
		energy.writeToNBT(compound);
	}
	
	// Capabilites
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityEnergy.ENERGY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityEnergy.ENERGY) {
			return CapabilityEnergy.ENERGY.cast(energy);
		}
		return super.getCapability(capability, facing);
	}
	
}
