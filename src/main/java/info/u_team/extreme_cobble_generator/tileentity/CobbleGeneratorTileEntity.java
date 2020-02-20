package info.u_team.extreme_cobble_generator.tileentity;

import info.u_team.extreme_cobble_generator.energy.Energy;
import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetworkManager;
import net.minecraft.tileentity.*;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.*;

public class CobbleGeneratorTileEntity extends UTileEntity implements ITickableTileEntity {
	
	private Energy energy;
	
	private boolean working;
	
	private int maxamount = 100000;
	private int multiplier = 4;
	
	private int amount;
	
	public CobbleGeneratorTileEntity() {
		energy = new Energy(1000000, maxamount * multiplier, maxamount * multiplier, 0);
		working = false;
		amount = 1;
	}
	
	// Update
	@Override
	public void tick() {
		if (world == null || world.isRemote) {
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
		energy.extractEnergy((size - errorstack.getCount()) * multiplier, false);
		if (!errorstack.isEmpty()) {
			return false;
		}
		return true;
	}
	
	// Server side update
	private void markUpdate() {
		world.markBlockRangeForRenderUpdate(pos, pos);
		world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
		world.scheduleBlockUpdate(pos, this.getBlockType(), 0, 0);
		markDirty();
	}
	
	// NBT
	
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
		amount = compound.getInteger("Amount");
		working = compound.getBoolean("Working");
	}
	
	public void writeNBT(NBTTagCompound compound) {
		energy.writeToNBT(compound);
		compound.setInteger("Amount", amount);
		compound.setBoolean("Working", working);
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
	
	// Sync
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.pos, 3, this.getUpdateTag());
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		handleUpdateTag(pkt.getNbtCompound());
	}
	
	// Getter
	
	public Energy getEnergy() {
		return energy;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public int getMaxAmount() {
		return maxamount;
	}
	
	public boolean isWorking() {
		return working;
	}
	
	// Setter (Server side only!)
	
	public void setAmount(int amount) {
		this.amount = amount;
		markUpdate();
	}
	
}
