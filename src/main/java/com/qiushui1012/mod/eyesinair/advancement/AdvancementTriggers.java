package com.qiushui1012.mod.eyesinair.advancement;

import com.qiushui1012.mod.eyesinair.init.block.VIACriterionTriggers;
import dev.dubhe.anvilcraft.util.PlayerUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

public class AdvancementTriggers {
    public static void voidFountainCreate(Level level, BlockPos pos) {
        if (level.isClientSide()) return;
        for (ServerPlayer player : PlayerUtil.searchPlayerByPos(level, pos, 7)) {
            VIACriterionTriggers.VOID_FOUNTAIN_CREATE.get().trigger(player);
        }
    }
}
