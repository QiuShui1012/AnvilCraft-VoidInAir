package com.qiushui1012.mod.voidinair.init.item;

import com.qiushui1012.mod.voidinair.AncVoidInAir;
import com.qiushui1012.mod.voidinair.init.entity.ViaDamageTypeTags;
import com.qiushui1012.mod.voidinair.init.entity.ViaEntityTypeTags;
import dev.dubhe.anvilcraft.api.amulet.def.AmuletDefinition;
import dev.dubhe.anvilcraft.api.amulet.def.IAmuletDefinition;
import dev.dubhe.anvilcraft.init.registry.ModRegistryKeys;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;

public class ViaAmuletDefinitions {
    public static final ResourceKey<IAmuletDefinition> VOID = ViaAmuletDefinitions.key("void");
    public static final ResourceKey<IAmuletDefinition> SNOWFLAKE = ViaAmuletDefinitions.key("snowflake");
    public static final ResourceKey<IAmuletDefinition> BEEHIVE = ViaAmuletDefinitions.key("beehive");
    public static final ResourceKey<IAmuletDefinition> GOLD = ViaAmuletDefinitions.key("gold");
    public static final ResourceKey<IAmuletDefinition> DOLPHIN = ViaAmuletDefinitions.key("dolphin");

    public static void bootstrap(BootstrapContext<IAmuletDefinition> ctx) {
        HolderGetter<EntityType<?>> entityTypes = ctx.lookup(Registries.ENTITY_TYPE);
        ctx.register(
            ViaAmuletDefinitions.VOID,
            AmuletDefinition.builder(ViaItems.VOID_AMULET)
                .obtain(ViaDamageTypeTags.VOID_AMULET_VALID)
                .build()
        );
        ctx.register(
            ViaAmuletDefinitions.SNOWFLAKE,
            AmuletDefinition.builder(ViaItems.SNOWFLAKE_AMULET)
                .obtain(ViaDamageTypeTags.SNOWFLAKE_AMULET_VALID)
                .build()
        );
        ctx.register(
            ViaAmuletDefinitions.BEEHIVE,
            AmuletDefinition.builder(ViaItems.BEEHIVE_AMULET)
                .obtain(entityTypes, ViaEntityTypeTags.BEEHIVE_AMULET_VALID)
                .build()
        );
        ctx.register(
            ViaAmuletDefinitions.GOLD,
            AmuletDefinition.builder(ViaItems.GOLD_AMULET)
                .obtain(entityTypes, ViaEntityTypeTags.GOLD_AMULET_VALID)
                .build()
        );
        ctx.register(
            ViaAmuletDefinitions.DOLPHIN,
            AmuletDefinition.builder(ViaItems.DOLPHIN_AMULET)
                .obtain(entityTypes, ViaEntityTypeTags.DOLPHIN_AMULET_VALID)
                .build()
        );
    }

    private static ResourceKey<IAmuletDefinition> key(String name) {
        return ResourceKey.create(ModRegistryKeys.AMULET_DEF, AncVoidInAir.of(name));
    }
}
