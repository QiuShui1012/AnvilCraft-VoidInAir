package com.qiushui1012.mod.voidinair.item.tooltip;

import com.google.common.collect.Maps;
import com.qiushui1012.mod.voidinair.init.block.ViaBlocks;
import com.qiushui1012.mod.voidinair.init.item.ViaItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;

public class ItemTooltipManager {
    public static final Component SHIFT_TIP = Component.translatable(
        "tooltip.anvilcraft.press_key",
        Component.literal("Shift").withStyle(ChatFormatting.WHITE)
    ).withStyle(ChatFormatting.DARK_GRAY);
    public static final Map<Item, String> NORMAL = Maps.newHashMap();
    public static final Map<Item, String> SHIFT = Maps.newHashMap();

    // 禁用行长度检查"""
    static {
        NORMAL.put(ViaBlocks.BLACK_CAT.asItem(), "Cute little black cat. §8§mThe Void§r");
        NORMAL.put(ViaItems.TOTEM_OF_VOID.asItem(), "Triggers when falling into the Void");
        NORMAL.put(ViaItems.VOID_AMULET.asItem(), "Grants immunity of Void");
        NORMAL.put(ViaBlocks.VOID_FOUNTAIN.asItem(), "Similar to Mineral Fountain, but can only produce the result of Void Decay");
        NORMAL.put(ViaItems.SNOWFLAKE_AMULET.asItem(), "Grants immunity of freeze and lets you to walk on the powder snow");
        NORMAL.put(ViaItems.BEEHIVE_AMULET.asItem(), "Grants immunity of bees and lets you to safely collect honeycombs and honey");
        NORMAL.put(ViaItems.GOLD_AMULET.asItem(), "Clothe you in golden armor in the eyes of the piglin");
        NORMAL.put(ViaItems.DOLPHIN_AMULET.asItem(), "Gives you Dolphin Grace III and making it less likely for the Guardians to notice");
        NORMAL.put(ViaItems.TRANSCENDED_AMULET.asItem(), "Includes all AnvilCraft's and Void in Air's amulet effects (except Abnormal Amulet)");

        SHIFT.put(ViaItems.TOTEM_OF_VOID.asItem(), "Triggers when falling into the Void.nWhen trigger, grants long enough Slow Falling effect, and teleports to the scaled xz coordinates of the max build height in the Overworld");
        SHIFT.put(ViaItems.VOID_AMULET.asItem(), "Grants immunity of Void\nWhen falling into the Void, grants long enough Slow Falling effect, and teleports to the scaled xz coordinates of the max build height in the Overworld");
        SHIFT.put(ViaBlocks.VOID_FOUNTAIN.asItem(), "Similar to Mineral Fountain, but can only produce the result of Void Decay\nTrying to generate blocks like Mineral Fountain can lead to some unusual things...");
    }
    // 启用行长度检查""";

    /**
     * 为模组物品添加工具提示
     *
     * @param stack    需要添加工具提示的物品堆叠
     * @param builder 提示内容
     */
    public static void addTooltip(ItemStack stack, Consumer<Component> builder, TooltipFlag flag) {
        Item item = stack.getItem();
        if (ItemTooltipManager.SHIFT.containsKey(item)) {
            if (flag.hasShiftDown()) {
                builder.accept(ItemTooltipManager.getShiftItemTooltip(item));
            } else {
                if (ItemTooltipManager.NORMAL.containsKey(item)) {
                    builder.accept(ItemTooltipManager.getItemTooltip(item));
                }
                builder.accept(ItemTooltipManager.SHIFT_TIP);
            }
        } else if (ItemTooltipManager.NORMAL.containsKey(item)) {
            builder.accept(ItemTooltipManager.getItemTooltip(item));
        }
    }

    private static Component getItemTooltip(Item item) {
        return Component.translatable(ItemTooltipManager.getTranslationKey(item)).withStyle(ChatFormatting.GRAY);
    }

    private static Component getShiftItemTooltip(Item item) {
        return Component.translatable(ItemTooltipManager.getShiftTranslationKey(item)).withStyle(ChatFormatting.GRAY);
    }

    public static String getTranslationKey(Item item) {
        Identifier key = BuiltInRegistries.ITEM.getKey(item);
        return "tooltip.%s.item.%s".formatted(key.getNamespace(), key.getPath());
    }

    public static String getShiftTranslationKey(Item item) {
        return ItemTooltipManager.getTranslationKey(item) + ".shift";
    }

    public static Map<Item, String> getNormalMap() {
        return Collections.unmodifiableMap(NORMAL);
    }

    public static Map<Item, String> getShiftMap() {
        return Collections.unmodifiableMap(SHIFT);
    }
}
