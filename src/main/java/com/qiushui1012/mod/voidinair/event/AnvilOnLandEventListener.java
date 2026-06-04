package com.qiushui1012.mod.voidinair.event;

import com.qiushui1012.mod.voidinair.block.workstation.BlackCatBlock;
import com.qiushui1012.mod.voidinair.init.block.ViaBlocks;
import dev.anvilcraft.lib.v2.util.MathUtil;
import dev.dubhe.anvilcraft.api.event.AnvilEvent;
import dev.dubhe.anvilcraft.init.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber
public class AnvilOnLandEventListener {
    @SubscribeEvent
    public static void handleVoidFountainCreate(AnvilEvent.OnLand event) {
        if (!event.getEntity().getBlockState().is(ModBlocks.TRANSCENDENCE_ANVIL)) return;
        if (event.getFallDistance() + 1 < 20) return;

        ServerLevel level = event.getLevel();
        BlockPos anvilPos = event.getPos();
        if (!MathUtil.isInRange(anvilPos.getY(), level.getMinY(), level.getMinY() + 10)) return;

        BlockPos voidMatterBlockPos = anvilPos.below();
        if (!level.getBlockState(voidMatterBlockPos).is(ModBlocks.VOID_MATTER_BLOCK)) return;
        BlockPos impactPilePos = voidMatterBlockPos.below();
        if (!level.getBlockState(impactPilePos).is(ModBlocks.IMPACT_PILE)) return;
        BlockPos fountainPos = impactPilePos.below();
        if (!level.getBlockState(fountainPos).is(ModBlocks.MINERAL_FOUNTAIN)) return;

        level.removeBlock(anvilPos, false);
        level.removeBlock(voidMatterBlockPos, false);
        level.removeBlock(impactPilePos, false);
        level.setBlockAndUpdate(fountainPos, ViaBlocks.VOID_FOUNTAIN.getDefaultState());
    }

    @SubscribeEvent
    public static void handleBlackCatAnvilOnLand(AnvilEvent.OnLand event) {
        if (event.getEntity().getBlockState().is(ViaBlocks.BLACK_CAT)) {
            if (event.getFallDistance() > 1) {
                ServerLevel level = event.getLevel();
                if (level.getRandom().nextFloat() < 0.01F) {
                    BlackCatBlock.damage(level, event.getPos());
                }
            }
        }
    }
}
