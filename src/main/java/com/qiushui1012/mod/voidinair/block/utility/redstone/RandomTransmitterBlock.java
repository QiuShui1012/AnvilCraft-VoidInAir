package com.qiushui1012.mod.voidinair.block.utility.redstone;

import com.google.common.collect.ImmutableMap;
import com.qiushui1012.mod.voidinair.init.block.ViaBlocks;
import dev.anvilcraft.lib.v2.util.ShapeUtil;
import dev.dubhe.anvilcraft.init.item.ModItemTags;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SignalGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;

public class RandomTransmitterBlock extends Block {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final EnumProperty<Mode> NORTH = EnumProperty.create("north", Mode.class);
    public static final EnumProperty<Mode> SOUTH = EnumProperty.create("south", Mode.class);
    public static final EnumProperty<Mode> EAST = EnumProperty.create("east", Mode.class);
    public static final EnumProperty<Mode> WEST = EnumProperty.create("west", Mode.class);
    public static final EnumProperty<Mode> UP = EnumProperty.create("up", Mode.class);
    public static final EnumProperty<Mode> DOWN = EnumProperty.create("down", Mode.class);
    public static final Map<Direction, EnumProperty<Mode>> DIRECTION_TO_PROPERTY = ImmutableMap.of(
        Direction.NORTH,
        RandomTransmitterBlock.NORTH,
        Direction.SOUTH,
        RandomTransmitterBlock.SOUTH,
        Direction.EAST,
        RandomTransmitterBlock.EAST,
        Direction.WEST,
        RandomTransmitterBlock.WEST,
        Direction.UP,
        RandomTransmitterBlock.UP,
        Direction.DOWN,
        RandomTransmitterBlock.DOWN
    );

    public RandomTransmitterBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(
            this.getStateDefinition().any()
                .setValue(RandomTransmitterBlock.POWERED, false)
                .setValue(RandomTransmitterBlock.NORTH, Mode.NONE)
                .setValue(RandomTransmitterBlock.SOUTH, Mode.NONE)
                .setValue(RandomTransmitterBlock.EAST, Mode.NONE)
                .setValue(RandomTransmitterBlock.WEST, Mode.NONE)
                .setValue(RandomTransmitterBlock.UP, Mode.NONE)
                .setValue(RandomTransmitterBlock.DOWN, Mode.NONE)
        );
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        if (!state.isAir()) return null;
        Direction face = context.getClickedFace();
        return this.defaultBlockState()
            .setValue(RandomTransmitterBlock.POWERED, level.getBestNeighborSignal(pos) > 0)
            .setValue(RandomTransmitterBlock.DIRECTION_TO_PROPERTY.get(face.getOpposite()), Mode.INACTIVE);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(RandomTransmitterBlock.POWERED).add(
            RandomTransmitterBlock.NORTH,
            RandomTransmitterBlock.SOUTH,
            RandomTransmitterBlock.EAST,
            RandomTransmitterBlock.WEST,
            RandomTransmitterBlock.UP,
            RandomTransmitterBlock.DOWN
        );
    }

    @Override
    protected ItemInteractionResult useItemOn(
        ItemStack stack,
        BlockState state,
        Level level,
        BlockPos pos,
        Player player,
        InteractionHand hand,
        BlockHitResult hitResult
    ) {
        if (stack.isEmpty()) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

        if (!RandomTransmitterBlock.CUBE_BB.move(pos).contains(hitResult.getLocation())) {
            if (stack.is(ModItemTags.ANVIL_HAMMER) && player.isShiftKeyDown()) {
                return this.tryRemoveTransmitter(state, level, pos, player, hitResult);
            }
            return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
        }

        if (stack.is(ViaBlocks.RANDOM_TRANSMITTER.asItem())) {
            Direction face = hitResult.getDirection();
            EnumProperty<Mode> property = RandomTransmitterBlock.DIRECTION_TO_PROPERTY.get(face);
            if (state.getValue(property).hasTransmitter()) return super.useItemOn(stack, state, level, pos, player, hand, hitResult);

            if (level.isClientSide()) return ItemInteractionResult.sidedSuccess(level.isClientSide());
            level.setBlockAndUpdate(
                pos,
                state.setValue(property, Mode.fromActive(state.getValue(property).isActive()))
            );
            level.playSound(null, pos, state.getSoundType(level, pos, null).getPlaceSound(), SoundSource.BLOCKS);
            if (!player.hasInfiniteMaterials()) stack.shrink(1);
            return ItemInteractionResult.sidedSuccess(level.isClientSide());
        } else if (stack.is(ModItemTags.ANVIL_HAMMER)) {
            ItemInteractionResult result = this.tryMoveTransmitters(state, level, pos, hitResult);
            return result == null ? super.useItemOn(stack, state, level, pos, player, hand, hitResult) : result;
        }

        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    private ItemInteractionResult tryRemoveTransmitter(
        BlockState state,
        Level level,
        BlockPos pos,
        Player player,
        BlockHitResult hitResult
    ) {
        Vec3 hitLoc = hitResult.getLocation();
        Direction side = null;
        for (Direction dir : Direction.values()) {
            if (!RandomTransmitterBlock.TRANSMITTER_BBS.get(dir).move(pos).contains(hitLoc)) continue;
            side = dir;
            break;
        }
        if (side == null) return ItemInteractionResult.FAIL;

        EnumProperty<Mode> property = RandomTransmitterBlock.DIRECTION_TO_PROPERTY.get(side);
        if (!state.getValue(property).hasTransmitter()) return ItemInteractionResult.FAIL;

        if (level.isClientSide()) return ItemInteractionResult.sidedSuccess(level.isClientSide());
        boolean someRemain = false;
        for (Direction dir : Direction.values()) {
            if (dir == side) continue;
            if (!state.getValue(RandomTransmitterBlock.DIRECTION_TO_PROPERTY.get(dir)).hasTransmitter()) continue;
            someRemain = true;
            break;
        }
        if (someRemain) {
            level.setBlockAndUpdate(pos, state.setValue(property, Mode.NONE));
        } else {
            level.removeBlock(pos, false);
        }
        if (!player.hasInfiniteMaterials()) player.getInventory().placeItemBackInInventory(ViaBlocks.RANDOM_TRANSMITTER.asStack());
        return ItemInteractionResult.sidedSuccess(level.isClientSide());
    }

    private @Nullable ItemInteractionResult tryMoveTransmitters(
        BlockState state,
        Level level,
        BlockPos pos,
        BlockHitResult hitResult
    ) {
        Direction face = hitResult.getDirection();
        EnumProperty<Mode> property = RandomTransmitterBlock.DIRECTION_TO_PROPERTY.get(face);

        // 若点击侧已有发信器，则尝试将其移动到点击侧的对侧
        if (state.getValue(property).hasTransmitter()) {
            EnumProperty<Mode> oppositeProperty = RandomTransmitterBlock.DIRECTION_TO_PROPERTY.get(face.getOpposite());
            // 若点击侧的对侧已有发信器则返回
            if (state.getValue(oppositeProperty).hasTransmitter()) return null;

            if (level.isClientSide()) return ItemInteractionResult.sidedSuccess(level.isClientSide());
            level.setBlockAndUpdate(
                pos,
                state
                    .setValue(property, Mode.NONE)
                    .setValue(oppositeProperty, Mode.fromActive(state.getValue(property).isActive()))
            );
            return ItemInteractionResult.sidedSuccess(level.isClientSide());
        }

        // 若点击侧没有发信器

        // 尝试从点击侧的对侧移动发信器到点击侧
        ItemInteractionResult result = this.moveTransmitterToAnotherSide(state, level, pos, face.getOpposite(), property);
        if (result != null) return result;

        // 尝试从另外四侧移动发信器到点击侧
        boolean first = false;
        Direction.AxisDirection second = face.getAxisDirection();
        for (Direction.Axis axis : new Direction.Axis[] {Direction.Axis.Y, Direction.Axis.Z, Direction.Axis.X}) {
            if (axis == face.getAxis()) continue;

            Direction side;
            if (!first) {
                first = true;
                side = Direction.fromAxisAndDirection(axis, Direction.AxisDirection.POSITIVE);
            } else {
                side = Direction.get(second, axis);
            }

            result = this.moveTransmitterToAnotherSide(state, level, pos, side, property);
            if (result != null) return result;
        }
        for (Direction.Axis axis : new Direction.Axis[] {Direction.Axis.Y, Direction.Axis.Z, Direction.Axis.X}) {
            if (axis == face.getAxis()) continue;

            Direction side;
            if (first) {
                first = false;
                side = Direction.fromAxisAndDirection(axis, Direction.AxisDirection.NEGATIVE);
            } else {
                side = Direction.get(second.opposite(), axis);
            }

            result = this.moveTransmitterToAnotherSide(state, level, pos, side, property);
            if (result != null) return result;
        }

        return null;
    }

    private @Nullable ItemInteractionResult moveTransmitterToAnotherSide(
        BlockState state,
        Level level,
        BlockPos pos,
        Direction side,
        EnumProperty<Mode> target
    ) {
        EnumProperty<Mode> source = RandomTransmitterBlock.DIRECTION_TO_PROPERTY.get(side);
        if (state.getValue(source).hasTransmitter()) {
            if (level.isClientSide()) return ItemInteractionResult.sidedSuccess(level.isClientSide());
            level.setBlockAndUpdate(
                pos,
                state
                    .setValue(target, Mode.fromActive(state.getValue(source).isActive()))
                    .setValue(source, Mode.NONE)
            );
            return ItemInteractionResult.sidedSuccess(level.isClientSide());
        }
        return null;
    }

    @Override
    protected boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    protected int getDirectSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return state.getSignal(level, pos, direction);
    }

    @Override
    protected int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        EnumProperty<Mode> property = RandomTransmitterBlock.DIRECTION_TO_PROPERTY.get(direction.getOpposite());
        return state.getValue(property).isActive() ? 15 : 0;
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if (state.getBlock() == oldState.getBlock()) return;
        level.scheduleTick(pos, this, 1);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        this.checkPowered(level, pos, state);
        if (state.getValue(RandomTransmitterBlock.POWERED)) return;

        for (Direction dir : Direction.values()) {
            EnumProperty<Mode> property = RandomTransmitterBlock.DIRECTION_TO_PROPERTY.get(dir);
            Mode mode = state.getValue(property);
            if (!mode.hasTransmitter()) continue;

            boolean shouldActive = random.nextBoolean();
            if (mode.isActive() == shouldActive) continue;

            level.setBlockAndUpdate(pos, state.setValue(property, Mode.fromActive(shouldActive)));
            BlockPos front = pos.relative(dir);
            level.neighborChanged(front, this, pos);
            level.updateNeighborsAtExceptFromFacing(front, this, dir.getOpposite());
        }
        level.scheduleTick(pos, this, 1);
    }

    @Override
    protected void neighborChanged(
        BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos,
        boolean movedByPiston
    ) {
        this.checkPowered(level, pos, state);
    }

    private void checkPowered(Level level, BlockPos pos, BlockState state) {
        boolean shouldPowered = this.hasBeenPowered(level, pos, state);
        if (state.getValue(RandomTransmitterBlock.POWERED) == shouldPowered) return;

        state = state.setValue(RandomTransmitterBlock.POWERED, shouldPowered);
        level.setBlock(pos, state, 2);

        for (Direction dir : Direction.values()) {
            EnumProperty<Mode> property = RandomTransmitterBlock.DIRECTION_TO_PROPERTY.get(dir);
            if (state.getValue(property).isInactive()) continue;
            state = state.setValue(property, state.getValue(property).toInactive());
            level.setBlockAndUpdate(pos, state);
            BlockPos front = pos.relative(dir);
            level.neighborChanged(front, this, pos);
            level.updateNeighborsAtExceptFromFacing(front, this, dir.getOpposite());
        }

        level.updateNeighborsAt(pos, this);
        if (!level.getBlockTicks().hasScheduledTick(pos, this)) {
            level.scheduleTick(pos, this, 1);
        }
    }

    private boolean hasBeenPowered(Level level, BlockPos pos, BlockState state) {
        for (Direction dir : SignalGetter.DIRECTIONS) {
            EnumProperty<Mode> property = RandomTransmitterBlock.DIRECTION_TO_PROPERTY.get(dir);
            if (state.getValue(property).hasTransmitter()) continue;

            BlockPos side = pos.relative(dir);
            BlockState sideState = level.getBlockState(side);
            if (sideState.getSignal(level, side, dir) > 0) return true;
            if (sideState.shouldCheckWeakPower(level, side, dir) && level.getDirectSignal(side, dir) > 0) return true;
        }
        return false;
    }

    public static LootItemConditionalFunction.Builder<?> side(EnumProperty<Mode> property, RandomTransmitterBlock block) {
        return SetItemCountFunction.setCount(ConstantValue.exactly(-1.0f), true)
            .when(
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                    .setProperties(
                        StatePropertiesPredicate.Builder.properties()
                            .hasProperty(property, "none")
                    )
            );
    }

    public enum Mode implements StringRepresentable {
        NONE,
        INACTIVE,
        ACTIVE,
        ;

        public boolean hasTransmitter() {
            return this != Mode.NONE;
        }

        public boolean isActive() {
            return this == Mode.ACTIVE;
        }

        public boolean isInactive() {
            return this == Mode.INACTIVE;
        }

        public static Mode fromActive(boolean active) {
            return active ? Mode.ACTIVE : Mode.INACTIVE;
        }

        public Mode toInactive() {
            return this.hasTransmitter() ? Mode.INACTIVE : this;
        }

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }

    // region Shapes

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return this.getShapes(state).get(state);
    }

    private Map<BlockState, VoxelShape> getShapes(BlockState state) {
        if (this.shapes != null) return this.shapes;
        return this.shapes = RandomTransmitterBlock.constructShapes(state);
    }

    public static final AABB CUBE_BB = new AABB(0.375, 0.375, 0.375, 0.625, 0.625, 0.625)
        .inflate(0.001);
    public static final VoxelShape CUBE = Block.box(6, 6, 6, 10, 10, 10);
    public static final Map<Direction, AABB> TRANSMITTER_BBS = ImmutableMap.of(
        Direction.NORTH,
        new AABB(0.4375, 0.4375, 0.0, 0.5625, 0.5625, 0.4375).inflate(0.001),
        Direction.SOUTH,
        new AABB(0.4375, 0.4375, 0.5625, 0.5625, 0.5625, 1.0).inflate(0.001),
        Direction.WEST,
        new AABB(0.0, 0.4375, 0.4375, 0.4375, 0.5625, 0.5625).inflate(0.001),
        Direction.EAST,
        new AABB(0.5625, 0.4375, 0.4375, 1.0, 0.5625, 0.5625).inflate(0.001),
        Direction.DOWN,
        new AABB(0.4375, 0.0, 0.4375, 0.5625, 0.4375, 0.5625).inflate(0.001),
        Direction.UP,
        new AABB(0.4375, 0.5625, 0.4375, 0.5625, 1.0, 0.5625).inflate(0.001)
    );
    private static final VoxelShape TRANSMITTER_NORTH = Block.box(7, 7, 0, 9, 9, 7);
    public static final Map<Direction, VoxelShape> TRANSMITTERS = ImmutableMap.of(
        Direction.NORTH,
        RandomTransmitterBlock.TRANSMITTER_NORTH,
        Direction.SOUTH,
        ShapeUtil.rotate(Direction.Axis.Y, 180, RandomTransmitterBlock.TRANSMITTER_NORTH),
        Direction.WEST,
        ShapeUtil.rotate(Direction.Axis.Y, 90, RandomTransmitterBlock.TRANSMITTER_NORTH),
        Direction.EAST,
        ShapeUtil.rotate(Direction.Axis.Y, 270, RandomTransmitterBlock.TRANSMITTER_NORTH),
        Direction.DOWN,
        ShapeUtil.rotate(Direction.Axis.X, 90, RandomTransmitterBlock.TRANSMITTER_NORTH),
        Direction.UP,
        ShapeUtil.rotate(Direction.Axis.X, 270, RandomTransmitterBlock.TRANSMITTER_NORTH)
    );
    private @Nullable Map<BlockState, VoxelShape> shapes;

    public static Map<BlockState, VoxelShape> constructShapes(BlockState defaultState) {
        ImmutableMap.Builder<BlockState, VoxelShape> shapes = ImmutableMap.builder();

        Mode[] modes = Mode.values();
        int modeCount = modes.length;
        int totalCombinations = 2 * (int) Math.pow(modeCount, 6); // 2 * 3^6

        for (int i = 0; i < totalCombinations; i++) {
            int temp = i;

            // 解码第一个参数（boolean）
            final boolean active = (temp % 2 == 1);
            temp /= 2;

            // 解码6个Mode参数（六进制）
            final Mode up = modes[temp % modeCount];
            temp /= modeCount;
            final Mode down = modes[temp % modeCount];
            temp /= modeCount;
            final Mode north = modes[temp % modeCount];
            temp /= modeCount;
            final Mode south = modes[temp % modeCount];
            temp /= modeCount;
            final Mode east = modes[temp % modeCount];
            temp /= modeCount;
            final Mode west = modes[temp % modeCount];

            BlockState state = defaultState
                .setValue(RandomTransmitterBlock.POWERED, active)
                .setValue(RandomTransmitterBlock.UP, up)
                .setValue(RandomTransmitterBlock.DOWN, down)
                .setValue(RandomTransmitterBlock.NORTH, north)
                .setValue(RandomTransmitterBlock.SOUTH, south)
                .setValue(RandomTransmitterBlock.EAST, east)
                .setValue(RandomTransmitterBlock.WEST, west);

            VoxelShape shape = RandomTransmitterBlock.buildShape(up, down, north, south, east, west);
            shapes.put(state, shape);
        }

        return shapes.buildKeepingLast();
    }

    private static VoxelShape buildShape(Mode up, Mode down, Mode north, Mode south, Mode east, Mode west) {
        // 基础碰撞箱 - 始终包含CUBE
        VoxelShape shape = RandomTransmitterBlock.CUBE;

        // 为每个不为NONE的面添加对应的发射器碰撞箱
        if (up != Mode.NONE) {
            shape = Shapes.or(shape, RandomTransmitterBlock.TRANSMITTERS.get(Direction.UP));
        }
        if (down != Mode.NONE) {
            shape = Shapes.or(shape, RandomTransmitterBlock.TRANSMITTERS.get(Direction.DOWN));
        }
        if (north != Mode.NONE) {
            shape = Shapes.or(shape, RandomTransmitterBlock.TRANSMITTERS.get(Direction.NORTH));
        }
        if (south != Mode.NONE) {
            shape = Shapes.or(shape, RandomTransmitterBlock.TRANSMITTERS.get(Direction.SOUTH));
        }
        if (east != Mode.NONE) {
            shape = Shapes.or(shape, RandomTransmitterBlock.TRANSMITTERS.get(Direction.EAST));
        }
        if (west != Mode.NONE) {
            shape = Shapes.or(shape, RandomTransmitterBlock.TRANSMITTERS.get(Direction.WEST));
        }

        return shape;
    }
    // endregion
}
