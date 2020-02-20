package info.u_team.extreme_cobble_generator.init;

import info.u_team.extreme_cobble_generator.ExtremeCobbleGeneratorMod;
import info.u_team.u_team_core.util.registry.BaseRegistryUtil;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = ExtremeCobbleGeneratorMod.MODID, bus = Bus.MOD)
public class ExtremeCobbleGeneratorContainerTypes {
	
	@SubscribeEvent
	public static void register(Register<ContainerType<?>> event) {
		BaseRegistryUtil.getAllGenericRegistryEntriesAndApplyNames(ExtremeCobbleGeneratorMod.MODID, ContainerType.class).forEach(event.getRegistry()::register);
	}
	
}
