package info.u_team.extreme_cobble_generator.data.provider;

import static info.u_team.extreme_cobble_generator.init.ExtremeCobbleGeneratorBlocks.GENERATOR;

import java.util.function.BiConsumer;

import info.u_team.extreme_cobble_generator.loot.SetTileEntityNBT;
import info.u_team.u_team_core.data.*;
import net.minecraft.util.*;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.SurvivesExplosion;

public class ExtremeCobbleGeneratorLootTablesProvider extends CommonLootTablesProvider {
	
	public ExtremeCobbleGeneratorLootTablesProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	protected void registerLootTables(BiConsumer<ResourceLocation, LootTable> consumer) {
		registerBlock(GENERATOR, addTileEntityLootTable(GENERATOR), consumer);
	}
	
	protected static LootTable addTileEntityLootTable(IItemProvider item) {
		return LootTable.builder() //
				.setParameterSet(LootParameterSets.BLOCK) //
				.addLootPool(LootPool.builder() //
						.rolls(ConstantRange.of(1)) //
						.addEntry(ItemLootEntry.builder(item)) //
						.acceptFunction(SetTileEntityNBT.builder()) //
						.acceptCondition(SurvivesExplosion.builder())) //
				.build();
	}
	
}
