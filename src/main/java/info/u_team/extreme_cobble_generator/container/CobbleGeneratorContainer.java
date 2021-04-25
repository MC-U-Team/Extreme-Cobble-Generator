package info.u_team.extreme_cobble_generator.container;

import info.u_team.extreme_cobble_generator.init.ExtremeCobbleGeneratorContainerTypes;
import info.u_team.extreme_cobble_generator.tileentity.CobbleGeneratorTileEntity;
import info.u_team.u_team_core.container.UTileEntityContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

public class CobbleGeneratorContainer extends UTileEntityContainer<CobbleGeneratorTileEntity> {
	
	// Client
	public CobbleGeneratorContainer(int id, PlayerInventory playerInventory, PacketBuffer buffer) {
		super(ExtremeCobbleGeneratorContainerTypes.GENERATOR.get(), id, playerInventory, buffer);
	}
	
	// Server
	public CobbleGeneratorContainer(int id, PlayerInventory playerInventory, CobbleGeneratorTileEntity tileEntity) {
		super(ExtremeCobbleGeneratorContainerTypes.GENERATOR.get(), id, playerInventory, tileEntity);
	}
	
	@Override
	protected void init(boolean server) {
		appendPlayerInventory(playerInventory, 8, 91);
		addServerToClientTracker(tileEntity.getEnergyHolder());
		addServerToClientTracker(tileEntity.getWorkingHolder());
		addServerToClientTracker(tileEntity.getAmountHolder());
		addClientToServerTracker(tileEntity.getAmountUpdateMessage());
	}
	
	@Override
	public ItemStack transferStackInSlot(PlayerEntity player, int index) {
		ItemStack newstack = ItemStack.EMPTY;
		final Slot slot = this.inventorySlots.get(index);
		
		if (slot != null && slot.getHasStack()) {
			final ItemStack oldstack = slot.getStack();
			newstack = oldstack.copy();
			
			if (index >= 0 && index < 27) {
				if (!this.mergeItemStack(oldstack, 27, 36, false)) {
					return ItemStack.EMPTY;
				}
			} else if (index >= 27 && index < 36 && !this.mergeItemStack(oldstack, 0, 27, false)) {
				return ItemStack.EMPTY;
			}
			
			if (oldstack.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
			
			if (oldstack.getCount() == newstack.getCount()) {
				return ItemStack.EMPTY;
			}
			slot.onTake(player, oldstack);
		}
		return newstack;
	}
}
