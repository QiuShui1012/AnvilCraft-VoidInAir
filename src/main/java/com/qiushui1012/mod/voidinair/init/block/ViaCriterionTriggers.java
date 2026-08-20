package com.qiushui1012.mod.voidinair.init.block;

import com.qiushui1012.mod.voidinair.AncVoidInAir;
import com.qiushui1012.mod.voidinair.advancement.criterion.VoidFountainCreateTrigger;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ViaCriterionTriggers {
    private static final DeferredRegister<CriterionTrigger<?>> REGISTER = DeferredRegister.create(
        Registries.TRIGGER_TYPE,
        AncVoidInAir.MOD_ID
    );

    public static final DeferredHolder<CriterionTrigger<?>, VoidFountainCreateTrigger> VOID_FOUNTAIN_CREATE = REGISTER.register(
        "void_fountain_crate",
        VoidFountainCreateTrigger::new
    );

    public static void register(IEventBus modEventBus) {
        REGISTER.register(modEventBus);
    }
}
