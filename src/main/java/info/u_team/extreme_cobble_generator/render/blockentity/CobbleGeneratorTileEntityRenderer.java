package info.u_team.extreme_cobble_generator.render.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import info.u_team.extreme_cobble_generator.blockentity.CobbleGeneratorBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class CobbleGeneratorTileEntityRenderer implements BlockEntityRenderer<CobbleGeneratorBlockEntity> {
	
	private final BlockEntityRenderDispatcher renderDispatcher;
	private final ItemRenderer itemRenderer;
	
	public CobbleGeneratorTileEntityRenderer(BlockEntityRendererProvider.Context context) {
		renderDispatcher = context.getBlockEntityRenderDispatcher();
		itemRenderer = context.getItemRenderer();
	}
	
	@Override
	public void render(CobbleGeneratorBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
		if (!blockEntity.isWorking()) {
			return;
		}
		poseStack.pushPose();
		
		poseStack.translate(0.5, 0.5, 0.5);
		poseStack.scale(0.5F, 0.5F, 0.5F);
		poseStack.mulPose(Vector3f.YN.rotationDegrees(renderDispatcher.camera.getEntity().tickCount * 3));
		itemRenderer.renderStatic(new ItemStack(Items.COBBLESTONE), TransformType.FIXED, packedLight, packedOverlay, poseStack, bufferSource, 0);
		poseStack.popPose();
	}
	
}
