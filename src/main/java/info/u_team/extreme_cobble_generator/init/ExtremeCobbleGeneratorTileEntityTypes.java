package info.u_team.extreme_cobble_generator.init;

import info.u_team.extreme_cobble_generator.ExtremeCobbleGeneratorMod;
import info.u_team.extreme_cobble_generator.tileentity.CobbleGeneratorTileEntity;
import info.u_team.u_team_core.util.registry.TileEntityTypeDeferredRegister;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;

public class ExtremeCobbleGeneratorTileEntityTypes {
	
	public static final TileEntityTypeDeferredRegister TILE_ENTITY_TYPES = TileEntityTypeDeferredRegister.create(ExtremeCobbleGeneratorMod.MODID);
	
	public static final RegistryObject<TileEntityType<CobbleGeneratorTileEntity>> GENERATOR = TILE_ENTITY_TYPES.register("generator", () -> TileEntityType.Builder.create(CobbleGeneratorTileEntity::new, ExtremeCobbleGeneratorBlocks.GENERATOR.get()));
	
	public static void registerMod(IEventBus bus) {
		TILE_ENTITY_TYPES.register(bus);
	}
	
}
