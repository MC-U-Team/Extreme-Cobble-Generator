package info.u_team.extreme_cobble_generator.data.provider;

import static info.u_team.extreme_cobble_generator.init.ExtremeCobbleGeneratorBlocks.GENERATOR;

import info.u_team.u_team_core.data.CommonBlockStatesProvider;
import info.u_team.u_team_core.data.GenerationData;

public class ExtremeCobbleGeneratorBlockStatesProvider extends CommonBlockStatesProvider {
	
	public ExtremeCobbleGeneratorBlockStatesProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	protected void registerStatesAndModels() {
		horizontalBlock(GENERATOR.get(), models().getExistingFile(modLoc(getPath(GENERATOR.get()))));
	}
	
}
