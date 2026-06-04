package com.qiushui1012.mod.voidinair.event;

import com.qiushui1012.mod.voidinair.init.block.ViaBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;

@EventBusSubscriber
public class BlockEntityEventListener {
    @SubscribeEvent
    public static void on(BlockEntityTypeAddBlocksEvent event) {
        event.modify(BlockEntityType.SKULL, ViaBlocks.BLACK_CAT_HEAD.get(), ViaBlocks.BLACK_CAT_WALL_HEAD.get());
    }
}
