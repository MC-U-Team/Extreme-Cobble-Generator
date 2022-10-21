package info.u_team.extreme_cobble_generator.data.provider;

import static info.u_team.extreme_cobble_generator.init.ExtremeCobbleGeneratorBlocks.GENERATOR;

import info.u_team.u_team_core.data.CommonItemModelProvider;
import info.u_team.u_team_core.data.GenerationData;

public class ExtremeCobbleGeneratorItemModelProvider extends CommonItemModelProvider {
	
	public ExtremeCobbleGeneratorItemModelProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	public void register() {
		simpleBlock(GENERATOR.get());
	}
	
}
