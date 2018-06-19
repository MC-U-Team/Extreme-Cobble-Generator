package info.u_team.extreme_cobble_generator.energy;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.EnergyStorage;

public class Energy extends EnergyStorage {
	
	public Energy(int capacity, int maxReceive, int maxExtract, int energy) {
		super(capacity, maxReceive, maxExtract, energy);
	}
	
	public void writeToNBT(NBTTagCompound compound) {
		compound.setInteger("energy", energy);
	}
	
	public void readFromNBT(NBTTagCompound compound) {
		energy = compound.getInteger("energy");
	}
	
}
