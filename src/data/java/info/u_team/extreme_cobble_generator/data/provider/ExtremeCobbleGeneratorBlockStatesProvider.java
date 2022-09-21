package info.u_team.extreme_cobble_generator.data.provider;

import static info.u_team.extreme_cobble_generator.init.ExtremeCobbleGeneratorBlocks.GENERATOR;

import info.u_team.u_team_core.data.CommonBlockStateProvider;
import info.u_team.u_team_core.data.GenerationData;

public class ExtremeCobbleGeneratorBlockStatesProvider extends CommonBlockStateProvider {
	
	public ExtremeCobbleGeneratorBlockStatesProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	public void register() {
		horizontalBlock(GENERATOR.get(), models().getExistingFile(modLoc(getPath(GENERATOR.get()))));
	}
	
}
