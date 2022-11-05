package info.u_team.extreme_cobble_generator.init;

import info.u_team.extreme_cobble_generator.ExtremeCobbleGeneratorMod;
import info.u_team.extreme_cobble_generator.menu.CobbleGeneratorMenu;
import info.u_team.u_team_core.menutype.UMenuType;
import info.u_team.u_team_core.util.registry.CommonDeferredRegister;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ExtremeCobbleGeneratorContainerTypes {
	
	public static final CommonDeferredRegister<MenuType<?>> CONTAINER_TYPES = CommonDeferredRegister.create(ForgeRegistries.MENU_TYPES, ExtremeCobbleGeneratorMod.MODID);
	
	public static final RegistryObject<MenuType<CobbleGeneratorMenu>> GENERATOR = CONTAINER_TYPES.register("generator", () -> new UMenuType<>(CobbleGeneratorMenu::new));
	
	public static void registerMod(IEventBus bus) {
		CONTAINER_TYPES.register(bus);
	}
	
}
