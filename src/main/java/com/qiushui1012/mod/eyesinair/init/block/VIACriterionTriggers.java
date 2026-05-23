package com.qiushui1012.mod.eyesinair.init.block;

import com.qiushui1012.mod.eyesinair.AncVoidInAir;
import com.qiushui1012.mod.eyesinair.advancement.criterion.VoidFountainCreateTrigger;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class VIACriterionTriggers {
    private static final DeferredRegister<CriterionTrigger<?>> REGISTER = DeferredRegister.create(
        Registries.TRIGGER_TYPE,
        AncVoidInAir.MOD_ID
    );

    public static final DeferredHolder<CriterionTrigger<?>, VoidFountainCreateTrigger> VOID_FOUNTAIN_CREATE = REGISTER.register(
        "void_fountain_crate",
        VoidFountainCreateTrigger::new
    );
}
