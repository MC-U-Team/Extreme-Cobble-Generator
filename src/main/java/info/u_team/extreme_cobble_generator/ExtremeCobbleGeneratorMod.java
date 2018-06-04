package info.u_team.extreme_cobble_generator;

import static info.u_team.extreme_cobble_generator.ExtremeCobbleGeneratorConstants.*;

import info.u_team.extreme_cobble_generator.proxy.CommonProxy;
import info.u_team.u_team_core.sub.USubMod;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.Mod.*;
import net.minecraftforge.fml.common.event.*;

@Mod(modid = MODID, name = NAME, version = VERSION, acceptedMinecraftVersions = MCVERSION, dependencies = DEPENDENCIES)
public class ExtremeCobbleGeneratorMod extends USubMod {
	
	public ExtremeCobbleGeneratorMod() {
		super(MODID, NAME, VERSION, UPDATEURL);
	}
	
	@Instance
	private static ExtremeCobbleGeneratorMod instance;
	
	@SidedProxy(serverSide = COMMONPROXY, clientSide = CLIENTPROXY)
	private static CommonProxy proxy;
	
	public static ExtremeCobbleGeneratorMod getInstance() {
		return instance;
	}
	
	public CommonProxy getProxy() {
		return proxy;
	}
	
	@EventHandler
	public void preinit(FMLPreInitializationEvent event) {
		super.preinit(event);
		proxy.preinit(event);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		super.init(event);
		proxy.init(event);
	}
	
	@EventHandler
	public void postinit(FMLPostInitializationEvent event) {
		super.postinit(event);
		proxy.postinit(event);
	}
}
