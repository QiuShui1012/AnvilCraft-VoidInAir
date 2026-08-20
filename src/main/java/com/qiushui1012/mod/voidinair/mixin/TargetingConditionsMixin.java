package com.qiushui1012.mod.voidinair.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.qiushui1012.mod.voidinair.init.item.ViaAmulets;
import dev.dubhe.anvilcraft.api.amulet.AmuletManager;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TargetingConditions.class)
public class TargetingConditionsMixin {
    @Definition(id = "d1", local = @Local(type = double.class, ordinal = 1))
    @Expression("d1 * d1")
    @ModifyExpressionValue(method = "test", at = @At("MIXINEXTRAS:EXPRESSION"))
    private double decreaseRangeForDolphinAmulet(
        double original,
        @Local(argsOnly = true, ordinal = 0) LivingEntity targeter,
        @Local(argsOnly = true, ordinal = 1) LivingEntity target
    ) {
        if (!(target instanceof Player player)) return original;
        if (!(targeter instanceof Guardian)) return original;
        if (AmuletManager.get(player.registryAccess()).hasAmuletInInventory(player, ViaAmulets.DOLPHIN)) {
            return original / 5.0;
        }
        return original;
    }
}
