package info.u_team.extreme_cobble_generator.init;

import info.u_team.extreme_cobble_generator.ExtremeCobbleGeneratorMod;
import info.u_team.extreme_cobble_generator.blockentity.CobbleGeneratorBlockEntity;
import info.u_team.u_team_core.util.registry.BlockEntityTypeDeferredRegister;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

public class ExtremeCobbleGeneratorTileEntityTypes {
	
	public static final BlockEntityTypeDeferredRegister TILE_ENTITY_TYPES = BlockEntityTypeDeferredRegister.create(ExtremeCobbleGeneratorMod.MODID);
	
	public static final RegistryObject<BlockEntityType<CobbleGeneratorBlockEntity>> GENERATOR = TILE_ENTITY_TYPES.register("generator", () -> BlockEntityType.Builder.of(CobbleGeneratorBlockEntity::new, ExtremeCobbleGeneratorBlocks.GENERATOR.get()));
	
	public static void registerMod(IEventBus bus) {
		TILE_ENTITY_TYPES.register(bus);
	}
	
}
