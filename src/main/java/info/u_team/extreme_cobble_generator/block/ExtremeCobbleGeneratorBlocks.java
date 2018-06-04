package info.u_team.extreme_cobble_generator.block;

import static info.u_team.extreme_cobble_generator.tab.ExtremeCobbleGeneratorTabs.tab;

import info.u_team.extreme_cobble_generator.tileentity.ExtremeCobbleGeneratorTileEntities;
import info.u_team.u_team_core.block.UBlockTileEntity;

public class ExtremeCobbleGeneratorBlocks {
	
	public static UBlockTileEntity cobblegenerator;
	
	public ExtremeCobbleGeneratorBlocks() {
		cobblegenerator = new BlockCobbleGenerator("cobblegenerator", tab, ExtremeCobbleGeneratorTileEntities.cobblegenerator);
	}
	
}
