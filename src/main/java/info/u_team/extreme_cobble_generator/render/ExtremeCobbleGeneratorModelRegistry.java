package info.u_team.extreme_cobble_generator.render;

import static info.u_team.u_team_core.util.registry.ClientRegistry.registerModel;

import info.u_team.extreme_cobble_generator.block.ExtremeCobbleGeneratorBlocks;

public class ExtremeCobbleGeneratorModelRegistry {
	
	public ExtremeCobbleGeneratorModelRegistry() {
		block();
	}
	
	private void block() {
		registerModel(ExtremeCobbleGeneratorBlocks.cobblegenerator);
	}
	
}
