package info.u_team.extreme_cobble_generator.init;

import info.u_team.extreme_cobble_generator.ExtremeCobbleGeneratorMod;
import info.u_team.u_team_core.api.registry.CreativeModeTabRegister;
import info.u_team.u_team_core.api.registry.RegistryEntry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ExtremeCobbleGeneratorCreativeTabs {
	
	public static final CreativeModeTabRegister CREATIVE_TABS = CreativeModeTabRegister.create(ExtremeCobbleGeneratorMod.MODID);
	
	public static final RegistryEntry<CreativeModeTab> TAB = CREATIVE_TABS.register("tab", builder -> {
		builder.icon(() -> new ItemStack(ExtremeCobbleGeneratorBlocks.GENERATOR.get()));
		builder.displayItems((parameters, output) -> {
			ExtremeCobbleGeneratorBlocks.BLOCKS.itemIterable().forEach(output::accept);
		});
	});
	
	static void register() {
		CREATIVE_TABS.register();
	}
	
}
