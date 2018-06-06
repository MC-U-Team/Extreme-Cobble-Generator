package info.u_team.extreme_cobble_generator.proxy;

import info.u_team.extreme_cobble_generator.ExtremeCobbleGeneratorMod;
import info.u_team.extreme_cobble_generator.block.ExtremeCobbleGeneratorBlocks;
import info.u_team.extreme_cobble_generator.handler.ExtremeCobbleGeneratorGuiHandler;
import info.u_team.extreme_cobble_generator.network.ExtremeCobbleGeneratorNetworkHandler;
import info.u_team.extreme_cobble_generator.tab.ExtremeCobbleGeneratorTabs;
import info.u_team.extreme_cobble_generator.tileentity.ExtremeCobbleGeneratorTileEntities;
import info.u_team.u_team_core.util.registry.CommonRegistry;
import net.minecraftforge.fml.common.event.*;

public class CommonProxy {
	
	public void preinit(FMLPreInitializationEvent event) {
		new ExtremeCobbleGeneratorTabs();
		new ExtremeCobbleGeneratorTileEntities();
		new ExtremeCobbleGeneratorBlocks();
		new ExtremeCobbleGeneratorNetworkHandler();
	}
	
	public void init(FMLInitializationEvent event) {
		CommonRegistry.registerGuiHandler(ExtremeCobbleGeneratorMod.getInstance(), new ExtremeCobbleGeneratorGuiHandler());
	}
	
	public void postinit(FMLPostInitializationEvent event) {
		
	}
	
}
