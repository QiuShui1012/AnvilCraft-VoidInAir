package com.qiushui1012.mod.voidinair.mixin;

import com.qiushui1012.mod.voidinair.init.item.ViaAmulets;
import dev.dubhe.anvilcraft.api.amulet.AmuletManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.advancements.critereon.PiglinNeutralArmorEntityPredicate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PiglinNeutralArmorEntityPredicate.class)
public class PiglinNeutralArmorEntityPredicateMixin {
    @Inject(method = "matches", at = @At("HEAD"), cancellable = true)
    private static void checkForGoldAmulet(Entity entity, ServerLevel level, Vec3 position, CallbackInfoReturnable<Boolean> cir) {
        if (!(entity instanceof Player player)) return;
        if (AmuletManager.get(player.registryAccess()).hasAmuletInInventory(player, ViaAmulets.GOLD)) {
            cir.setReturnValue(true);
        }
    }
}
