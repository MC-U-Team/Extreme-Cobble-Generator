package info.u_team.extreme_cobble_generator.render.tileentity;

import info.u_team.extreme_cobble_generator.tileentity.TileEntityCobbleGenerator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class TileEntityRendererCobbleGenerator extends TileEntitySpecialRenderer<TileEntityCobbleGenerator> {
	
	@Override
	public void render(TileEntityCobbleGenerator tileentity, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		
		super.render(tileentity, x, y, z, partialTicks, destroyStage, alpha);
		
		if (!(rendererDispatcher.entity instanceof EntityLivingBase)) {
			return;
		}
		
		if (!tileentity.isWorking()) {
			return;
		}
		
		EntityLivingBase livingbase = (EntityLivingBase) rendererDispatcher.entity;
		
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x + 0.5, (float) y + 0.25, (float) z + 0.5);
		GlStateManager.scale(1.3F, 1.3F, 1.3F);
		GlStateManager.rotate(rendererDispatcher.entity.ticksExisted * 5, 0F, 1F, 0F);
		GlStateManager.enableRescaleNormal();
		bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		
		Minecraft.getMinecraft().getItemRenderer().renderItem(livingbase, new ItemStack(Blocks.COBBLESTONE), ItemCameraTransforms.TransformType.GROUND);
		
		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
	}
	
}
