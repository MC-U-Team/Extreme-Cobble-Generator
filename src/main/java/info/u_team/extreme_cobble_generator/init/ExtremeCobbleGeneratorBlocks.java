package info.u_team.extreme_cobble_generator.init;

import info.u_team.extreme_cobble_generator.ExtremeCobbleGeneratorMod;
import info.u_team.extreme_cobble_generator.block.CobbleGeneratorBlock;
import info.u_team.u_team_core.api.registry.BlockRegister;
import info.u_team.u_team_core.api.registry.BlockRegistryEntry;
import net.minecraft.world.item.BlockItem;

public class ExtremeCobbleGeneratorBlocks {
	
	public static final BlockRegister BLOCKS = BlockRegister.create(ExtremeCobbleGeneratorMod.MODID);
	
	public static final BlockRegistryEntry<CobbleGeneratorBlock, BlockItem> GENERATOR = BLOCKS.register("generator", CobbleGeneratorBlock::new);
	
	static void register() {
		BLOCKS.register();
	}
	
}
