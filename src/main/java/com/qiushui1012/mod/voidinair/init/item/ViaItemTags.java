package com.qiushui1012.mod.voidinair.init.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ViaItemTags {
    public static final TagKey<Item> PULP = ViaItemTags.common("pulp");

    private static TagKey<Item> common(String path) {
        return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", path));
    }
}
