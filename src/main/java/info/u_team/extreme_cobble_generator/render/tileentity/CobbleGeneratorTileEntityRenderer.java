package info.u_team.extreme_cobble_generator.render.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;

import info.u_team.extreme_cobble_generator.tileentity.CobbleGeneratorTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.item.*;

@SuppressWarnings("deprecation")
public class CobbleGeneratorTileEntityRenderer extends TileEntityRenderer<CobbleGeneratorTileEntity> {
	
	public CobbleGeneratorTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcher) {
		super(rendererDispatcher);
	}
	
	@Override
	public void render(CobbleGeneratorTileEntity tileEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
		if (!tileEntity.isWorking()) {
			return;
		}
		matrixStack.push();
		matrixStack.translate(0.5, 0.5, 0.5);
		matrixStack.scale(0.5F, 0.5F, 0.5F);
		matrixStack.rotate(Vector3f.YN.rotationDegrees(renderDispatcher.renderInfo.getRenderViewEntity().ticksExisted * 3));
		Minecraft.getInstance().getItemRenderer().renderItem(new ItemStack(Items.COBBLESTONE), ItemCameraTransforms.TransformType.FIXED, combinedLight, combinedOverlay, matrixStack, buffer);
		matrixStack.pop();
	}
	
}
