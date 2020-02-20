package info.u_team.extreme_cobble_generator.init;

import info.u_team.extreme_cobble_generator.ExtremeCobbleGeneratorConstants;
import info.u_team.extreme_cobble_generator.block.CobbleGeneratorBlock;
import info.u_team.u_team_core.block.UBlock;
import info.u_team.u_team_core.registry.BlockRegistry;
import info.u_team.u_team_core.util.RegistryUtil;
import net.minecraft.block.Block;

public class ExtremeCobbleGeneratorBlocks {
	
	public static final UBlock cobblegenerator = new CobbleGeneratorBlock("cobblegenerator");
	
	public static void preinit() {
		BlockRegistry.register(ExtremeCobbleGeneratorConstants.MODID, RegistryUtil.getRegistryEntries(Block.class, ExtremeCobbleGeneratorBlocks.class));
	}
	
}
