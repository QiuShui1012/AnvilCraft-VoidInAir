package com.qiushui1012.mod.voidinair.init.item;

import com.qiushui1012.mod.voidinair.item.property.consume.DisableBypassInvulnerabilityConsumeEffect;
import com.qiushui1012.mod.voidinair.item.property.consume.SaveEntityFromVoidConsumeEffect;
import dev.dubhe.anvilcraft.item.property.consume.PreventShrinkingConsumeEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.component.DeathProtection;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.item.consume_effects.ClearAllStatusEffectsConsumeEffect;

import java.util.List;

public class VIADeathProtections {
    public static final DeathProtection TOTEM_OF_VOID = new DeathProtection(List.of(
        ClearAllStatusEffectsConsumeEffect.INSTANCE,
        new ApplyStatusEffectsConsumeEffect(List.of(
            new MobEffectInstance(MobEffects.REGENERATION, 900, 1),
            new MobEffectInstance(MobEffects.ABSORPTION, 100, 1),
            new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0),
            new MobEffectInstance(MobEffects.SLOW_FALLING, 700, 0)
        )),
        SaveEntityFromVoidConsumeEffect.INSTANCE,
        DisableBypassInvulnerabilityConsumeEffect.INSTANCE
    ));
    public static final DeathProtection VOID_AMULET = new DeathProtection(List.of(
        ClearAllStatusEffectsConsumeEffect.INSTANCE,
        new ApplyStatusEffectsConsumeEffect(List.of(
            new MobEffectInstance(MobEffects.REGENERATION, 900, 1),
            new MobEffectInstance(MobEffects.ABSORPTION, 100, 1),
            new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0),
            new MobEffectInstance(MobEffects.SLOW_FALLING, 700, 0)
        )),
        SaveEntityFromVoidConsumeEffect.INSTANCE,
        DisableBypassInvulnerabilityConsumeEffect.INSTANCE,
        PreventShrinkingConsumeEffect.INSTANCE
    ));
}
