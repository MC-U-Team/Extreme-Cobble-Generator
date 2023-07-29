package info.u_team.extreme_cobble_generator.data.provider;

import static info.u_team.extreme_cobble_generator.init.ExtremeCobbleGeneratorBlocks.GENERATOR;
import static info.u_team.extreme_cobble_generator.init.ExtremeCobbleGeneratorCreativeTabs.TAB;

import info.u_team.u_team_core.data.CommonLanguagesProvider;
import info.u_team.u_team_core.data.GenerationData;

public class ExtremeCobbleGeneratorLanguagesProvider extends CommonLanguagesProvider {
	
	public ExtremeCobbleGeneratorLanguagesProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	public void register() {
		// English
		
		add(TAB.get(), "Extreme Cobblestone Generator");
		addBlock(GENERATOR, "Extreme Cobblestone Generator");
		add("container.extremecobblegenerator.generator", "Cobblestone Generator");
		add("container.extremecobblegenerator.generator.amount", "Amount");
		add("container.extremecobblegenerator.generator.description", "Can generate %s cobble per second");
		add("container.extremecobblegenerator.generator.working", "Working");
		add("container.extremecobblegenerator.generator.idling", "Idling");
		
		// German
		add("de_de", TAB.get(), "Extremer Pflasterstein Generator");
		addBlock("de_de", GENERATOR, "Extremer Pflasterstein Generator");
		add("de_de", "container.extremecobblegenerator.generator", "Pflasterstein Generator");
		add("de_de", "container.extremecobblegenerator.generator.amount", "Betrag");
		add("de_de", "container.extremecobblegenerator.generator.description", "Kann %s Kopfsteinpflaster pro Sekunde erzeugen");
		add("de_de", "container.extremecobblegenerator.generator.working", "Arbeitet");
		add("de_de", "container.extremecobblegenerator.generator.idling", "Leerlauf");
	}
	
}
