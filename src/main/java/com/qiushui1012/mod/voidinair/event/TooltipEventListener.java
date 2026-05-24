package com.qiushui1012.mod.voidinair.event;

import com.qiushui1012.mod.voidinair.item.tooltip.ItemTooltipManager;
import dev.dubhe.anvilcraft.api.event.AppendCustomHoverTextEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

import java.util.function.Consumer;

@EventBusSubscriber
public class TooltipEventListener {
    @SubscribeEvent
    public static void onTooltip(AppendCustomHoverTextEvent event) {
        final ItemStack stack = event.getStack();
        final Item.TooltipContext ctx = event.getContext();
        final TooltipDisplay display = event.getDisplay();
        final Consumer<Component> builder = event.getBuilder();
        final TooltipFlag flag = event.getTooltipFlag();
        final boolean shift = flag.hasShiftDown();

        ItemTooltipManager.addTooltip(stack, builder, flag);
    }
}
