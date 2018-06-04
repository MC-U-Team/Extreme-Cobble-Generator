package info.u_team.extreme_cobble_generator.proxy;

import info.u_team.extreme_cobble_generator.render.ExtremeCobbleGeneratorModelRegistry;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.relauncher.Side;

import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
	
	public void preinit(FMLPreInitializationEvent event) {
		super.preinit(event);
		new ExtremeCobbleGeneratorModelRegistry();
	}
	
	public void init(FMLInitializationEvent event) {
		super.init(event);
	}
	
	public void postinit(FMLPostInitializationEvent event) {
		super.postinit(event);
	}
	
}
