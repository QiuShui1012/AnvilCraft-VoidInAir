package com.qiushui1012.mod.voidinair.init.item;

import com.qiushui1012.mod.voidinair.init.entity.ViaDamageTypeTags;
import com.qiushui1012.mod.voidinair.init.entity.ViaEntityTypeTags;
import com.qiushui1012.mod.voidinair.item.property.amulet.BlackCatAmulet;
import dev.dubhe.anvilcraft.init.item.ModAmulets;
import dev.dubhe.anvilcraft.item.property.component.amulet.DoNothingAmulet;
import dev.dubhe.anvilcraft.item.property.component.amulet.GiveEffectAmulet;
import dev.dubhe.anvilcraft.item.property.component.amulet.ImmuneDamageAmulet;
import dev.dubhe.anvilcraft.item.property.component.amulet.ImmuneEntityAmulet;
import dev.dubhe.anvilcraft.item.property.component.amulet.WrappedOthersAmulet;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class ViaAmulets {
    public static final ImmuneDamageAmulet VOID = ImmuneDamageAmulet.builder()
        .immune(ViaDamageTypeTags.VOID_AMULET_VALID)
        .build();
    public static final ImmuneDamageAmulet SNOWFLAKE = ImmuneDamageAmulet.builder()
        .immune(ViaDamageTypeTags.SNOWFLAKE_AMULET_VALID)
        .build();
    public static final ImmuneEntityAmulet BEEHIVE = ImmuneEntityAmulet.builder()
        .immune(ViaEntityTypeTags.BEEHIVE_AMULET_VALID)
        .build();
    public static final DoNothingAmulet GOLD = new DoNothingAmulet();
    public static final GiveEffectAmulet DOLPHIN = new GiveEffectAmulet(
        new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 3, 0, false, false),
        MinMaxBounds.Ints.atMost(3600)
    );
    public static final DoNothingAmulet PUMPKIN = new DoNothingAmulet();
    public static final WrappedOthersAmulet ANVILCRAFT = WrappedOthersAmulet.of(
        ModAmulets.ABNORMAL,
        ModAmulets.ANVIL,
        ModAmulets.GEM,
        ModAmulets.NATURE
    );
    public static final BlackCatAmulet BLACK_CAT = BlackCatAmulet.empty();
}
