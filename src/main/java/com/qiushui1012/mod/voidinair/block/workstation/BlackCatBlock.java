package com.qiushui1012.mod.voidinair.block.workstation;

import com.mojang.serialization.MapCodec;
import com.qiushui1012.mod.voidinair.init.ViaSoundEvents;
import com.qiushui1012.mod.voidinair.inventory.BlackCatMenu;
import dev.dubhe.anvilcraft.api.hammer.IHammerRemovable;
import dev.dubhe.anvilcraft.block.NeoforgeBlock;
import dev.dubhe.anvilcraft.block.better.BetterAnvilBlock;
import dev.dubhe.anvilcraft.init.ModMenuTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.CatVariant;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.util.DeferredSoundType;
import org.jetbrains.annotations.Nullable;

public class BlackCatBlock extends BetterAnvilBlock implements IHammerRemovable {
    public static final SoundType SOUND_TYPE = new DeferredSoundType(
        1F,
        1F,
        ViaSoundEvents.BLACK_CAT_BREAK,
        ViaSoundEvents.BLACK_CAT_STEP,
        ViaSoundEvents.BLACK_CAT_PLACE,
        ViaSoundEvents.BLACK_CAT_HIT,
        ViaSoundEvents.BLACK_CAT_FALL
    );
    private static final VoxelShape BASE = Block.box(2.0, 0.0, 2.0, 14.0, 4.0, 14.0);
    private static final VoxelShape X_LEG1 = Block.box(4.0, 4.0, 5.0, 12.0, 10.0, 11.0);
    private static final VoxelShape X_TOP = Block.box(0.0, 10.0, 3.0, 16.0, 16.0, 13.0);
    private static final VoxelShape Z_LEG1 = Block.box(5.0, 4.0, 4.0, 11.0, 10.0, 12.0);
    private static final VoxelShape Z_TOP = Block.box(3.0, 10.0, 0.0, 13.0, 16.0, 16.0);
    private static final VoxelShape X_AXIS_AABB = Shapes.or(BASE, X_LEG1, X_TOP);
    private static final VoxelShape Z_AXIS_AABB = Shapes.or(BASE, Z_LEG1, Z_TOP);
    public static final Component CONTAINER_TITLE = Component.translatable("container.repair");

    public BlackCatBlock(Properties properties) {
        super(properties);
    }

    @SuppressWarnings("UnreachableCode")
    @Override
    public InteractionResult use(
        BlockState state,
        Level level,
        BlockPos pos,
        Player player,
        InteractionHand hand,
        BlockHitResult hit
    ) {
        if (level.isClientSide) return InteractionResult.SUCCESS;
        ModMenuTypes.open((ServerPlayer) player, state.getMenuProvider(level, pos));
        player.awardStat(Stats.INTERACT_WITH_ANVIL);
        return InteractionResult.CONSUME;
    }

    @Override
    public MapCodec<AnvilBlock> codec() {
        return NeoforgeBlock.simpleCodec(BlackCatBlock::new);
    }

    @Override
    protected @Nullable MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return new SimpleMenuProvider(
            (syncId, inventory, player) -> new BlackCatMenu(syncId, inventory, ContainerLevelAccess.create(level, pos)),
            CONTAINER_TITLE
        );
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        return direction.getAxis() == Direction.Axis.X ? X_AXIS_AABB : Z_AXIS_AABB;
    }

    @Override
    public void onLand(Level level, BlockPos pos, BlockState state, BlockState replaceableState, FallingBlockEntity fallingBlock) {
    }

    @Override
    public void falling(FallingBlockEntity entity) {
        entity.setHurtsEntities(2.0f, 20);
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (entity instanceof Player) {
            level.playSound(null, pos, ViaSoundEvents.BLACK_CAT_LAND.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
        }
        super.fallOn(level, state, pos, entity, fallDistance);
    }

    @Override
    public void onBrokenAfterFall(Level level, BlockPos pos, FallingBlockEntity fallingBlock) {
    }

    public static void damage(Level level, BlockPos pos) {
        level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        level.playSound(
            null,
            pos,
            SoundEvents.CAT_EAT,
            SoundSource.BLOCKS,
            1.0F,
            level.getRandom().nextFloat() * 0.1F + 0.9F
        );
        Cat spawned = EntityType.CAT.spawn(
            (ServerLevel) level,
            null,
            null,
            pos,
            MobSpawnType.SPAWN_EGG,
            true,
            false
        );
        if (spawned != null) {
            spawned.setVariant(level.registryAccess().holderOrThrow(CatVariant.ALL_BLACK));
            if (level.getRandom().nextFloat() < 0.05F) spawned.setBaby(true);
            level.gameEvent(null, GameEvent.ENTITY_PLACE, pos);
        }
    }
}
