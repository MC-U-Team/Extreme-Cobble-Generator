package info.u_team.extreme_cobble_generator.data.provider;

import static info.u_team.extreme_cobble_generator.init.ExtremeCobbleGeneratorBlocks.GENERATOR;

import java.util.function.Consumer;

import info.u_team.u_team_core.data.*;
import net.minecraft.data.*;
import net.minecraft.item.Items;
import net.minecraftforge.common.Tags;

public class ExtremeCobbleGeneratorRecipesProvider extends CommonRecipesProvider {
	
	public ExtremeCobbleGeneratorRecipesProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
		ShapedRecipeBuilder.shapedRecipe(GENERATOR.get()) //
				.patternLine("IAI") //
				.patternLine("IGI") //
				.patternLine("BCB") //
				.key('I', getIngredientOfTag(Tags.Items.INGOTS_IRON)) //
				.key('B', getIngredientOfTag(Tags.Items.STORAGE_BLOCKS_IRON)) //
				.key('A', Items.ANVIL) //
				.key('G', getIngredientOfTag(Tags.Items.GLASS_PANES)) //
				.key('C', getIngredientOfTag(Tags.Items.COBBLESTONE)) //
				.addCriterion("has_cobblestone", hasItem(Items.COBBLESTONE)) //
				.build(consumer);
	}
	
}
