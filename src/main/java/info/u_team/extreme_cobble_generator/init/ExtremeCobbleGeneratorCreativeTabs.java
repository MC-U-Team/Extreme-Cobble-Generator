package info.u_team.extreme_cobble_generator.init;

import info.u_team.extreme_cobble_generator.ExtremeCobbleGeneratorConstants;
import info.u_team.u_team_core.creativetab.UCreativeTab;

public class ExtremeCobbleGeneratorCreativeTabs {
	
	public static final UCreativeTab tab = new UCreativeTab(ExtremeCobbleGeneratorConstants.MODID, "tab");
	
	public static void init() {
		tab.setIcon(ExtremeCobbleGeneratorBlocks.cobblegenerator);
	}
	
}
