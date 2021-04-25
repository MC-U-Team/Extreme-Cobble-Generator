package info.u_team.extreme_cobble_generator.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import info.u_team.extreme_cobble_generator.ExtremeCobbleGeneratorMod;
import info.u_team.extreme_cobble_generator.container.CobbleGeneratorContainer;
import info.u_team.extreme_cobble_generator.tileentity.CobbleGeneratorTileEntity;
import info.u_team.u_team_core.gui.elements.EnergyStorageWidget;
import info.u_team.u_team_core.gui.elements.ScalableButton;
import info.u_team.u_team_core.gui.elements.ScalableSlider;
import info.u_team.u_team_core.screen.UBasicContainerScreen;
import info.u_team.u_team_core.util.WidgetUtil;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CobbleGeneratorScreen extends UBasicContainerScreen<CobbleGeneratorContainer> {
	
	private static final ResourceLocation BACKGROUND = new ResourceLocation(ExtremeCobbleGeneratorMod.MODID, "textures/gui/cobblegenerator.png");
	
	private final ITextComponent plusTextComponent;
	private final ITextComponent minusTextComponent;
	
	private ScalableSlider slider;
	
	public CobbleGeneratorScreen(CobbleGeneratorContainer container, PlayerInventory playerInventory, ITextComponent title) {
		super(container, playerInventory, title, BACKGROUND, 176, 173);
		
		plusTextComponent = new StringTextComponent("+");
		minusTextComponent = new StringTextComponent("-");
	}
	
	@Override
	protected void init() {
		super.init();
		
		final CobbleGeneratorTileEntity tileEntity = container.getTileEntity();
		
		addButton(new EnergyStorageWidget(guiLeft + 9, guiTop + 20, 54, () -> tileEntity.getInternalEnergyStorage()));
		
		addButton(createAdjustButton(guiLeft + 36, guiTop + 20, plusTextComponent, 100));
		addButton(createAdjustButton(guiLeft + 56, guiTop + 20, plusTextComponent, 10));
		addButton(createAdjustButton(guiLeft + 76, guiTop + 20, plusTextComponent, 1));
		
		addButton(createAdjustButton(guiLeft + 101, guiTop + 20, plusTextComponent, -1));
		addButton(createAdjustButton(guiLeft + 121, guiTop + 20, plusTextComponent, -10));
		addButton(createAdjustButton(guiLeft + 141, guiTop + 20, plusTextComponent, -100));
		
		slider = addButton(new ScalableSlider(guiLeft + 36, guiTop + 40, 120, 15, new TranslationTextComponent("container.extremecobblegenerator.generator.amount").appendString(": "), new StringTextComponent(""), 0, tileEntity.maxGeneration, tileEntity.getAmount(), false, true, true, 0.75F) {
			
			@Override
			public void onRelease(double mouseX, double mouseY) {
				super.onRelease(mouseX, mouseY);
				sendUpdateMessage(slider.getValueInt());
			}
		});
	}
	
	private ScalableButton createAdjustButton(int x, int y, ITextComponent component, int value) {
		return new ScalableButton(x, y, 15, 15, component, 0.75F, button -> sendUpdateAddValueMessage(value), (button, matrixStack, mouseX, mouseY) -> {
			if (WidgetUtil.isHovered(button)) {
				renderTooltip(matrixStack, component.copyRaw().appendString(Integer.toString(Math.abs(value))), mouseX, mouseY);
			}
		});
	}
	
	private void sendUpdateAddValueMessage(int value) {
		sendUpdateMessage(container.getTileEntity().getAmount() + value);
	}
	
	private void sendUpdateMessage(int value) {
		container.getTileEntity().setAmount(value);
		container.getTileEntity().getAmountUpdateMessage().triggerMessage(() -> new PacketBuffer(Unpooled.copyInt(value)));
	}
	
	@Override
	public void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
		font.func_238418_a_(new TranslationTextComponent("container.extremecobblegenerator.generator.description", container.getTileEntity().getAmount() * 20), 36, 58, 120, 0x404040);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(matrixStack, partialTicks, mouseX, mouseY);
		blit(matrixStack, guiLeft + 155, guiTop + 75, 10, 10, xSize + (container.getTileEntity().isWorking() ? 32 : 0), 0, 32, 32, backgroundWidth, backgroundHeight);
	}
	
	@Override
	public void renderToolTip(MatrixStack matrixStack, Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
		super.renderToolTip(matrixStack, minecraft, mouseX, mouseY, partialTicks);
		if (isPointInRegion(154, 74, 12, 12, mouseX, mouseY)) {
			renderTooltip(matrixStack, container.getTileEntity().isWorking() ? new TranslationTextComponent("container.extremecobblegenerator.generator.working") : new TranslationTextComponent("container.extremecobblegenerator.generator.idling"), mouseX, mouseY);
		}
	}
	
	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int mouseButton) {
		if (slider != null) {
			slider.mouseReleased(mouseX, mouseY, mouseButton);
		}
		return super.mouseReleased(mouseX, mouseY, mouseButton);
	}
	
	@Override
	public void closeScreen() {
		super.closeScreen();
		if (slider != null && slider.dragging) {
			sendUpdateMessage(slider.getValueInt());
		}
	}
	
	@Override
	public void tick() {
		super.tick();
		if (slider != null && !slider.dragging) {
			slider.setValue(container.getTileEntity().getAmount());
			slider.updateSlider();
		}
	}
	
}
