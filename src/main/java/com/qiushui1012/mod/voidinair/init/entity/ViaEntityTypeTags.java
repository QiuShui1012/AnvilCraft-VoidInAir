package com.qiushui1012.mod.voidinair.init.entity;

import com.qiushui1012.mod.voidinair.AncVoidInAir;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class ViaEntityTypeTags {
    public static final TagKey<EntityType<?>> BEEHIVE_AMULET_VALID = ViaEntityTypeTags.bind("amulet_valid/beehive");
    public static final TagKey<EntityType<?>> GOLD_AMULET_VALID = ViaEntityTypeTags.bind("amulet_valid/gold");
    public static final TagKey<EntityType<?>> DOLPHIN_AMULET_VALID = ViaEntityTypeTags.bind("amulet_valid/dolphin");
    public static final TagKey<EntityType<?>> PUMPKIN_AMULET_VALID = ViaEntityTypeTags.bind("amulet_valid/pumpkin");

    @SuppressWarnings("unused")
    private static TagKey<EntityType<?>> bindC(String id) {
        return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("c", id));
    }

    private static TagKey<EntityType<?>> bind(String id) {
        return TagKey.create(Registries.ENTITY_TYPE, AncVoidInAir.of(id));
    }
}
