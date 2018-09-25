package info.u_team.extreme_cobble_generator.proxy;

import info.u_team.extreme_cobble_generator.ExtremeCobbleGeneratorMod;
import info.u_team.extreme_cobble_generator.handler.ExtremeCobbleGeneratorGuiHandler;
import info.u_team.extreme_cobble_generator.init.*;
import info.u_team.extreme_cobble_generator.network.ExtremeCobbleGeneratorNetworkHandler;
import info.u_team.u_team_core.registry.CommonRegistry;
import net.minecraftforge.fml.common.event.*;

public class CommonProxy {
	
	public void preinit(FMLPreInitializationEvent event) {
		ExtremeCobbleGeneratorBlocks.preinit();
	}
	
	public void init(FMLInitializationEvent event) {
		ExtremeCobbleGeneratorCreativeTabs.init();
		ExtremeCobbleGeneratorNetworkHandler.init();
		CommonRegistry.registerGuiHandler(ExtremeCobbleGeneratorMod.getInstance(), new ExtremeCobbleGeneratorGuiHandler());
	}
	
	public void postinit(FMLPostInitializationEvent event) {
		
	}
	
}
