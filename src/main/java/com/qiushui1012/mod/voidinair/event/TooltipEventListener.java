package com.qiushui1012.mod.voidinair.event;

import com.qiushui1012.mod.voidinair.item.tooltip.ItemTooltipManager;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

@EventBusSubscriber
public class TooltipEventListener {
    @SuppressWarnings("unused")
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onTooltip(ItemTooltipEvent event) {
        ItemTooltipManager.addTooltip(event.getItemStack(), event.getToolTip(), event.getFlags());
    }
}
