package info.u_team.extreme_cobble_generator.gui;

import java.io.IOException;
import java.util.ArrayList;

import info.u_team.extreme_cobble_generator.ExtremeCobbleGeneratorConstants;
import info.u_team.extreme_cobble_generator.container.ContainerCobbleGenerator;
import info.u_team.extreme_cobble_generator.network.ExtremeCobbleGeneratorNetworkHandler;
import info.u_team.extreme_cobble_generator.network.message.MessageCobbleGeneratorUpdateAmount;
import info.u_team.extreme_cobble_generator.tileentity.TileEntityCobbleGenerator;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.*;
import net.minecraftforge.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class GuiCobbleGenerator extends GuiContainer {
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(ExtremeCobbleGeneratorConstants.MODID, "textures/gui/cobblegenerator.png");
	
	private TileEntityCobbleGenerator tileentity;
	
	private GuiSlider slider;
	
	public GuiCobbleGenerator(InventoryPlayer inventory, TileEntityCobbleGenerator tileentity) {
		super(new ContainerCobbleGenerator(inventory, tileentity));
		this.tileentity = tileentity;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		buttonList.add(new GuiButtonExt(0, guiLeft + 35, guiTop + 7, 20, 20, "+"));
		buttonList.add(new GuiButtonExt(1, guiLeft + 60, guiTop + 7, 20, 20, "+"));
		buttonList.add(new GuiButtonExt(2, guiLeft + 110, guiTop + 7, 20, 20, "-"));
		buttonList.add(new GuiButtonExt(3, guiLeft + 135, guiTop + 7, 20, 20, "-"));
		buttonList.add(slider = new GuiSlider(4, guiLeft + 35, guiTop + 34, 120, 20, "Amount: ", "", 1, tileentity.getMaxAmount(), tileentity.getAmount(), false, true, new GuiSlider.ISlider() {
			
			@Override
			public void onChangeSliderValue(GuiSlider slider) {
				ExtremeCobbleGeneratorNetworkHandler.network.sendToServer(new MessageCobbleGeneratorUpdateAmount(tileentity.getPos(), slider.getValueInt()));
			}
		}));
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		int newvalue = tileentity.getAmount();
		if (button.id == 0) {
			newvalue += 10;
		} else if (button.id == 1) {
			newvalue += 1;
		} else if (button.id == 2) {
			newvalue -= 1;
		} else if (button.id == 3) {
			newvalue -= 10;
		}
		slider.setValue(newvalue);
		slider.updateSlider();
		ExtremeCobbleGeneratorNetworkHandler.network.sendToServer(new MessageCobbleGeneratorUpdateAmount(tileentity.getPos(), newvalue));
	}
	
	@Override
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(TEXTURE);
		int i = this.guiLeft;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
		drawScaledCustomSizeModalRect(i + 158, j + 68, this.xSize + (tileentity.isWorking() ? 32 : 0), 0, 32, 32, 10, 10, 256, 256);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		drawCenteredString("" + tileentity.getAmount(), 95, 13, 4210752, false);
		
		fontRenderer.drawSplitString("Can generate " + (tileentity.getAmount() * 20) + " cobble per second", 35, 60, 120, 4210752);
		// drawCenteredString("Can generate " + (tileentity.getAmount() * 20) + " cobble
		// per second", 95, 60, 4210752, false);
		
		int maxenergy = tileentity.getEnergy().getMaxEnergyStored();
		int energy = tileentity.getEnergy().getEnergyStored();
		
		drawRect(8, 78, 24, 8, 0xFF8B8B8B);
		drawRect(8, 78, 24, 78 - (70 * energy / maxenergy), 0xFFFF4200);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		// Energy
		
		int maxenergy = tileentity.getEnergy().getMaxEnergyStored();
		int energy = tileentity.getEnergy().getEnergyStored();
		
		GlStateManager.pushMatrix();
		RenderHelper.enableGUIStandardItemLighting();
		
		boolean isHovering = isPointInRegion(8, 8, 16, 70, mouseX, mouseY);
		if (isHovering) {
			
			ArrayList<String> list = new ArrayList<>();
			list.add("Energy: " + energy);
			list.add("Capacity: " + maxenergy);
			
			drawHoveringText(list, mouseX, mouseY);
		}
		RenderHelper.enableStandardItemLighting();
		GlStateManager.popMatrix();
		
		// Working
		
		GlStateManager.pushMatrix();
		RenderHelper.enableGUIStandardItemLighting();
		
		boolean isHovering2 = isPointInRegion(158, 68, 10, 10, mouseX, mouseY);
		if (isHovering2) {
			drawHoveringText(tileentity.isWorking() ? "Working" : "Idling", mouseX, mouseY);
		}
		RenderHelper.enableStandardItemLighting();
		GlStateManager.popMatrix();
		
		renderHoveredToolTip(mouseX, mouseY);
		
	}
	
	public void drawCenteredString(String text, int x, int y, int color, boolean dropShadow) {
		fontRenderer.drawString(text, (float) (x - fontRenderer.getStringWidth(text) / 2), (float) y, 4210752, dropShadow);
	}
	
}
