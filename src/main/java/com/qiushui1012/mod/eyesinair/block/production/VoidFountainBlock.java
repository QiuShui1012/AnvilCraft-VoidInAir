package com.qiushui1012.mod.eyesinair.block.production;

import com.mojang.serialization.MapCodec;
import com.qiushui1012.mod.eyesinair.advancement.AdvancementTriggers;
import com.qiushui1012.mod.eyesinair.block.entity.VoidFountainBlockEntity;
import com.qiushui1012.mod.eyesinair.init.block.VIABlockEntities;
import dev.anvilcraft.lib.v2.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public class VoidFountainBlock extends BaseEntityBlock {
    public VoidFountainBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<VoidFountainBlock> codec() {
        return Block.simpleCodec(VoidFountainBlock::new);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return VIABlockEntities.VOID_FOUNTAIN.create(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide()) return null;
        return BaseEntityBlock.createTickerHelper(
            type,
            VIABlockEntities.VOID_FOUNTAIN.get(),
            (tickLevel, _, _, be) -> be.tick(Util.cast(tickLevel))
        );
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        this.resetCooldown(level, pos);
        if (!oldState.is(state.getBlock())) {
            AdvancementTriggers.voidFountainCreate(level, pos);
        }
        super.onPlace(state, level, pos, oldState, movedByPiston);
    }

    @Override
    protected BlockState updateShape(
        BlockState state,
        LevelReader level,
        ScheduledTickAccess ticks,
        BlockPos pos,
        Direction directionToNeighbour,
        BlockPos neighbourPos,
        BlockState neighbourState,
        RandomSource random
    ) {
        if (level instanceof LevelAccessor accessor) {
            this.resetCooldown(accessor, pos);
        }
        return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    }

    private void resetCooldown(LevelAccessor level, BlockPos pos) {
        if (level.isClientSide()) return;
        level.getBlockEntity(pos, VIABlockEntities.VOID_FOUNTAIN.get())
            .ifPresent(VoidFountainBlockEntity::resetCooldown);
    }
}
