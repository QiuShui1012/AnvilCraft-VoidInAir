package com.qiushui1012.mod.voidinair.mixin;

import com.qiushui1012.mod.voidinair.init.item.ViaAmulets;
import dev.dubhe.anvilcraft.api.amulet.AmuletManager;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PiglinAi.class)
public class PiglinAiMixin {
    @Inject(method = "isWearingSafeArmor", at = @At("HEAD"), cancellable = true)
    private static void checkForGoldAmulet(LivingEntity livingEntity, CallbackInfoReturnable<Boolean> cir) {
        if (!(livingEntity instanceof Player player)) return;
        if (AmuletManager.get(player.registryAccess()).hasAmuletInInventory(player, ViaAmulets.GOLD)) {
            cir.setReturnValue(true);
        }
    }
}
