package info.u_team.extreme_cobble_generator.init;

import info.u_team.extreme_cobble_generator.screen.CobbleGeneratorScreen;
import info.u_team.u_team_core.util.registry.ClientRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ExtremeCobbleGeneratorScreens {
	
	private static void setup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			ClientRegistry.registerScreen(ExtremeCobbleGeneratorContainerTypes.GENERATOR, CobbleGeneratorScreen::new);
		});
	}
	
	public static void registerMod(IEventBus bus) {
		bus.addListener(ExtremeCobbleGeneratorScreens::setup);
	}
	
}
