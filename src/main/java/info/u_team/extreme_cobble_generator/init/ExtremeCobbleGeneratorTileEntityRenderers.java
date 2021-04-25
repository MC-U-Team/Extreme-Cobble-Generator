package info.u_team.extreme_cobble_generator.init;

import info.u_team.extreme_cobble_generator.render.tileentity.CobbleGeneratorTileEntityRenderer;
import info.u_team.u_team_core.util.registry.ClientRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ExtremeCobbleGeneratorTileEntityRenderers {
	
	private static void setup(FMLClientSetupEvent event) {
		ClientRegistry.registerSpecialTileEntityRenderer(ExtremeCobbleGeneratorTileEntityTypes.GENERATOR, CobbleGeneratorTileEntityRenderer::new);
	}
	
	public static void registerMod(IEventBus bus) {
		bus.addListener(ExtremeCobbleGeneratorTileEntityRenderers::setup);
	}
	
}
