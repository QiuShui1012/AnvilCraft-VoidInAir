package com.qiushui1012.mod.voidinair.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.qiushui1012.mod.voidinair.AncVoidInAir;
import com.qiushui1012.mod.voidinair.init.item.ViaAmulets;
import com.qiushui1012.mod.voidinair.item.property.consume.DisableBypassInvulnerabilityConsumeEffect;
import dev.dubhe.anvilcraft.api.amulet.AmuletManager;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DeathProtection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
abstract class LivingEntityMixin {
    @WrapOperation(
        method = "checkTotemDeathProtection",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/damagesource/DamageSource;is(Lnet/minecraft/tags/TagKey;)Z")
    )
    private boolean recordIsBypassInvulnerability(
        DamageSource instance,
        TagKey<DamageType> tag,
        Operation<Boolean> original,
        @Share(namespace = AncVoidInAir.MOD_ID, value = "isBypassInvulnerability") LocalBooleanRef ref
    ) {
        ref.set(original.call(instance, tag));
        return false;
    }

    @WrapOperation(
        method = "checkTotemDeathProtection",
        at = @At(
            value = "INVOKE",
            target = "Lnet/neoforged/neoforge/common/CommonHooks;"
                     + "onLivingUseTotem("
                     + "Lnet/minecraft/world/entity/LivingEntity;"
                     + "Lnet/minecraft/world/damagesource/DamageSource;"
                     + "Lnet/minecraft/world/item/ItemStack;"
                     + "Lnet/minecraft/world/InteractionHand;)Z"
        )
    )
    private boolean processBypassInvulnerability(
        LivingEntity entity,
        DamageSource damageSource,
        ItemStack totem,
        InteractionHand hand,
        Operation<Boolean> original,
        @Local(name = "protection") DeathProtection protection,
        @Share(namespace = AncVoidInAir.MOD_ID, value = "isBypassInvulnerability") LocalBooleanRef ref
    ) {
        boolean result = original.call(entity, damageSource, totem, hand);
        System.out.println("Original.call: " + result);
        if (ref.get() && !protection.deathEffects().contains(DisableBypassInvulnerabilityConsumeEffect.INSTANCE)) result = false;
        System.out.println("Ref: " + ref.get());
        System.out.println("Protection.deathEffects: " + protection.deathEffects());
        return result;
    }

    @Inject(
        method = "lambda$static$0",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/player/Player;"
                     + "getItemBySlot(Lnet/minecraft/world/entity/EquipmentSlot;"
                     + ")Lnet/minecraft/world/item/ItemStack;"
        ),
        cancellable = true
    )
    private static void hasPumpkinAmulet(
        LivingEntity livingEntity,
        LivingEntity target,
        CallbackInfoReturnable<Boolean> cir,
        @Local(name = "player") Player player
    ) {
        if (AmuletManager.get(player.registryAccess()).hasAmuletInInventory(player, ViaAmulets.PUMPKIN)) {
            cir.setReturnValue(true);
        }
    }
}
