package info.u_team.extreme_cobble_generator.init;

import info.u_team.extreme_cobble_generator.ExtremeCobbleGeneratorMod;
import info.u_team.extreme_cobble_generator.blockentity.CobbleGeneratorBlockEntity;
import info.u_team.u_team_core.api.registry.BlockEntityTypeRegister;
import info.u_team.u_team_core.api.registry.RegistryEntry;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ExtremeCobbleGeneratorBlockEntityTypes {
	
	public static final BlockEntityTypeRegister BLOCK_ENTITY_TYPES = BlockEntityTypeRegister.create(ExtremeCobbleGeneratorMod.MODID);
	
	public static final RegistryEntry<BlockEntityType<CobbleGeneratorBlockEntity>> GENERATOR = BLOCK_ENTITY_TYPES.register("generator", () -> BlockEntityType.Builder.of(CobbleGeneratorBlockEntity::new, ExtremeCobbleGeneratorBlocks.GENERATOR.get()));
	
	static void register() {
		BLOCK_ENTITY_TYPES.register();
	}
	
}
