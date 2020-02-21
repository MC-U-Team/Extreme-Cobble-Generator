package info.u_team.extreme_cobble_generator.init;

import info.u_team.extreme_cobble_generator.ExtremeCobbleGeneratorMod;
import info.u_team.extreme_cobble_generator.render.tileentity.CobbleGeneratorTileEntityRenderer;
import info.u_team.u_team_core.util.registry.ClientRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = ExtremeCobbleGeneratorMod.MODID, bus = Bus.MOD, value = Dist.CLIENT)
public class ExtremeCobbleGeneratorTileEntityRenderers {
	
	@SubscribeEvent
	public static void register(FMLClientSetupEvent event) {
		ClientRegistry.registerSpecialTileEntityRenderer(ExtremeCobbleGeneratorTileEntityTypes.GENERATOR, CobbleGeneratorTileEntityRenderer::new);
	}
	
}
