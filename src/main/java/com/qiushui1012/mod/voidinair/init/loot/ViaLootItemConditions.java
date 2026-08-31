package com.qiushui1012.mod.voidinair.init.loot;

import com.qiushui1012.mod.voidinair.AncVoidInAir;
import com.qiushui1012.mod.voidinair.loot.conditions.MatchCatVariant;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ViaLootItemConditions {
    private static final DeferredRegister<LootItemConditionType> LOOT_CONDITION_TYPES =
        DeferredRegister.create(Registries.LOOT_CONDITION_TYPE, AncVoidInAir.MOD_ID);

    public static final Supplier<LootItemConditionType> MATCH_CAT_VARIANT = LOOT_CONDITION_TYPES.register(
        "match_cat_variant",
        () -> new LootItemConditionType(MatchCatVariant.CODEC)
    );

    public static void register(IEventBus modEventBus) {
        ViaLootItemConditions.LOOT_CONDITION_TYPES.register(modEventBus);
    }
}
