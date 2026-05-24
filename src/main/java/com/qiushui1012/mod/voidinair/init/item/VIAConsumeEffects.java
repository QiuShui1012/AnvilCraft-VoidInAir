package com.qiushui1012.mod.voidinair.init.item;

import com.qiushui1012.mod.voidinair.AncVoidInAir;
import com.qiushui1012.mod.voidinair.item.property.consume.DisableBypassInvulnerabilityConsumeEffect;
import com.qiushui1012.mod.voidinair.item.property.consume.SaveEntityFromVoidConsumeEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class VIAConsumeEffects {
    public static final DeferredRegister<ConsumeEffect.Type<?>> REGISTER = DeferredRegister.create(
        Registries.CONSUME_EFFECT_TYPE,
        AncVoidInAir.MOD_ID
    );

    public static final DeferredHolder<ConsumeEffect.Type<?>, ConsumeEffect.Type<SaveEntityFromVoidConsumeEffect>> SAVE_ENTITY_FROM_VOID =
        REGISTER.register("save_entity_from_void", () -> new ConsumeEffect.Type<>(
            SaveEntityFromVoidConsumeEffect.CODEC,
            SaveEntityFromVoidConsumeEffect.STREAM_CODEC.cast()
        ));

    public static final DeferredHolder<ConsumeEffect.Type<?>, ConsumeEffect.Type<DisableBypassInvulnerabilityConsumeEffect>> DBI =
        REGISTER.register("disable_bypass_invulnerability", () -> new ConsumeEffect.Type<>(
            DisableBypassInvulnerabilityConsumeEffect.CODEC,
            DisableBypassInvulnerabilityConsumeEffect.STREAM_CODEC.cast()
        ));

    public static void init(IEventBus modEventBus) {
        REGISTER.register(modEventBus);
    }
}
