package com.qiushui1012.mod.voidinair.data.tag;

import com.qiushui1012.mod.voidinair.init.entity.ViaDamageTypeTags;
import com.qiushui1012.mod.voidinair.init.entity.ViaEntityTypeTags;
import dev.anvilcraft.lib.v2.registrum.providers.RegistrumTagsProvider;
import dev.dubhe.anvilcraft.init.entity.ModDamageTypeTags;
import dev.dubhe.anvilcraft.init.entity.ModEntityTypeTags;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;

public class ViaTagHandler {
    public static void entity(RegistrumTagsProvider<EntityType<?>> provider) {
        provider.rawBuilder(ModEntityTypeTags.AMULET_VALID)
            .addTag(ViaEntityTypeTags.BEEHIVE_AMULET_VALID.location())
            .addTag(ViaEntityTypeTags.GOLD_AMULET_VALID.location())
            .addTag(ViaEntityTypeTags.DOLPHIN_AMULET_VALID.location());

        provider.rawBuilder(ViaEntityTypeTags.BEEHIVE_AMULET_VALID)
            .addTag(EntityTypeTags.BEEHIVE_INHABITORS.location());

        provider.rawBuilder(ViaEntityTypeTags.GOLD_AMULET_VALID)
            .addElement(ViaTagHandler.findId(EntityType.PIGLIN))
            .addElement(ViaTagHandler.findId(EntityType.PIGLIN_BRUTE));

        provider.rawBuilder(ViaEntityTypeTags.DOLPHIN_AMULET_VALID)
            .addElement(ViaTagHandler.findId(EntityType.DOLPHIN))
            .addElement(ViaTagHandler.findId(EntityType.GUARDIAN))
            .addElement(ViaTagHandler.findId(EntityType.ELDER_GUARDIAN));
    }

    public static void damage(RegistrumTagsProvider<DamageType> provider) {
        provider.rawBuilder(ModDamageTypeTags.AMULET_VALID)
            .addTag(ViaDamageTypeTags.VOID_AMULET_VALID.location())
            .addTag(ViaDamageTypeTags.SNOWFLAKE_AMULET_VALID.location());

        provider.rawBuilder(ViaDamageTypeTags.VOID_AMULET_VALID)
            .addElement(DamageTypes.FELL_OUT_OF_WORLD.identifier());

        provider.rawBuilder(ViaDamageTypeTags.SNOWFLAKE_AMULET_VALID)
            .addElement(DamageTypes.FREEZE.identifier());
    }

    @SuppressWarnings("deprecation")
    private static Identifier findId(EntityType<?> type) {
        return type.builtInRegistryHolder().key().identifier();
    }
}
