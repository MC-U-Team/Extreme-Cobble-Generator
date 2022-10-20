package info.u_team.extreme_cobble_generator.init;

import info.u_team.extreme_cobble_generator.ExtremeCobbleGeneratorMod;
import info.u_team.u_team_core.api.construct.Construct;
import info.u_team.u_team_core.api.construct.ModConstruct;
import info.u_team.u_team_core.util.registry.BusRegister;

@Construct(modid = ExtremeCobbleGeneratorMod.MODID, client = true)
public class ExtremeCobbleGeneratorClientConstruct implements ModConstruct {
	
	@Override
	public void construct() {
		BusRegister.registerMod(ExtremeCobbleGeneratorScreens::registerMod);
		BusRegister.registerMod(ExtremeCobbleGeneratorBlockEntityRenderers::registerMod);
	}
	
}
