package info.u_team.extreme_cobble_generator.render;

import static info.u_team.u_team_core.util.registry.ClientRegistry.registerSpecialTileEntityRenderer;

import info.u_team.extreme_cobble_generator.render.tileentity.TileEntityRendererCobbleGenerator;
import info.u_team.extreme_cobble_generator.tileentity.TileEntityCobbleGenerator;

public class ExtremeCobbleGeneratorRenderRegistry {
	
	public ExtremeCobbleGeneratorRenderRegistry() {
		tileentity();
	}
	
	private void tileentity() {
		registerSpecialTileEntityRenderer(TileEntityCobbleGenerator.class, new TileEntityRendererCobbleGenerator());
	}
	
}
