package com.qiushui1012.mod.voidinair.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.math.Transformation;
import com.qiushui1012.mod.voidinair.init.block.VIABlocks;
import com.qiushui1012.mod.voidinair.util.LambdaUtil;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.client.renderer.blockentity.state.SkullBlockRenderState;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.WallSkullBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SkullBlockRenderer.class)
public class SkullBlockRendererMixin {
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
        if (blockState.is(VIABlocks.BLACK_CAT_HEAD)) {
            original.call(instance, LambdaUtil.qjdh(blockState.getValue(SkullBlock.ROTATION)));
        } else if (blockState.is(VIABlocks.BLACK_CAT_WALL_HEAD)) {
            original.call(instance, LambdaUtil.affw(blockState.getValue(WallSkullBlock.FACING)));
        }
    }
}
