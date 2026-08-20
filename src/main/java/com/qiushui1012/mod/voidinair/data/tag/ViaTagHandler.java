package com.qiushui1012.mod.voidinair.data.tag;

import com.qiushui1012.mod.voidinair.init.entity.ViaDamageTypeTags;
import com.qiushui1012.mod.voidinair.init.entity.ViaEntityTypeTags;
import dev.anvilcraft.lib.v2.registrum.providers.RegistrumTagsProvider;
import dev.dubhe.anvilcraft.init.entity.ModDamageTypeTags;
import dev.dubhe.anvilcraft.init.entity.ModEntityTypeTags;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;

public class ViaTagHandler {
    public static void entity(RegistrumTagsProvider<EntityType<?>> provider) {
        provider.addTag(ModEntityTypeTags.AMULET_VALID)
            .addTag(ViaEntityTypeTags.BEEHIVE_AMULET_VALID)
            .addTag(ViaEntityTypeTags.GOLD_AMULET_VALID)
            .addTag(ViaEntityTypeTags.DOLPHIN_AMULET_VALID);

        provider.addTag(ViaEntityTypeTags.BEEHIVE_AMULET_VALID)
            .addTag(EntityTypeTags.BEEHIVE_INHABITORS);

        provider.addTag(ViaEntityTypeTags.GOLD_AMULET_VALID)
            .add(ViaTagHandler.findId(EntityType.PIGLIN))
            .add(ViaTagHandler.findId(EntityType.PIGLIN_BRUTE));

        provider.addTag(ViaEntityTypeTags.DOLPHIN_AMULET_VALID)
            .add(ViaTagHandler.findId(EntityType.DOLPHIN))
            .add(ViaTagHandler.findId(EntityType.GUARDIAN))
            .add(ViaTagHandler.findId(EntityType.ELDER_GUARDIAN));

        provider.addTag(ViaEntityTypeTags.PUMPKIN_AMULET_VALID)
            .add(ViaTagHandler.findId(EntityType.ENDERMAN));
    }

    public static void damage(RegistrumTagsProvider<DamageType> provider) {
        provider.addTag(ModDamageTypeTags.AMULET_VALID)
            .addTag(ViaDamageTypeTags.VOID_AMULET_VALID)
            .addTag(ViaDamageTypeTags.SNOWFLAKE_AMULET_VALID);

        provider.addTag(ViaDamageTypeTags.VOID_AMULET_VALID)
            .add(DamageTypes.FELL_OUT_OF_WORLD);

        provider.addTag(ViaDamageTypeTags.SNOWFLAKE_AMULET_VALID)
            .add(DamageTypes.FREEZE);
    }

    @SuppressWarnings("deprecation")
    private static ResourceKey<EntityType<?>> findId(EntityType<?> type) {
        return type.builtInRegistryHolder().key();
    }
}
