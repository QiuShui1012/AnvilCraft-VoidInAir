package com.qiushui1012.mod.voidinair.item.tooltip;

import com.google.common.collect.Maps;
import com.qiushui1012.mod.voidinair.init.block.ViaBlocks;
import com.qiushui1012.mod.voidinair.init.item.ViaItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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
        NORMAL.put(ViaItems.PUMPKIN_AMULET.asItem(), "Put on a virtual Carved Pumpkin for you");
        NORMAL.put(ViaItems.ANVILCRAFT_AMULET.asItem(), "Includes all AnvilCraft's amulet effects (except Comrade Amulet)");
        NORMAL.put(ViaItems.BLACK_CAT_AMULET.asItem(), "Includes all AnvilCraft's and Void in Air's amulet effects");
        NORMAL.put(ViaBlocks.RANDOM_TRANSMITTER.asItem(), "Random transmitting redstone pulses");

        SHIFT.put(ViaItems.TOTEM_OF_VOID.asItem(), "Triggers when falling into the Void\nWhen trigger, grants long enough Slow Falling effect, and teleports to the scaled xz coordinates of the max build height in the Overworld");
        SHIFT.put(ViaItems.VOID_AMULET.asItem(), "Grants immunity of Void\nWhen falling into the Void, grants long enough Slow Falling effect, and teleports to the scaled xz coordinates of the max build height in the Overworld");
        SHIFT.put(ViaBlocks.VOID_FOUNTAIN.asItem(), "Similar to Mineral Fountain, but can only produce the result of Void Decay\nTrying to generate blocks like Mineral Fountain can lead to some unusual things…");
    }
    // 启用行长度检查""";

    /**
     * 为模组物品添加工具提示
     *
     * @param stack    需要添加工具提示的物品堆叠
     */
    public static void addTooltip(ItemStack stack, List<Component> tooltip, TooltipFlag flag) {
        Item item = stack.getItem();
        final int initialTooltipSize = tooltip.size();
        if (ItemTooltipManager.SHIFT.containsKey(item)) {
            if (flag.hasShiftDown()) {
                ItemTooltipManager.addShiftTooltip(tooltip, item);
            } else {
                if (ItemTooltipManager.NORMAL.containsKey(item)) {
                    ItemTooltipManager.addNormalTooltip(tooltip, item);
                }
                int lines = tooltip.size() - initialTooltipSize;
                tooltip.add(1 + lines, ItemTooltipManager.SHIFT_TIP);
            }
        } else if (ItemTooltipManager.NORMAL.containsKey(item)) {
            ItemTooltipManager.addNormalTooltip(tooltip, item);
        }
    }

    private static void addNormalTooltip(List<Component> tooltip, Item item) {
        ItemTooltipManager.addTranslatedTooltip(tooltip, ItemTooltipManager.getTranslationKey(item));
    }

    private static void addShiftTooltip(List<Component> tooltip, Item item) {
        ItemTooltipManager.addTranslatedTooltip(tooltip, ItemTooltipManager.getShiftTranslationKey(item));
    }

    /**
     * 添加翻译后的tooltip，自动将 \n 拆分为多行
     */
    private static void addTranslatedTooltip(List<Component> tooltip, String key) {
        String text = I18n.get(key);
        String[] lines = text.split("\n");
        for (int i = lines.length - 1; i >= 0; i--) {
            tooltip.add(1, Component.literal(lines[i]).withStyle(ChatFormatting.GRAY));
        }
    }

    public static String getTranslationKey(Item item) {
        ResourceLocation key = BuiltInRegistries.ITEM.getKey(item);
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
