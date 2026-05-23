package com.qiushui1012.mod.voidinair.block.entity;

import com.qiushui1012.mod.voidinair.AncVoidInAir;
import dev.anvilcraft.lib.v2.util.MathUtil;
import dev.dubhe.anvilcraft.block.storage.VoidMatterBlock;
import dev.dubhe.anvilcraft.init.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

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
        this.resetCooldown();
        if (!MathUtil.isInRange(this.getBlockPos().getY(), level.getMinY(), level.getMinY() + 8)) return;

        BlockState aroundState = this.getAroundBlock(level);
        if (aroundState == null) return;
        BlockState aboveState = level.getBlockState(this.getBlockPos().above());
        if (!aboveState.isAir()) return;

        level.setBlockAndUpdate(this.getBlockPos().above(), this.getRandomizedResult(level, level.getRandom(), aroundState));
    }

    private @Nullable BlockState getAroundBlock(ServerLevel level) {
        BlockPos pos = this.getBlockPos();
        BlockState around = null;
        boolean statesNotMatch = false;
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockState state = level.getBlockState(pos.relative(direction));
            if (state.isAir()) return Blocks.AIR.defaultBlockState();
            if (around == null) {
                around = state;
            } else if (around.is(state.getBlock())) {
                statesNotMatch |= !this.isStatesMatch(around, state);
            } else {
                return null;
            }
        }
        return statesNotMatch ? Blocks.AIR.defaultBlockState() : around;
    }

    private boolean isStatesMatch(BlockState a, BlockState b) {
        if (!a.is(b.getBlock())) return false;
        for (Property<?> property : a.getProperties()) {
            if (
                !b.getOptionalValue(property)
                    .map(value -> Objects.equals(value, a.getValue(property)))
                    .orElse(false)
            ) {
                return false;
            }
        }
        return b.getFluidState().isEmpty() || !b.getFluidState().isSource();
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

    protected BlockState getRandomizedResult(Level level, RandomSource random, BlockState around) {
        float randomized = random.nextFloat();
        if (randomized < 0.2) {
            return ModBlocks.VOID_STONE.getDefaultState();
        } else if (randomized < 0.4) {
            return ModBlocks.EARTH_CORE_SHARD_ORE.getDefaultState();
        } else {
            if (!around.isAir() && randomized < 0.43) return around;
            return VoidMatterBlock.voidDecay(level, level.getRandom());
        }
    }
}
