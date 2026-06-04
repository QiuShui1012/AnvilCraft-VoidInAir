package com.qiushui1012.mod.voidinair.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.math.Axis;
import com.mojang.math.Transformation;
import com.qiushui1012.mod.voidinair.init.block.ViaBlocks;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.client.renderer.blockentity.WallAndGroundTransformations;
import net.minecraft.client.renderer.blockentity.state.SkullBlockRenderState;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.WallSkullBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SkullBlockRenderer.class)
public class SkullBlockRendererMixin {
    @Unique
    private static final WallAndGroundTransformations<Transformation> BLACK_CAT_TRANSFORMATIONS = new WallAndGroundTransformations<>(
        facing -> new Transformation(
            new Vector3f(0.5F - facing.getStepX() * 0.25F, 0.34375F, 0.5F - facing.getStepZ() * 0.25F),
            Axis.YP.rotationDegrees(-facing.getOpposite().toYRot()),
            new Vector3f(-1.0F, -1.0F, 1.0F),
            null
        ),
        rotation -> new Transformation(
            new Matrix4f()
                .translation(0.5F, 0.0F, 0.5F)
                .rotate(Axis.YP.rotationDegrees(-RotationSegment.convertToDegrees(rotation)))
                .translate(0.0F, 0.0F, -0.0625F)
                .scale(-1.0F, -1.0F, 1.0F)
        ),
        16
    );

    @WrapOperation(
        method = "extractRenderState("
                 + "Lnet/minecraft/world/level/block/entity/SkullBlockEntity;"
                 + "Lnet/minecraft/client/renderer/blockentity/state/SkullBlockRenderState;"
                 + "FLnet/minecraft/world/phys/Vec3;"
                 + "Lnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)V",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/client/renderer/blockentity/state/SkullBlockRenderState;"
                     + "transformation"
                     + ":Lcom/mojang/math/Transformation;",
            opcode = Opcodes.PUTFIELD
        )
    )
    private void overrideBlackCatHeadTransformations(
        SkullBlockRenderState instance,
        Transformation value,
        Operation<Void> original,
        @Local(name = "blockState") BlockState blockState
    ) {
        original.call(instance, value);
        if (blockState.is(ViaBlocks.BLACK_CAT_HEAD)) {
            original.call(instance, BLACK_CAT_TRANSFORMATIONS.freeTransformations(blockState.getValue(SkullBlock.ROTATION)));
        } else if (blockState.is(ViaBlocks.BLACK_CAT_WALL_HEAD)) {
            original.call(instance, BLACK_CAT_TRANSFORMATIONS.wallTransformation(blockState.getValue(WallSkullBlock.FACING)));
        }
    }
}
