package info.u_team.extreme_cobble_generator.init;

import info.u_team.extreme_cobble_generator.ExtremeCobbleGeneratorMod;
import info.u_team.extreme_cobble_generator.container.CobbleGeneratorContainer;
import info.u_team.u_team_core.containertype.UContainerType;
import info.u_team.u_team_core.util.registry.CommonDeferredRegister;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtremeCobbleGeneratorContainerTypes {
	
	public static final CommonDeferredRegister<ContainerType<?>> CONTAINER_TYPES = CommonDeferredRegister.create(ForgeRegistries.CONTAINERS, ExtremeCobbleGeneratorMod.MODID);
	
	public static final ContainerType<CobbleGeneratorContainer> GENERATOR = new UContainerType<CobbleGeneratorContainer>("generator", CobbleGeneratorContainer::new);
	
	public static void register(IEventBus bus) {
		CONTAINER_TYPES.register(bus);
	}
	
}
