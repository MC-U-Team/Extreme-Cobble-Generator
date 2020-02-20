package info.u_team.extreme_cobble_generator.container;

import info.u_team.extreme_cobble_generator.init.ExtremeCobbleGeneratorContainerTypes;
import info.u_team.extreme_cobble_generator.tileentity.CobbleGeneratorTileEntity;
import info.u_team.u_team_core.container.UTileEntityContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public class CobbleGeneratorContainer extends UTileEntityContainer<CobbleGeneratorTileEntity> {

	// Client
	public CobbleGeneratorContainer(int id, PlayerInventory playerInventory, PacketBuffer buffer) {
		super(ExtremeCobbleGeneratorContainerTypes.GENERATOR, id, playerInventory, buffer);
	}
	
	// Server
	public CobbleGeneratorContainer(int id, PlayerInventory playerInventory, CobbleGeneratorTileEntity tileEntity) {
		super(ExtremeCobbleGeneratorContainerTypes.GENERATOR,id, playerInventory, tileEntity);
	}
	
	@Override
	protected void init(boolean server) {
	}

	
	
	
//	public ContainerCobbleGenerator(InventoryPlayer inventory, CobbleGeneratorTileEntity generator) {
//		for (int i = 0; i < 3; ++i) {
//			for (int j = 0; j < 9; ++j) {
//				this.addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
//			}
//		}
//		
//		for (int k = 0; k < 9; ++k) {
//			this.addSlotToContainer(new Slot(inventory, k, 8 + k * 18, 142));
//		}
//	}
	
//	@Override
//	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
//		ItemStack newstack = ItemStack.EMPTY;
//		Slot slot = this.inventorySlots.get(index);
//		
//		if (slot != null && slot.getHasStack()) {
//			ItemStack oldstack = slot.getStack();
//			newstack = oldstack.copy();
//			
//			if (index >= 0 && index < 27) {
//				if (!this.mergeItemStack(oldstack, 27, 36, false)) {
//					return ItemStack.EMPTY;
//				}
//			} else if (index >= 27 && index < 36 && !this.mergeItemStack(oldstack, 0, 27, false)) {
//				return ItemStack.EMPTY;
//			}
//			
//			if (oldstack.isEmpty()) {
//				slot.putStack(ItemStack.EMPTY);
//			} else {
//				slot.onSlotChanged();
//			}
//			
//			if (oldstack.getCount() == newstack.getCount()) {
//				return ItemStack.EMPTY;
//			}
//			slot.onTake(player, oldstack);
//		}
//		return newstack;
//	}
}
