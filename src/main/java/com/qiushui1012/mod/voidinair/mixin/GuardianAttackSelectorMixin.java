package com.qiushui1012.mod.voidinair.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.qiushui1012.mod.voidinair.init.item.ViaAmulets;
import dev.dubhe.anvilcraft.api.amulet.AmuletManager;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import javax.annotation.Nullable;

@Mixin(targets = "net.minecraft.world.entity.monster.Guardian$GuardianAttackSelector")
public class GuardianAttackSelectorMixin {
    @ModifyExpressionValue(
        method = "test(Lnet/minecraft/world/entity/LivingEntity;)Z",
        at = @At(value = "CONSTANT", args = "doubleValue=9.0")
    )
    private double decreaseRangeForDolphinAmulet(double constant, @Local(argsOnly = true, ordinal = 0) @Nullable LivingEntity target) {
        if (!(target instanceof Player player)) return constant;
        if (AmuletManager.get(player.registryAccess()).hasAmuletInInventory(player, ViaAmulets.DOLPHIN)) {
            return constant / 3.0;
        }
        return constant;
    }
}
