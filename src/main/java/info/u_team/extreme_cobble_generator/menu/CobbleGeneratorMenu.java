package info.u_team.extreme_cobble_generator.menu;

import info.u_team.extreme_cobble_generator.blockentity.CobbleGeneratorBlockEntity;
import info.u_team.extreme_cobble_generator.init.ExtremeCobbleGeneratorContainerTypes;
import info.u_team.u_team_core.menu.UBlockEntityContainerMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.LogicalSide;

public class CobbleGeneratorMenu extends UBlockEntityContainerMenu<CobbleGeneratorBlockEntity> {
	
	// Client
	public CobbleGeneratorMenu(int id, Inventory playerInventory, FriendlyByteBuf buffer) {
		super(ExtremeCobbleGeneratorContainerTypes.GENERATOR.get(), id, playerInventory, buffer);
	}
	
	// Server
	public CobbleGeneratorMenu(int id, Inventory playerInventory, CobbleGeneratorBlockEntity tileEntity) {
		super(ExtremeCobbleGeneratorContainerTypes.GENERATOR.get(), id, playerInventory, tileEntity);
	}
	
	@Override
	protected void init(LogicalSide side) {
		addPlayerInventory(playerInventory, 8, 91);
		addDataHolderToClient(blockEntity.getEnergyHolder());
		addDataHolderToClient(blockEntity.getWorkingHolder());
		addDataHolderToClient(blockEntity.getAmountHolder());
		addDataHolderToServer(blockEntity.getAmountUpdateMessage());
	}
	
	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		ItemStack newstack = ItemStack.EMPTY;
		final Slot slot = slots.get(index);
		
		if (slot != null && slot.hasItem()) {
			final ItemStack oldstack = slot.getItem();
			newstack = oldstack.copy();
			
			if (index >= 0 && index < 27) {
				if (!moveItemStackTo(oldstack, 27, 36, false)) {
					return ItemStack.EMPTY;
				}
			} else if (index >= 27 && index < 36 && !moveItemStackTo(oldstack, 0, 27, false)) {
				return ItemStack.EMPTY;
			}
			
			if (oldstack.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
			
			if (oldstack.getCount() == newstack.getCount()) {
				return ItemStack.EMPTY;
			}
			slot.onTake(player, oldstack);
		}
		return newstack;
	}
}
