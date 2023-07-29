package info.u_team.extreme_cobble_generator.data.provider;

import info.u_team.extreme_cobble_generator.init.ExtremeCobbleGeneratorBlocks;
import info.u_team.u_team_core.data.CommonBlockTagsProvider;
import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_core.item.tier.VanillaTierTags;
import net.minecraft.core.HolderLookup.Provider;

public class ExtremeCobbleGeneratorBlockTagsProvider extends CommonBlockTagsProvider {
	
	public ExtremeCobbleGeneratorBlockTagsProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	public void register(Provider provider) {
		tag(VanillaTierTags.MINEABLE_WITH_PICKAXE).add(ExtremeCobbleGeneratorBlocks.GENERATOR.get());
		tag(VanillaTierTags.NEEDS_IRON_TOOL).add(ExtremeCobbleGeneratorBlocks.GENERATOR.get());
	}
	
}
