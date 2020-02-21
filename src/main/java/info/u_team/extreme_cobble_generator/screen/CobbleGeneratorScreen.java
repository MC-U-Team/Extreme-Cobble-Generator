package info.u_team.extreme_cobble_generator.screen;

import info.u_team.extreme_cobble_generator.ExtremeCobbleGeneratorMod;
import info.u_team.extreme_cobble_generator.container.CobbleGeneratorContainer;
import info.u_team.extreme_cobble_generator.tileentity.CobbleGeneratorTileEntity;
import info.u_team.u_team_core.gui.UContainerScreen;
import info.u_team.u_team_core.gui.elements.*;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
public class CobbleGeneratorScreen extends UContainerScreen<CobbleGeneratorContainer> {
	
	private static final ResourceLocation BACKGROUND = new ResourceLocation(ExtremeCobbleGeneratorMod.MODID, "textures/gui/cobblegenerator.png");
	
	private BetterFontSlider slider;
	
	public CobbleGeneratorScreen(CobbleGeneratorContainer container, PlayerInventory playerInventory, ITextComponent title) {
		super(container, playerInventory, title, BACKGROUND);
		xSize = 176;
		ySize = 173;
	}
	
	@Override
	protected void init() {
		super.init();
		
		final CobbleGeneratorTileEntity tileEntity = container.getTileEntity();
		
		addButton(new EnergyStorageWidget(guiLeft + 9, guiTop + 20, 54, () -> tileEntity.getInternalEnergyStorage()));
		
		addButton(new BetterButton(guiLeft + 36, guiTop + 20, 15, 15, 0.75F, "+", button -> sendUpdateAddValueMessage(100)));
		addButton(new BetterButton(guiLeft + 56, guiTop + 20, 15, 15, 0.75F, "+", button -> sendUpdateAddValueMessage(10)));
		addButton(new BetterButton(guiLeft + 76, guiTop + 20, 15, 15, 0.75F, "+", button -> sendUpdateAddValueMessage(1)));
		addButton(new BetterButton(guiLeft + 101, guiTop + 20, 15, 15, 0.75F, "-", button -> sendUpdateAddValueMessage(-1)));
		addButton(new BetterButton(guiLeft + 121, guiTop + 20, 15, 15, 0.75F, "-", button -> sendUpdateAddValueMessage(-10)));
		addButton(new BetterButton(guiLeft + 141, guiTop + 20, 15, 15, 0.75F, "-", button -> sendUpdateAddValueMessage(-100)));
		
		slider = addButton(new BetterFontSlider(guiLeft + 36, guiTop + 40, 120, 15, "Amount: ", "", 0, tileEntity.maxGeneration, tileEntity.getAmount(), false, true, 0.75F, null) {
			
			@Override
			public void onRelease(double mouseX, double mouseY) {
				super.onRelease(mouseX, mouseY);
				sendUpdateMessage(slider.getValueInt());
			}
		});
	}
	
	private void sendUpdateAddValueMessage(int value) {
		sendUpdateMessage(container.getTileEntity().getAmount() + value);
	}
	
	private void sendUpdateMessage(int value) {
		container.getTileEntity().getAmountUpdateMessage().triggerMessage(() -> new PacketBuffer(Unpooled.copyInt(value)));
	}
	
	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		renderBackground();
		super.render(mouseX, mouseY, partialTicks);
		buttons.forEach(button -> button.renderToolTip(mouseX, mouseY));
		renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		font.drawString(title.getFormattedText(), 8, 6, 4210752);
		font.drawString(playerInventory.getDisplayName().getFormattedText(), 8.0F, ySize - 94, 4210752);
	}
	
	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int mouseButton) {
		if (slider != null) {
			slider.mouseReleased(mouseX, mouseY, mouseButton);
		}
		return super.mouseReleased(mouseX, mouseY, mouseButton);
	}
	
	@Override
	public void tick() {
		super.tick();
		if (slider != null) {
			slider.setValue(container.getTileEntity().getAmount());
		}
	}
	
}
