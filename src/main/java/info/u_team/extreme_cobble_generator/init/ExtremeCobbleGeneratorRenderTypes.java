package info.u_team.extreme_cobble_generator.init;

import static info.u_team.extreme_cobble_generator.init.ExtremeCobbleGeneratorBlocks.GENERATOR;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ExtremeCobbleGeneratorRenderTypes {
	
	private static void setup(FMLClientSetupEvent event) {
		RenderTypeLookup.setRenderLayer(GENERATOR.get(), RenderType.getCutout());
	}
	
	public static void registerMod(IEventBus bus) {
		bus.addListener(ExtremeCobbleGeneratorRenderTypes::setup);
	}
}
