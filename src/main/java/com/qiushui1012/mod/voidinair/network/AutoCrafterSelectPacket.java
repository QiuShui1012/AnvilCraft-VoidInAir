package com.qiushui1012.mod.voidinair.network;

import com.qiushui1012.mod.voidinair.AncVoidInAir;
import com.qiushui1012.mod.voidinair.block.entity.AutoCrafterBlockEntity;
import com.qiushui1012.mod.voidinair.init.block.ViaBlockEntities;
import com.qiushui1012.mod.voidinair.inventory.AutoCrafterMenu;
import dev.anvilcraft.lib.v2.network.packet.IInsensitiveBiPacket;
import dev.anvilcraft.lib.v2.network.packet.IPacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.Optional;

public record AutoCrafterSelectPacket(int selecting, BlockPos pos) implements IInsensitiveBiPacket {
    public static final Type<AutoCrafterSelectPacket> TYPE = IPacket.type(AncVoidInAir.of("auto_crafter_select"));
    public static final StreamCodec<ByteBuf, AutoCrafterSelectPacket> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.VAR_INT,
        AutoCrafterSelectPacket::selecting,
        BlockPos.STREAM_CODEC,
        AutoCrafterSelectPacket::pos,
        AutoCrafterSelectPacket::new
    );

    @Override
    public Type<AutoCrafterSelectPacket> type() {
        return TYPE;
    }

    @Override
    public void handleOnBothSide(Player player) {
        Level level = player.level();
        if (!level.isLoaded(this.pos)) return;

        Optional<AutoCrafterBlockEntity> blockEntity = level.getBlockEntity(
            this.pos,
            ViaBlockEntities.AUTO_CRAFTER.get()
        );
        if (blockEntity.isEmpty()) return;
        AutoCrafterBlockEntity autoCrafter = blockEntity.get();

        if (!level.isClientSide()
            && (!(player.containerMenu instanceof AutoCrafterMenu menu) || menu.getBlockEntity() != autoCrafter)) {
            return;
        }
        autoCrafter.setSelecting(this.selecting);
        if (player.containerMenu instanceof AutoCrafterMenu menu && menu.getBlockEntity() == autoCrafter) {
            menu.refreshResult();
        }
    }
}
