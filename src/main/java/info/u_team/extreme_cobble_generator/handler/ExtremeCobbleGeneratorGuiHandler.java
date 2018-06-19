package info.u_team.extreme_cobble_generator.handler;

import info.u_team.extreme_cobble_generator.container.ContainerCobbleGenerator;
import info.u_team.extreme_cobble_generator.gui.GuiCobbleGenerator;
import info.u_team.extreme_cobble_generator.tileentity.TileEntityCobbleGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class ExtremeCobbleGeneratorGuiHandler implements IGuiHandler {
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == 0) {
			TileEntity tileentity = world.getTileEntity(new BlockPos(x, y, z));
			if (tileentity instanceof TileEntityCobbleGenerator) {
				return new ContainerCobbleGenerator(player.inventory, (TileEntityCobbleGenerator) tileentity);
			}
		}
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == 0) {
			TileEntity tileentity = world.getTileEntity(new BlockPos(x, y, z));
			if (tileentity instanceof TileEntityCobbleGenerator) {
				return new GuiCobbleGenerator(player.inventory, (TileEntityCobbleGenerator) tileentity);
			}
		}
		return null;
	}
	
}
