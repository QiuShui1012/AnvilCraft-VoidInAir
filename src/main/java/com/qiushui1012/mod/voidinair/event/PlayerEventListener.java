package com.qiushui1012.mod.voidinair.event;

import com.qiushui1012.mod.voidinair.item.property.amulet.BlackCatAmulet;
import dev.dubhe.anvilcraft.init.item.ModComponents;
import dev.dubhe.anvilcraft.item.property.component.amulet.IAmulet;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber
public class PlayerEventListener {
    @SubscribeEvent
    public static void onPlayerUse(PlayerInteractEvent.RightClickItem event) {
        ItemStack stack = event.getItemStack();
        if (stack.isEmpty() || !stack.has(ModComponents.AMULET)) return;

        IAmulet amulet = stack.get(ModComponents.AMULET);
        if (!(amulet instanceof BlackCatAmulet blackCat)) return;

        BlackCatAmulet signed = blackCat.sign(event.getEntity());
        if (blackCat == signed) {
            event.setCancellationResult(InteractionResult.FAIL);
            return;
        }
        if (event.getLevel().isClientSide()) {
            event.setCancellationResult(InteractionResult.SUCCESS);
            return;
        }
        stack.set(ModComponents.AMULET, signed);
        event.setCancellationResult(InteractionResult.SUCCESS_SERVER);
    }
}
