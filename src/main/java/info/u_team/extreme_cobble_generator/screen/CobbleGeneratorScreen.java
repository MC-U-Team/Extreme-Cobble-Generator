package info.u_team.extreme_cobble_generator.screen;

import info.u_team.extreme_cobble_generator.ExtremeCobbleGeneratorMod;
import info.u_team.extreme_cobble_generator.container.CobbleGeneratorContainer;
import info.u_team.u_team_core.gui.UContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
public class CobbleGeneratorScreen extends UContainerScreen<CobbleGeneratorContainer> {
	
	private static final ResourceLocation BACKGROUND = new ResourceLocation(ExtremeCobbleGeneratorMod.MODID, "textures/gui/cobblegenerator.png");
	
	public CobbleGeneratorScreen(CobbleGeneratorContainer container, PlayerInventory playerInventory, ITextComponent title) {
		super(container, playerInventory, title, BACKGROUND);
	}
	
}
