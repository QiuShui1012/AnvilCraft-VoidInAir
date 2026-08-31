package com.qiushui1012.mod.voidinair.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.qiushui1012.mod.voidinair.init.block.ViaBlocks;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import javax.annotation.Nullable;

@Mixin(SkullBlockRenderer.class)
public class SkullBlockRendererMixin {
    @WrapOperation(
        method = "render(Lnet/minecraft/world/level/block/entity/SkullBlockEntity;"
                 + "FLcom/mojang/blaze3d/vertex/PoseStack;"
                 + "Lnet/minecraft/client/renderer/MultiBufferSource;"
                 + "II)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/blockentity/SkullBlockRenderer;"
                     + "renderSkull(Lnet/minecraft/core/Direction;"
                     + "FFLcom/mojang/blaze3d/vertex/PoseStack;"
                     + "Lnet/minecraft/client/renderer/MultiBufferSource;"
                     + "ILnet/minecraft/client/model/SkullModelBase;"
                     + "Lnet/minecraft/client/renderer/RenderType;)V"
        )
    )
    private void overrideBlackCatHeadTransformations(
        Direction direction,
        float rotation,
        float animation,
        PoseStack poseStack,
        MultiBufferSource bufferSource,
        int packedLight,
        SkullModelBase model,
        RenderType renderType,
        Operation<Void> original,
        @Local BlockState blockState
    ) {
        if (blockState.is(ViaBlocks.BLACK_CAT_HEAD) || blockState.is(ViaBlocks.BLACK_CAT_WALL_HEAD)) {
            this.voidinair$renderBlackCatHead(direction, rotation, animation, poseStack, bufferSource, packedLight, model, renderType);
        } else {
            original.call(direction, rotation, animation, poseStack, bufferSource, packedLight, model, renderType);
        }
    }

    @Unique
    private void voidinair$renderBlackCatHead(
        @Nullable Direction direction,
        float rotation,
        float animation,
        PoseStack poseStack,
        MultiBufferSource bufferSource,
        int packedLight,
        SkullModelBase model,
        RenderType renderType
    ) {
        poseStack.pushPose();
        if (direction == null) {
            poseStack.translate(0.5F, -0.09375F, 0.5F);
            poseStack.mulPose(Axis.YN.rotationDegrees(rotation));
            poseStack.translate(0.0F, 0.0F, -0.0625F);
            poseStack.mulPose(Axis.YP.rotationDegrees(rotation));
        } else {
            float f = 0.25F;
            poseStack.translate(0.5F - (float) direction.getStepX() * 0.25F, 0.25F, 0.5F - (float) direction.getStepZ() * 0.25F);
        }

        poseStack.scale(-1.0F, -1.0F, 1.0F);
        VertexConsumer vertexConsumer = bufferSource.getBuffer(renderType);
        model.setupAnim(animation, rotation, 0.0F);
        model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }
}
