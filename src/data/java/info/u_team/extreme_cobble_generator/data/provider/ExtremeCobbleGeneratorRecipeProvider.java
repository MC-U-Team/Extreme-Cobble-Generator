package info.u_team.extreme_cobble_generator.data.provider;

import static info.u_team.extreme_cobble_generator.init.ExtremeCobbleGeneratorBlocks.GENERATOR;

import java.util.function.Consumer;

import info.u_team.u_team_core.data.CommonRecipeProvider;
import info.u_team.u_team_core.data.GenerationData;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

public class ExtremeCobbleGeneratorRecipeProvider extends CommonRecipeProvider {
	
	public ExtremeCobbleGeneratorRecipeProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	public void register(Consumer<FinishedRecipe> consumer) {
		ShapedRecipeBuilder.shaped(GENERATOR.get()) //
				.pattern("IAI") //
				.pattern("IGI") //
				.pattern("BCB") //
				.define('I', getIngredientOfTag(Tags.Items.INGOTS_IRON)) //
				.define('B', getIngredientOfTag(Tags.Items.STORAGE_BLOCKS_IRON)) //
				.define('A', Items.ANVIL) //
				.define('G', getIngredientOfTag(Tags.Items.GLASS_PANES)) //
				.define('C', getIngredientOfTag(Tags.Items.COBBLESTONE)) //
				.unlockedBy("has_cobblestone", has(Items.COBBLESTONE)) //
				.save(consumer);
	}
	
}
