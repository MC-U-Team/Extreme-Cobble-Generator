package info.u_team.extreme_cobble_generator;

import info.u_team.extreme_cobble_generator.config.CommonConfig;
import info.u_team.extreme_cobble_generator.init.ExtremeCobbleGeneratorBlocks;
import info.u_team.u_team_core.util.registry.BusRegister;
import info.u_team.u_team_core.util.verify.JarSignVerifier;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;

@Mod(ExtremeCobbleGeneratorMod.MODID)
public class ExtremeCobbleGeneratorMod {
	
	public static final String MODID = "extremecobblegenerator";
	
	public ExtremeCobbleGeneratorMod() {
		JarSignVerifier.checkSigned(MODID);
		ModLoadingContext.get().registerConfig(Type.COMMON, CommonConfig.CONFIG);
		register();
	}
	
	private void register() {
		BusRegister.registerMod(ExtremeCobbleGeneratorBlocks::register);
		BusRegister.registerMod(ExtremeCobbleGeneratorContainerTypes::register);
	}
}
