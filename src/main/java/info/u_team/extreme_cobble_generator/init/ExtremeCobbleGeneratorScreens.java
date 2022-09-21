package info.u_team.extreme_cobble_generator.init;

import info.u_team.extreme_cobble_generator.screen.CobbleGeneratorScreen;
import info.u_team.u_team_core.event.RegisterMenuScreensEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public class ExtremeCobbleGeneratorScreens {
	
	private static void setup(RegisterMenuScreensEvent event) {
		event.registerScreen(ExtremeCobbleGeneratorContainerTypes.GENERATOR, CobbleGeneratorScreen::new);
	}
	
	public static void registerMod(IEventBus bus) {
		bus.addListener(ExtremeCobbleGeneratorScreens::setup);
	}
	
}
