package info.u_team.extreme_cobble_generator.proxy;

import info.u_team.extreme_cobble_generator.block.ExtremeCobbleGeneratorBlocks;
import info.u_team.extreme_cobble_generator.tab.ExtremeCobbleGeneratorTabs;
import info.u_team.extreme_cobble_generator.tileentity.ExtremeCobbleGeneratorTileEntities;
import net.minecraftforge.fml.common.event.*;

public class CommonProxy {
	
	public void preinit(FMLPreInitializationEvent event) {
		new ExtremeCobbleGeneratorTabs();
		new ExtremeCobbleGeneratorTileEntities();
		new ExtremeCobbleGeneratorBlocks();
	}
	
	public void init(FMLInitializationEvent event) {
		
	}
	
	public void postinit(FMLPostInitializationEvent event) {
		
	}
	
}
