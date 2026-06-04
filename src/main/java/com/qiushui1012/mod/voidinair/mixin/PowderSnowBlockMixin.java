package com.qiushui1012.mod.voidinair.mixin;

import com.qiushui1012.mod.voidinair.init.item.ViaAmulets;
import dev.dubhe.anvilcraft.api.amulet.AmuletManager;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.PowderSnowBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PowderSnowBlock.class)
abstract class PowderSnowBlockMixin {
    @Inject(method = "canEntityWalkOnPowderSnow", at = @At("HEAD"), cancellable = true)
    private static void checkSnowflakeAmulet(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (!(entity instanceof Player player)) return;
        if (!AmuletManager.get(player.registryAccess()).hasAmuletInInventory(player, ViaAmulets.SNOWFLAKE)) return;
        cir.setReturnValue(true);
    }
}
