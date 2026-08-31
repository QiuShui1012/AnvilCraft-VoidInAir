package com.qiushui1012.mod.voidinair.loot.conditions;

import com.mojang.serialization.MapCodec;
import com.qiushui1012.mod.voidinair.init.loot.ViaLootItemConditions;
import dev.anvilcraft.lib.v2.codec.CodecUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.CatVariant;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

import java.util.Optional;

public record MatchCatVariant(Optional<ResourceKey<CatVariant>> variant) implements LootItemCondition {
    public static final MapCodec<MatchCatVariant> CODEC = CodecUtil.mapCodec(
        ResourceKey.codec(Registries.CAT_VARIANT)
            .optionalFieldOf("variant")
            .forGetter(MatchCatVariant::variant),
        MatchCatVariant::new
    );

    @Override
    public boolean test(LootContext ctx) {
        return this.variant.isEmpty()
               || ctx.getParamOrNull(LootContextParams.THIS_ENTITY) instanceof Cat cat
                  && cat.getVariant().is(this.variant.get());
    }

    @Override
    public LootItemConditionType getType() {
        return ViaLootItemConditions.MATCH_CAT_VARIANT.get();
    }
}
