package com.qiushui1012.mod.eyesinair.block.entity;

import com.qiushui1012.mod.eyesinair.AncVoidInAir;
import dev.anvilcraft.lib.v2.util.MathUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class VoidFountainBlockEntity extends BlockEntity {
    private int cooldown = 0;

    public VoidFountainBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    // region 持久化
    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putInt("cooldown", this.cooldown);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.cooldown = input.getIntOr("cooldown", AncVoidInAir.CONFIG.voidFountainCooldown);
    }
    // endregion

    public void tick(ServerLevel level) {
        if (!this.processCooldown()) return;
        if (!MathUtil.isInRange(this.getBlockPos().getY(), level.getMinY(), level.getMinY() + 8)) return;

        BlockState aroundState = this.getAroundBlock(level);
        if (aroundState.isAir()) return;


    }

    private BlockState getAroundBlock(ServerLevel level) {
        BlockPos pos = this.getBlockPos();
        BlockState around = Blocks.AIR.defaultBlockState();
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockState state = level.getBlockState(pos.relative(direction));
            if (state.isAir()) return Blocks.AIR.defaultBlockState();
            if (around.isAir()) {
                around = state;
            } else if (
                !around.is(state.getBlock())
                || !state.getFluidState().isEmpty() && !state.getFluidState().isSource()
            ) {
                return Blocks.AIR.defaultBlockState();
            }
        }
        return around;
    }

    private boolean processCooldown() {
        if (this.cooldown > -1) {
            this.cooldown--;
        }
        return this.cooldown == 0;
    }

    public void resetCooldown() {
        if (this.cooldown <= 0) {
            this.cooldown = AncVoidInAir.CONFIG.voidFountainCooldown;
        }
    }
}
