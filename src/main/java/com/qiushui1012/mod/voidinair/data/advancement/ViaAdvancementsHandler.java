package com.qiushui1012.mod.voidinair.data.advancement;

import com.qiushui1012.mod.voidinair.AncVoidInAir;
import com.qiushui1012.mod.voidinair.advancement.criterion.VoidFountainCreateTrigger;
import com.qiushui1012.mod.voidinair.init.block.ViaBlocks;
import com.qiushui1012.mod.voidinair.init.item.ViaItems;
import dev.anvilcraft.lib.v2.registrum.providers.RegistrumAdvancementProvider;
import dev.dubhe.anvilcraft.AnvilCraft;
import dev.dubhe.anvilcraft.api.advancement.AdvancementLineHelper;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.criterion.ConsumeItemTrigger;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;

import java.util.Objects;

public class ViaAdvancementsHandler {
    @SuppressWarnings("unused")
    public static void init(RegistrumAdvancementProvider provider) {
        HolderLookup.Provider registries = Objects.requireNonNull(provider.getProvider());
        HolderGetter<Item> itemLookup = registries.lookupOrThrow(Registries.ITEM);

        AdvancementLineHelper mainLine = new AdvancementLineHelper(AncVoidInAir.MOD_ID);
        AdvancementHolder root = mainLine.next()
            .display(
                ViaBlocks.BLACK_CAT.asItem(),
                Component.translatable("advancements.voidinair.root.title"),
                Component.translatable("advancements.voidinair.root.description"),
                AnvilCraft.of("gui/misc/background/advancement"),
                AdvancementType.TASK,
                false,
                false,
                false
            )
            .playerFirstDetected("join")
            .save(provider, "root");

        AdvancementLineHelper voidLine = mainLine.createBranch();
        AdvancementHolder voidTotem = voidLine.next()
            .display(
                ViaItems.TOTEM_OF_VOID,
                Component.translatable("advancements.voidinair.void_totem.title"),
                Component.translatable("advancements.voidinair.void_totem.description"),
                null,
                AdvancementType.TASK,
                true,
                true,
                false
            )
            .hasItems("has_void_totem", ViaItems.TOTEM_OF_VOID)
            .save(provider, "void_totem");
        AdvancementHolder voidTotemConsumed = voidLine.next()
            .display(
                ViaItems.TOTEM_OF_VOID,
                Component.translatable("advancements.voidinair.void_totem_consumed.title"),
                Component.translatable("advancements.voidinair.void_totem_consumed.description"),
                null,
                AdvancementType.TASK,
                true,
                true,
                false
            )
            .addCriterion("consume_void_totem", ConsumeItemTrigger.TriggerInstance.usedItem(itemLookup, ViaItems.TOTEM_OF_VOID))
            .save(provider, "void_totem_consumed");
        AdvancementHolder voidAmulet = voidLine.next()
            .display(
                ViaItems.VOID_AMULET,
                Component.translatable("advancements.voidinair.void_amulet.title"),
                Component.translatable("advancements.voidinair.void_amulet.description"),
                null,
                AdvancementType.TASK,
                true,
                true,
                false
            )
            .hasItems("has_void_amulet", ViaItems.VOID_AMULET)
            .save(provider, "void_amulet");

        AdvancementLineHelper fountainLine = mainLine.createBranch();
        AdvancementHolder voidPoint = fountainLine.next()
            .display(
                ViaBlocks.VOID_FOUNTAIN,
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
