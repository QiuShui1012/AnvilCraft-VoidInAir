package com.qiushui1012.mod.voidinair.event;

import com.qiushui1012.mod.voidinair.AncVoidInAir;
import com.qiushui1012.mod.voidinair.init.block.ViaBlockEntities;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@EventBusSubscriber(modid = AncVoidInAir.MOD_ID)
public class ViaCapabilities {
    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
            Capabilities.ItemHandler.BLOCK,
            ViaBlockEntities.AUTO_CRAFTER.get(),
            (autoCrafter, ignored) -> autoCrafter.getItemHandler()
        );
    }
}
