package com.qiushui1012.mod.voidinair.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.qiushui1012.mod.voidinair.init.item.ViaAmulets;
import dev.dubhe.anvilcraft.api.amulet.AmuletManager;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.world.entity.monster.Guardian$GuardianAttackGoal")
public class GuardianAttackGoalMixin {
    @Shadow
    @Final
    private Guardian guardian;

    @Shadow
    @Final
    private boolean elder;

    @Inject(
        method = "tick",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/monster/Guardian;getNavigation()Lnet/minecraft/world/entity/ai/navigation/PathNavigation;"
        ),
        cancellable = true
    )
    private void checkRangeForDolphinAmulet(CallbackInfo ci, @Local(name = "livingentity") LivingEntity target) {
        if (!(target instanceof Player player)) return;
        if (!AmuletManager.get(player.registryAccess()).hasAmuletInInventory(player, ViaAmulets.DOLPHIN)) return;
        if (player.distanceTo(this.guardian) >= (this.elder ? 5.0 : 3.0)) {
            this.guardian.setTarget(null);
            this.guardian.setActiveAttackTarget(0);
            ci.cancel();
        }
    }
}
