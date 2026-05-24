package com.qiushui1012.mod.voidinair.client.init;

import com.qiushui1012.mod.voidinair.api.skull.SimpleSkullBlockType;
import com.qiushui1012.mod.voidinair.init.block.VIABlocks;
import net.minecraft.client.renderer.block.BuiltInBlockModels;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterBlockModelsEvent;

@EventBusSubscriber(Dist.CLIENT)
public class VIABlockModels {
    @SubscribeEvent
    public static void on(RegisterBlockModelsEvent event) {
        BuiltInBlockModels.createMobHeads(
            event.getBuilder(),
            SimpleSkullBlockType.VOID,
            VIABlocks.BLACK_CAT_HEAD.get(),
            VIABlocks.BLACK_CAT_WALL_HEAD.get()
        );
    }
}
