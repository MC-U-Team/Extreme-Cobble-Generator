package info.u_team.extreme_cobble_generator.init;

import java.util.List;
import info.u_team.extreme_cobble_generator.ExtremeCobbleGeneratorMod;
import info.u_team.extreme_cobble_generator.block.CobbleGeneratorBlock;
import info.u_team.u_team_core.util.registry.*;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

public class ExtremeCobbleGeneratorBlocks {
	
	public static final BlockDeferredRegister BLOCKS = BlockDeferredRegister.create(ExtremeCobbleGeneratorMod.MODID);
	
	public static final CobbleGeneratorBlock GENERATOR = new CobbleGeneratorBlock("generator");
	
	public static void register(IEventBus bus) {
		BLOCKS.register(bus);
	}
	
}
