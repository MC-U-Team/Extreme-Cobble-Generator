package info.u_team.extreme_cobble_generator.init;

import info.u_team.extreme_cobble_generator.ExtremeCobbleGeneratorMod;
import info.u_team.extreme_cobble_generator.block.CobbleGeneratorBlock;
import info.u_team.u_team_core.util.registry.BlockDeferredRegister;
import info.u_team.u_team_core.util.registry.BlockRegistryObject;
import net.minecraft.item.BlockItem;
import net.minecraftforge.eventbus.api.IEventBus;

public class ExtremeCobbleGeneratorBlocks {
	
	public static final BlockDeferredRegister BLOCKS = BlockDeferredRegister.create(ExtremeCobbleGeneratorMod.MODID);
	
	public static final BlockRegistryObject<CobbleGeneratorBlock, BlockItem> GENERATOR = BLOCKS.register("generator", CobbleGeneratorBlock::new);
	
	public static void registerMod(IEventBus bus) {
		BLOCKS.register(bus);
	}
	
}
