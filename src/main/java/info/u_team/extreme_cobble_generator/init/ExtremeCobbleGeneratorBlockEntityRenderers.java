package info.u_team.extreme_cobble_generator.init;

import info.u_team.extreme_cobble_generator.render.blockentity.CobbleGeneratorTileEntityRenderer;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.minecraftforge.eventbus.api.IEventBus;

public class ExtremeCobbleGeneratorBlockEntityRenderers {
	
	private static void setup(RegisterRenderers event) {
		event.registerBlockEntityRenderer(ExtremeCobbleGeneratorBlockEntityTypes.GENERATOR.get(), CobbleGeneratorTileEntityRenderer::new);
	}
	
	public static void registerMod(IEventBus bus) {
		bus.addListener(ExtremeCobbleGeneratorBlockEntityRenderers::setup);
	}
	
}
