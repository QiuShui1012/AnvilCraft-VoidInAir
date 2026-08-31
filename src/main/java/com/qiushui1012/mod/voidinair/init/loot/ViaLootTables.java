package com.qiushui1012.mod.voidinair.init.loot;

import dev.dubhe.anvilcraft.AnvilCraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootTable;

public class ViaLootTables {
    public static final ResourceKey<LootTable> BEHEADING_CAT = beheadingKey(EntityType.CAT);

    private static ResourceKey<LootTable> ancKey(String path) {
        return ResourceKey.create(Registries.LOOT_TABLE, AnvilCraft.of(path));
    }

    @SuppressWarnings("SameParameterValue")
    private static ResourceKey<LootTable> beheadingKey(EntityType<?> entityType) {
        ResourceLocation entityId = EntityType.getKey(entityType);
        return ancKey("entities/beheading/" + entityId.getNamespace() + '/' + entityId.getPath());
    }
}
