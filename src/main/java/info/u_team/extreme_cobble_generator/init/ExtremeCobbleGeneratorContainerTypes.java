package info.u_team.extreme_cobble_generator.init;

import info.u_team.extreme_cobble_generator.ExtremeCobbleGeneratorMod;
import info.u_team.extreme_cobble_generator.container.ContainerCobbleGenerator;
import info.u_team.u_team_core.containertype.UContainerType;
import info.u_team.u_team_core.util.registry.BaseRegistryUtil;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = ExtremeCobbleGeneratorMod.MODID, bus = Bus.MOD)
public class ExtremeCobbleGeneratorContainerTypes {
	
	public static final ContainerType<ContainerCobbleGenerator> GENERATOR = new UContainerType<ContainerCobbleGenerator>("generator", ContainerCobbleGenerator::new);
	
	@SubscribeEvent
	public static void register(Register<ContainerType<?>> event) {
		BaseRegistryUtil.getAllGenericRegistryEntriesAndApplyNames(ExtremeCobbleGeneratorMod.MODID, ContainerType.class).forEach(event.getRegistry()::register);
	}
	
}
