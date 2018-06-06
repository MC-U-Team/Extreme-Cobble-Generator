package info.u_team.extreme_cobble_generator.container;

import info.u_team.extreme_cobble_generator.tileentity.TileEntityCobbleGenerator;
import info.u_team.u_team_core.container.UContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class ContainerCobbleGenerator extends UContainer {
	
	public ContainerCobbleGenerator(InventoryPlayer inventory, TileEntityCobbleGenerator generator) {
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		
		for (int k = 0; k < 9; ++k) {
			this.addSlotToContainer(new Slot(inventory, k, 8 + k * 18, 142));
		}
	}
	
}
