package com.qiushui1012.mod.voidinair.init.entity;

import com.qiushui1012.mod.voidinair.AncVoidInAir;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

public class ViaDamageTypeTags {
    public static final TagKey<DamageType> VOID_AMULET_VALID = ViaDamageTypeTags.bind("amulet_valid/void");
    public static final TagKey<DamageType> SNOWFLAKE_AMULET_VALID = ViaDamageTypeTags.bind("amulet_valid/snowflake");

    @SuppressWarnings("unused")
    private static TagKey<DamageType> bindC(String id) {
        return TagKey.create(Registries.DAMAGE_TYPE, Identifier.fromNamespaceAndPath("c", id));
    }

    private static TagKey<DamageType> bind(String id) {
        return TagKey.create(Registries.DAMAGE_TYPE, AncVoidInAir.of(id));
    }
}
