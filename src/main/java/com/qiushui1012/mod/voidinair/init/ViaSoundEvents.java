package com.qiushui1012.mod.voidinair.init;

import com.qiushui1012.mod.voidinair.AncVoidInAir;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ViaSoundEvents {
    private static final DeferredRegister<SoundEvent> REGISTER = DeferredRegister.create(Registries.SOUND_EVENT, AncVoidInAir.MOD_ID);

    public static final DeferredHolder<SoundEvent, SoundEvent> BLACK_CAT_BASE = REGISTER.register(
        "block.black_cat.base",
        () -> SoundEvent.createVariableRangeEvent(AncVoidInAir.of("block.black_cat.base"))
    );
    public static final DeferredHolder<SoundEvent, SoundEvent> BLACK_CAT_BREAK = REGISTER.register(
        "block.black_cat.break",
        () -> SoundEvent.createVariableRangeEvent(AncVoidInAir.of("block.black_cat.break"))
    );
    public static final DeferredHolder<SoundEvent, SoundEvent> BLACK_CAT_STEP = REGISTER.register(
        "block.black_cat.step",
        () -> SoundEvent.createVariableRangeEvent(AncVoidInAir.of("block.black_cat.step"))
    );
    public static final DeferredHolder<SoundEvent, SoundEvent> BLACK_CAT_PLACE = REGISTER.register(
        "block.black_cat.place",
        () -> SoundEvent.createVariableRangeEvent(AncVoidInAir.of("block.black_cat.place"))
    );
    public static final DeferredHolder<SoundEvent, SoundEvent> BLACK_CAT_HIT = REGISTER.register(
        "block.black_cat.hit",
        () -> SoundEvent.createVariableRangeEvent(AncVoidInAir.of("block.black_cat.hit"))
    );
    public static final DeferredHolder<SoundEvent, SoundEvent> BLACK_CAT_FALL = REGISTER.register(
        "block.black_cat.fall",
        () -> SoundEvent.createVariableRangeEvent(AncVoidInAir.of("block.black_cat.fall"))
    );
    public static final DeferredHolder<SoundEvent, SoundEvent> BLACK_CAT_LAND = REGISTER.register(
        "block.black_cat.land",
        () -> SoundEvent.createVariableRangeEvent(AncVoidInAir.of("block.black_cat.land"))
    );
    public static final DeferredHolder<SoundEvent, SoundEvent> BLACK_CAT_USE = REGISTER.register(
        "block.black_cat.use",
        () -> SoundEvent.createVariableRangeEvent(AncVoidInAir.of("block.black_cat.use"))
    );

    public static void register(IEventBus modEventBus) {
        ViaSoundEvents.REGISTER.register(modEventBus);
    }
}
