package info.u_team.extreme_cobble_generator.data.provider;

import static info.u_team.extreme_cobble_generator.init.ExtremeCobbleGeneratorBlocks.GENERATOR;

import java.util.function.BiConsumer;

import info.u_team.u_team_core.data.CommonLootTableProvider;
import info.u_team.u_team_core.data.GenerationData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;

public class ExtremeCobbleGeneratorLootTablesProvider extends CommonLootTableProvider {
	
	public ExtremeCobbleGeneratorLootTablesProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	public void register(BiConsumer<ResourceLocation, LootTable> consumer) {
		registerBlock(GENERATOR, addTileEntityBlockLootTable(GENERATOR.get()), consumer);
	}
	
}
