package com.qiushui1012.mod.voidinair.block.decoration;

import com.qiushui1012.mod.voidinair.api.skull.SimpleSkullBlockType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallSkullBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;

public class BlackCatWallHeadBlock extends WallSkullBlock {
    private static final Map<Direction, VoxelShape> SHAPES = Map.of(
        Direction.NORTH, Block.box(5.5, 5.5, 10, 10.5, 10.5, 16.0),
        Direction.SOUTH, Block.box(5.5, 5.5, 0, 10.5, 10.5, 6.0),
        Direction.EAST, Block.box(0, 5.5, 5.5, 6.0, 10.5, 10.5),
        Direction.WEST, Block.box(10, 5.5, 5.5, 16.0, 10.5, 10.5)
    );

    public BlackCatWallHeadBlock(Properties properties) {
        super(SimpleSkullBlockType.VOID, properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return BlackCatWallHeadBlock.SHAPES.get(state.getValue(WallSkullBlock.FACING));
    }
}
