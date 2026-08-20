package com.qiushui1012.mod.voidinair.block.decoration;

import com.qiushui1012.mod.voidinair.api.skull.SimpleSkullBlockType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlackCatHeadBlock extends SkullBlock {
    private static final VoxelShape SHAPE = Block.box(5.5, 0.0, 5.5, 10.5, 5.0, 10.5);

    public BlackCatHeadBlock(Properties properties) {
        super(SimpleSkullBlockType.VOID, properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return BlackCatHeadBlock.SHAPE;
    }
}
