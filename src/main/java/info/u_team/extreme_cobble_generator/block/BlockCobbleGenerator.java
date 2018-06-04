package info.u_team.extreme_cobble_generator.block;

import info.u_team.u_team_core.block.UBlockTileEntity;
import info.u_team.u_team_core.creativetab.UCreativeTab;
import info.u_team.u_team_core.tileentity.UTileEntityProvider;
import net.minecraft.block.material.Material;

public class BlockCobbleGenerator extends UBlockTileEntity {
	
	public BlockCobbleGenerator(String name, UCreativeTab tab, UTileEntityProvider provider) {
		super(name, Material.IRON, tab, provider);
	}
	
}
