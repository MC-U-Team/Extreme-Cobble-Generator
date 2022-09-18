package info.u_team.extreme_cobble_generator.render.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;

import info.u_team.extreme_cobble_generator.blockentity.CobbleGeneratorBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.vector.Vector3f;

public class CobbleGeneratorTileEntityRenderer extends TileEntityRenderer<CobbleGeneratorBlockEntity> {
	
	public CobbleGeneratorTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcher) {
		super(rendererDispatcher);
	}
	
	@Override
	public void render(CobbleGeneratorBlockEntity tileEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
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
