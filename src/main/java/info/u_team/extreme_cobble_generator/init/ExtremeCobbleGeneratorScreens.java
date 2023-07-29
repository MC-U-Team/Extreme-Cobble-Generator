package info.u_team.extreme_cobble_generator.init;

import info.u_team.extreme_cobble_generator.screen.CobbleGeneratorScreen;
import info.u_team.u_team_core.api.registry.client.MenuScreenRegister;
import net.minecraft.Util;

public class ExtremeCobbleGeneratorScreens {
	
	private static final MenuScreenRegister MENU_SCREENS = Util.make(MenuScreenRegister.create(), menuScreens -> {
		menuScreens.register(ExtremeCobbleGeneratorMenuTypes.GENERATOR, CobbleGeneratorScreen::new);
	});
	
	static void register() {
		MENU_SCREENS.register();
	}
	
}
