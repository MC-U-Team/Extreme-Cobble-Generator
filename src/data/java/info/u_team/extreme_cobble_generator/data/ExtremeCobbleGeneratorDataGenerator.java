package info.u_team.extreme_cobble_generator.data;

import info.u_team.extreme_cobble_generator.ExtremeCobbleGeneratorMod;
import info.u_team.extreme_cobble_generator.data.provider.ExtremeCobbleGeneratorBlockStateProvider;
import info.u_team.extreme_cobble_generator.data.provider.ExtremeCobbleGeneratorItemModelProvider;
import info.u_team.extreme_cobble_generator.data.provider.ExtremeCobbleGeneratorLanguagesProvider;
import info.u_team.extreme_cobble_generator.data.provider.ExtremeCobbleGeneratorLootTableProvider;
import info.u_team.extreme_cobble_generator.data.provider.ExtremeCobbleGeneratorRecipeProvider;
import info.u_team.u_team_core.data.GenerationData;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = ExtremeCobbleGeneratorMod.MODID, bus = Bus.MOD)
public class ExtremeCobbleGeneratorDataGenerator {
	
	@SubscribeEvent
	public static void data(GatherDataEvent event) {
		final GenerationData data = new GenerationData(ExtremeCobbleGeneratorMod.MODID, event);
		data.addProvider(event.includeServer(), ExtremeCobbleGeneratorLootTableProvider::new);
		data.addProvider(event.includeServer(), ExtremeCobbleGeneratorRecipeProvider::new);
		
		data.addProvider(event.includeClient(), ExtremeCobbleGeneratorBlockStateProvider::new);
		data.addProvider(event.includeClient(), ExtremeCobbleGeneratorItemModelProvider::new);
		data.addProvider(event.includeClient(), ExtremeCobbleGeneratorLanguagesProvider::new);
	}
	
}
