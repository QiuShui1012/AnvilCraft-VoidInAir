package com.qiushui1012.mod.voidinair.block.decoration;

import com.qiushui1012.mod.voidinair.api.skull.SimpleSkullBlockType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallSkullBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;

public class BlackCatWallHeadBlock extends WallSkullBlock {
    private static final Map<Direction, VoxelShape> SHAPES = Shapes.rotateHorizontal(Block.box(5.5, 5.5, 10, 10.5, 10.5, 16.0));

    public BlackCatWallHeadBlock(Properties properties) {
        super(SimpleSkullBlockType.VOID, properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return BlackCatWallHeadBlock.SHAPES.get(state.getValue(WallSkullBlock.FACING));
    }
}
