package com.qiushui1012.mod.voidinair.block.production;

import com.mojang.serialization.MapCodec;
import com.qiushui1012.mod.voidinair.block.entity.AutoCrafterBlockEntity;
import com.qiushui1012.mod.voidinair.init.block.ViaBlockEntities;
import dev.dubhe.anvilcraft.block.power.batch.BaseBatchCraftingBlock;
import dev.dubhe.anvilcraft.network.MachineEnableFilterPacket;
import dev.dubhe.anvilcraft.network.MachineOutputDirectionPacket;
import dev.dubhe.anvilcraft.network.SlotDisableChangePacket;
import dev.dubhe.anvilcraft.network.SlotFilterChangePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jspecify.annotations.Nullable;

public class AutoCrafterBlock extends BaseBatchCraftingBlock {
    public AutoCrafterBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<AutoCrafterBlock> codec() {
        return Block.simpleCodec(AutoCrafterBlock::new);
    }

    @Override
    protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos, Direction direction) {
        if (level.getBlockEntity(pos) instanceof AutoCrafterBlockEntity autoCrafter) {
            return autoCrafter.getRedstoneSignal();
        }
        return 0;
    }

    @Override
    protected InteractionResult playerUse(
        Level level,
        BlockPos pos,
        BlockState state,
        @Nullable BlockEntity blockEntity,
        Player player,
        InteractionHand hand,
        BlockHitResult hit
    ) {
        if (!(blockEntity instanceof AutoCrafterBlockEntity autoCrafter)) return InteractionResult.PASS;
        if (!(player instanceof ServerPlayer serverPlayer)) return InteractionResult.PASS;
        if (serverPlayer.gameMode.getGameModeForPlayer() == GameType.SPECTATOR) return InteractionResult.PASS;

        serverPlayer.openMenu(autoCrafter, pos);
        PacketDistributor.sendToPlayer(serverPlayer, new MachineOutputDirectionPacket(autoCrafter.getDirection()));
        PacketDistributor.sendToPlayer(serverPlayer, new MachineEnableFilterPacket(autoCrafter.isFilterEnabled()));
        for (int i = 0; i < autoCrafter.getFilteredItems().size(); i++) {
            PacketDistributor.sendToPlayer(
                serverPlayer,
                new SlotDisableChangePacket(i, autoCrafter.getFilteredItemStackHandler().getDisabled().get(i))
            );
            PacketDistributor.sendToPlayer(serverPlayer, new SlotFilterChangePacket(i, autoCrafter.getFilter(i)));
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return ViaBlockEntities.AUTO_CRAFTER.create(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(
        Level level,
        BlockState state,
        BlockEntityType<T> type
    ) {
        if (level.isClientSide()) return null;
        return BaseEntityBlock.createTickerHelper(
            type,
            ViaBlockEntities.AUTO_CRAFTER.get(),
            (tickLevel, pos, _, autoCrafter) -> autoCrafter.tick(tickLevel, pos)
        );
    }

    @Override
    public Item getToastSymbol() {
        return Items.CRAFTING_TABLE;
    }
}
