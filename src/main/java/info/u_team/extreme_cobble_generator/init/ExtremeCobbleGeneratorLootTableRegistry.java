package info.u_team.extreme_cobble_generator.init;

import info.u_team.extreme_cobble_generator.ExtremeCobbleGeneratorMod;
import info.u_team.extreme_cobble_generator.loot.SetTileEntityNBT;
import net.minecraft.block.Block;
import net.minecraft.loot.functions.LootFunctionManager;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = ExtremeCobbleGeneratorMod.MODID, bus = Bus.MOD)
public class ExtremeCobbleGeneratorLootTableRegistry {
	
	@SubscribeEvent
	public static void register(Register<Block> event) {
		LootFunctionManager.registerFunction(new SetTileEntityNBT.Serializer());
	}
	
}
