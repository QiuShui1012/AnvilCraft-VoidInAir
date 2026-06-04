package com.qiushui1012.mod.voidinair.item.tooltip;

import com.google.common.collect.ImmutableMap;
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
import org.jetbrains.annotations.Unmodifiable;

import java.util.Map;
import java.util.function.Consumer;

public class ItemTooltipManager {
    public static final Component SHIFT_TIP = Component.translatable("tooltip.anvilcraft.press_key", "Shift")
        .withStyle(ChatFormatting.GRAY);
    private static final Map<Item, String> NORMAL = Maps.newHashMap();
    private static final Map<Item, String> SHIFT = Maps.newHashMap();

    // CHECKSTYLE.SUPPRESS: LineLength for +11 lines
    static {
        NORMAL.put(ViaBlocks.BLACK_CAT.asItem(), "Cute little black cat. §8§mThe Void.§r");
        NORMAL.put(ViaBlocks.VOID_FOUNTAIN.asItem(), "Similar to Mineral Fountain, but can only produce the result of Void Decay.\nTrying to generate blocks like Mineral Fountain can lead to some unusual things...");
        NORMAL.put(ViaItems.SNOWFLAKE_AMULET.asItem(), "Grants immunity of freeze and lets you to walk on the powder snow.");
        NORMAL.put(ViaItems.BEEHIVE_AMULET.asItem(), "Grants immunity of bees and lets you to safely collect honeycombs and honey.");
        NORMAL.put(ViaItems.GOLD_AMULET.asItem(), "Clothe you in golden armor in the eyes of the piglin.");
        NORMAL.put(ViaItems.DOLPHIN_AMULET.asItem(), "Gives you Dolphin Grace III and making it less likely for the Guardians to notice.");
        NORMAL.put(ViaItems.TRANSCENDED_AMULET.asItem(), "Includes all AnvilCraft's and Void in Air's amulet effects (except Abnormal Amulet).");

        SHIFT.put(ViaItems.TOTEM_OF_VOID.asItem(), "Triggers when falling into the Void. \nWhen trigger, grants long enough Slow Falling effect, and teleports to the scaled xz coordinates of the max build height in the Overworld");
        SHIFT.put(ViaItems.VOID_AMULET.asItem(), "Grants immunity of void.\nWhen falling into the Void, grants long enough Slow Falling effect, and teleports to the scaled xz coordinates of the max build height in the Overworld");

        ImmutableMap.Builder<Item, String> allTooltips = ImmutableMap.builder();
        allTooltips.putAll(NORMAL);
        allTooltips.putAll(SHIFT);
        NEED_TOOLTIP_ITEM = allTooltips.build();
    }

    @Unmodifiable
    public static final Map<Item, String> NEED_TOOLTIP_ITEM;

    /**
     * 为模组物品添加工具提示
     *
     * @param stack    需要添加工具提示的物品堆叠
     * @param builder 提示内容
     */
    public static void addTooltip(ItemStack stack, Consumer<Component> builder, TooltipFlag flag) {
        Item item = stack.getItem();
        if (NORMAL.containsKey(item)) {
            builder.accept(ItemTooltipManager.getItemTooltip(item));
        }
        if (SHIFT.containsKey(item)) {
            if (flag.hasShiftDown()) {
                builder.accept(ItemTooltipManager.getItemTooltip(item));
            } else {
                builder.accept(ItemTooltipManager.SHIFT_TIP);
            }
        }
    }

    private static Component getItemTooltip(Item item) {
        return Component.translatable(getTranslationKey(item)).withStyle(ChatFormatting.GRAY);
    }

    public static String getTranslationKey(Item item) {
        Identifier key = BuiltInRegistries.ITEM.getKey(item);
        return "tooltip.%s.item.%s".formatted(key.getNamespace(), key.getPath());
    }
}
