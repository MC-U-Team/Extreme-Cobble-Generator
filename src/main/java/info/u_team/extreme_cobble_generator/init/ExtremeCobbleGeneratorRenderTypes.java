package info.u_team.extreme_cobble_generator.init;

import static info.u_team.extreme_cobble_generator.init.ExtremeCobbleGeneratorBlocks.*;

import info.u_team.extreme_cobble_generator.ExtremeCobbleGeneratorMod;
import net.minecraft.client.renderer.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = ExtremeCobbleGeneratorMod.MODID, bus = Bus.MOD, value = Dist.CLIENT)
public class ExtremeCobbleGeneratorRenderTypes {
	
	@SubscribeEvent
	public static void register(FMLClientSetupEvent event) {
		// Cutout
		final RenderType cutout = RenderType.getCutout();
		
		RenderTypeLookup.setRenderLayer(GENERATOR.get(), cutout);
	}
	
}
