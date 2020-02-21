package info.u_team.extreme_cobble_generator.data.provider;

import static info.u_team.extreme_cobble_generator.init.ExtremeCobbleGeneratorBlocks.GENERATOR;
import static info.u_team.extreme_cobble_generator.init.ExtremeCobbleGeneratorItemGroups.GROUP;

import info.u_team.u_team_core.data.*;

public class ExtremeCobbleGeneratorLanguagesProvider extends CommonLanguagesProvider {
	
	public ExtremeCobbleGeneratorLanguagesProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	public void addTranslations() {
		// English
		
		add(GROUP, "Extreme Cobblestone Generator");
		add(GENERATOR, "Extreme Cobblestone Generator");
		add("container.extremecobblegenerator.generator", "Cobblestone Generator");
		add("container.extremecobblegenerator.generator.amount", "Amount");
		add("container.extremecobblegenerator.generator.description", "Can generate %s cobble per second");
		add("container.extremecobblegenerator.generator.working", "Working");
		add("container.extremecobblegenerator.generator.idling", "Idling");
	}
	
}
