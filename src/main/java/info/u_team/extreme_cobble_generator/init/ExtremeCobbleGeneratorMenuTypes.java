package info.u_team.extreme_cobble_generator.init;

import info.u_team.extreme_cobble_generator.ExtremeCobbleGeneratorMod;
import info.u_team.extreme_cobble_generator.menu.CobbleGeneratorMenu;
import info.u_team.u_team_core.api.registry.CommonRegister;
import info.u_team.u_team_core.api.registry.RegistryEntry;
import info.u_team.u_team_core.menutype.UMenuType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;

public class ExtremeCobbleGeneratorMenuTypes {
	
	public static final CommonRegister<MenuType<?>> MENU_TYPES = CommonRegister.create(Registries.MENU, ExtremeCobbleGeneratorMod.MODID);
	
	public static final RegistryEntry<UMenuType<CobbleGeneratorMenu>> GENERATOR = MENU_TYPES.register("generator", () -> new UMenuType<>(CobbleGeneratorMenu::new));
	
	static void register() {
		MENU_TYPES.register();
	}
	
}
