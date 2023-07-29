package info.u_team.extreme_cobble_generator.init;

import info.u_team.extreme_cobble_generator.render.blockentity.CobbleGeneratorTileEntityRenderer;
import info.u_team.u_team_core.api.registry.client.EntityRendererRegister;
import net.minecraft.Util;

public class ExtremeCobbleGeneratorEntityRenderers {
	
	public static final EntityRendererRegister ENTITY_RENDERERS = Util.make(EntityRendererRegister.create(), entityRenderers -> {
		entityRenderers.register(ExtremeCobbleGeneratorBlockEntityTypes.GENERATOR, CobbleGeneratorTileEntityRenderer::new);
	});
	
	static void register() {
		ENTITY_RENDERERS.register();
	}
	
}
