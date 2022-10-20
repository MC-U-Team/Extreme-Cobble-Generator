package info.u_team.extreme_cobble_generator.screen;

import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.extreme_cobble_generator.ExtremeCobbleGeneratorMod;
import info.u_team.extreme_cobble_generator.blockentity.CobbleGeneratorBlockEntity;
import info.u_team.extreme_cobble_generator.menu.CobbleGeneratorMenu;
import info.u_team.u_team_core.gui.elements.EnergyStorageWidget;
import info.u_team.u_team_core.gui.elements.ScalableButton;
import info.u_team.u_team_core.gui.elements.ScalableSlider;
import info.u_team.u_team_core.gui.elements.USlider;
import info.u_team.u_team_core.screen.UContainerMenuScreen;
import info.u_team.u_team_core.util.RGBA;
import info.u_team.u_team_core.util.RenderUtil;
import info.u_team.u_team_core.util.WidgetUtil;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CobbleGeneratorScreen extends UContainerMenuScreen<CobbleGeneratorMenu> {
	
	private static final ResourceLocation BACKGROUND = new ResourceLocation(ExtremeCobbleGeneratorMod.MODID, "textures/gui/cobblegenerator.png");
	
	private final Component plusTextComponent;
	private final Component minusTextComponent;
	private final Component amountTextComponent;
	private final Component workingTextComponent;
	private final Component idlingTextComponent;
	
	private USlider slider;
	
	public CobbleGeneratorScreen(CobbleGeneratorMenu container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title, BACKGROUND, 176, 173);
		
		plusTextComponent = Component.literal("+");
		minusTextComponent = Component.literal("-");
		amountTextComponent = Component.translatable("container.extremecobblegenerator.generator.amount").append(": ");
		workingTextComponent = Component.translatable("container.extremecobblegenerator.generator.working");
		idlingTextComponent = Component.translatable("container.extremecobblegenerator.generator.idling");
	}
	
	@Override
	protected void init() {
		super.init();
		
		final CobbleGeneratorBlockEntity blockEntity = menu.getBlockEntity();
		
		addRenderableWidget(new EnergyStorageWidget(leftPos + 9, topPos + 20, 54, () -> blockEntity.getInternalEnergyStorage()));
		
		addRenderableWidget(createAdjustButton(leftPos + 36, topPos + 20, plusTextComponent, 100));
		addRenderableWidget(createAdjustButton(leftPos + 56, topPos + 20, plusTextComponent, 10));
		addRenderableWidget(createAdjustButton(leftPos + 76, topPos + 20, plusTextComponent, 1));
		
		addRenderableWidget(createAdjustButton(leftPos + 101, topPos + 20, minusTextComponent, -1));
		addRenderableWidget(createAdjustButton(leftPos + 121, topPos + 20, minusTextComponent, -10));
		addRenderableWidget(createAdjustButton(leftPos + 141, topPos + 20, minusTextComponent, -100));
		
		slider = addRenderableWidget(new ScalableSlider(leftPos + 36, topPos + 40, 120, 15, amountTextComponent, Component.empty(), 0, blockEntity.maxGeneration, blockEntity.getAmount(), false, true, true, 0.75F, slider -> {
			sendUpdateMessage(slider.getValueInt());
		}));
	}
	
	private ScalableButton createAdjustButton(int x, int y, Component component, int value) {
		return new ScalableButton(x, y, 15, 15, component, 0.75F, button -> sendUpdateAddValueMessage(value), (button, matrixStack, mouseX, mouseY) -> {
			if (WidgetUtil.isHovered(button)) {
				renderTooltip(matrixStack, component.copy().append(Integer.toString(Math.abs(value))), mouseX, mouseY);
			}
		});
	}
	
	private void sendUpdateAddValueMessage(int value) {
		sendUpdateMessage(menu.getBlockEntity().getAmount() + value);
	}
	
	private void sendUpdateMessage(int value) {
		menu.getBlockEntity().setAmount(value);
		menu.getBlockEntity().getAmountUpdateMessage().triggerMessage(() -> new FriendlyByteBuf(Unpooled.copyInt(value)));
	}
	
	@Override
	public void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
		super.renderLabels(poseStack, mouseX, mouseY);
		font.drawWordWrap(Component.translatable("container.extremecobblegenerator.generator.description", menu.getBlockEntity().getAmount() * 20), 36, 58, 120, 0x404040);
	}
	
	@Override
	public void renderForeground(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		super.renderForeground(poseStack, mouseX, mouseY, partialTicks);
		RenderUtil.drawTexturedQuad(poseStack, leftPos + 155, topPos + 75, 10, 10, 32, 32, imageWidth + (menu.getBlockEntity().isWorking() ? 32 : 0), 0, backgroundWidth, backgroundHeight, 0, background, RGBA.WHITE);
	}
	
	@Override
	public void renderToolTip(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		super.renderToolTip(poseStack, mouseX, mouseY, partialTicks);
		if (isHovering(154, 74, 12, 12, mouseX, mouseY)) {
			renderTooltip(poseStack, menu.getBlockEntity().isWorking() ? workingTextComponent : idlingTextComponent, mouseX, mouseY);
		}
	}
	
	@Override
	public void containerTick() {
		super.containerTick();
		if (slider != null) {
			slider.setValue(menu.getBlockEntity().getAmount());
		}
	}
	
	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		if (slider != null) {
			slider.mouseReleased(mouseX, mouseY, button);
		}
		return super.mouseReleased(mouseX, mouseY, button);
	}
}
