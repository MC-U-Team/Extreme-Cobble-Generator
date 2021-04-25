package info.u_team.extreme_cobble_generator.data;

import info.u_team.extreme_cobble_generator.ExtremeCobbleGeneratorMod;
import info.u_team.extreme_cobble_generator.data.provider.ExtremeCobbleGeneratorBlockStatesProvider;
import info.u_team.extreme_cobble_generator.data.provider.ExtremeCobbleGeneratorItemModelsProvider;
import info.u_team.extreme_cobble_generator.data.provider.ExtremeCobbleGeneratorLanguagesProvider;
import info.u_team.extreme_cobble_generator.data.provider.ExtremeCobbleGeneratorLootTablesProvider;
import info.u_team.extreme_cobble_generator.data.provider.ExtremeCobbleGeneratorRecipesProvider;
import info.u_team.u_team_core.data.GenerationData;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@EventBusSubscriber(modid = ExtremeCobbleGeneratorMod.MODID, bus = Bus.MOD)
public class ExtremeCobbleGeneratorDataGenerator {
	
	@SubscribeEvent
	public static void data(GatherDataEvent event) {
		final GenerationData data = new GenerationData(ExtremeCobbleGeneratorMod.MODID, event);
		if (event.includeServer()) {
			data.addProvider(ExtremeCobbleGeneratorLootTablesProvider::new);
			data.addProvider(ExtremeCobbleGeneratorRecipesProvider::new);
		}
		if (event.includeClient()) {
			data.addProvider(ExtremeCobbleGeneratorBlockStatesProvider::new);
			data.addProvider(ExtremeCobbleGeneratorItemModelsProvider::new);
			data.addProvider(ExtremeCobbleGeneratorLanguagesProvider::new);
		}
	}
	
}
