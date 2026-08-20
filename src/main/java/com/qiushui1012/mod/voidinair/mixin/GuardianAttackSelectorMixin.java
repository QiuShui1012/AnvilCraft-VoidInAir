package com.qiushui1012.mod.voidinair.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.qiushui1012.mod.voidinair.init.item.ViaAmulets;
import dev.dubhe.anvilcraft.api.amulet.AmuletManager;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import javax.annotation.Nullable;

@Mixin(targets = "net.minecraft.world.entity.monster.Guardian$GuardianAttackSelector")
public class GuardianAttackSelectorMixin {
    @ModifyConstant(
        method = "test(Lnet/minecraft/world/entity/LivingEntity;)Z",
        constant = @Constant(doubleValue = 9.0F)
    )
    private double decreaseRangeForDolphinAmulet(double constant, @Local(argsOnly = true, ordinal = 0) @Nullable LivingEntity target) {
        if (!(target instanceof Player player)) return constant;
        if (AmuletManager.get(player.registryAccess()).hasAmuletInInventory(player, ViaAmulets.DOLPHIN)) {
            return constant / 3.0;
        }
        return constant;
    }
}
