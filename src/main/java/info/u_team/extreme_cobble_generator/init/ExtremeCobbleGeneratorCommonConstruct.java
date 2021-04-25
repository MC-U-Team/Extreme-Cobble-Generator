package info.u_team.extreme_cobble_generator.init;

import info.u_team.extreme_cobble_generator.ExtremeCobbleGeneratorMod;
import info.u_team.extreme_cobble_generator.config.CommonConfig;
import info.u_team.u_team_core.api.construct.Construct;
import info.u_team.u_team_core.api.construct.IModConstruct;
import info.u_team.u_team_core.util.registry.BusRegister;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig.Type;

@Construct(modid = ExtremeCobbleGeneratorMod.MODID)
public class ExtremeCobbleGeneratorCommonConstruct implements IModConstruct {
	
	@Override
	public void construct() {
		ModLoadingContext.get().registerConfig(Type.COMMON, CommonConfig.CONFIG);
		
		BusRegister.registerMod(ExtremeCobbleGeneratorBlocks::register);
		BusRegister.registerMod(ExtremeCobbleGeneratorContainerTypes::register);
		BusRegister.registerMod(ExtremeCobbleGeneratorTileEntityTypes::register);
	}
	
}
