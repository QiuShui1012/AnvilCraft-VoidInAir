package com.qiushui1012.mod.voidinair.data.advancement;

import com.qiushui1012.mod.voidinair.AncVoidInAir;
import com.qiushui1012.mod.voidinair.advancement.criterion.VoidFountainCreateTrigger;
import com.qiushui1012.mod.voidinair.init.block.VIABlocks;
import com.qiushui1012.mod.voidinair.init.item.VIAItems;
import dev.anvilcraft.lib.v2.registrum.providers.RegistrumAdvancementProvider;
import dev.dubhe.anvilcraft.api.advancement.AdvancementLineHelper;
import dev.dubhe.anvilcraft.constant.SharedTextures;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.criterion.ConsumeItemTrigger;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.Objects;

public class VIAAdvancementsHandler {
    @SuppressWarnings("unused")
    public static void init(RegistrumAdvancementProvider provider) {
        HolderLookup.Provider registries = Objects.requireNonNull(provider.getProvider());
        HolderGetter<Item> itemLookup = registries.lookupOrThrow(Registries.ITEM);

        AdvancementLineHelper mainLine = new AdvancementLineHelper(AncVoidInAir.MOD_ID);
        AdvancementHolder root = mainLine.next()
            .display(
                Items.CAT_SPAWN_EGG.asItem(),
                Component.translatable("advancements.voidinair.root.title"),
                Component.translatable("advancements.voidinair.root.description"),
                SharedTextures.bg("misc", "advancement"),
                AdvancementType.TASK,
                false,
                false,
                false
            )
            .playerFirstDetected("join")
            .save(provider, "root");

        AdvancementLineHelper totemLine = mainLine.createBranch();
        AdvancementHolder voidTotem = totemLine.next()
            .display(
                VIAItems.TOTEM_OF_VOID,
                Component.translatable("advancements.voidinair.void_totem.title"),
                Component.translatable("advancements.voidinair.void_totem.description"),
                null,
                AdvancementType.TASK,
                true,
                true,
                false
            )
            .hasItems("has_void_totem", VIAItems.TOTEM_OF_VOID)
            .save(provider, "void_totem");
        AdvancementHolder voidTotemConsumed = totemLine.next()
            .display(
                VIAItems.TOTEM_OF_VOID,
                Component.translatable("advancements.voidinair.void_totem_consumed.title"),
                Component.translatable("advancements.voidinair.void_totem_consumed.description"),
                null,
                AdvancementType.TASK,
                true,
                true,
                false
            )
            .addCriterion("consume_void_totem", ConsumeItemTrigger.TriggerInstance.usedItem(itemLookup, VIAItems.TOTEM_OF_VOID))
            .save(provider, "void_totem_consumed");
        AdvancementHolder voidAmulet = totemLine.next()
            .display(
                VIAItems.VOID_AMULET,
                Component.translatable("advancements.voidinair.void_amulet.title"),
                Component.translatable("advancements.voidinair.void_amulet.description"),
                null,
                AdvancementType.TASK,
                true,
                true,
                false
            )
            .hasItems("has_void_amulet", VIAItems.VOID_AMULET)
            .save(provider, "void_amulet");

        AdvancementLineHelper fountainLine = mainLine.createBranch();
        AdvancementHolder voidPoint = fountainLine.next()
            .display(
                VIABlocks.VOID_FOUNTAIN,
                Component.translatable("advancements.voidinair.void_point.title"),
                Component.translatable("advancements.voidinair.void_point.description"),
                null,
                AdvancementType.TASK,
                true,
                true,
                false
            )
            .addCriterion("create_void_fountain", VoidFountainCreateTrigger.TriggerInstance.create())
            .save(provider, "void_point");
    }
}
