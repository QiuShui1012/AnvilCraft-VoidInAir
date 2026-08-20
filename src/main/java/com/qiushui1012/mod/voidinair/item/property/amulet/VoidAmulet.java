package com.qiushui1012.mod.voidinair.item.property.amulet;

import com.mojang.serialization.MapCodec;
import com.qiushui1012.mod.voidinair.init.item.ViaAmuletTypes;
import com.qiushui1012.mod.voidinair.item.totem.TotemOfVoidHandler;
import dev.dubhe.anvilcraft.item.property.component.amulet.IAmulet;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public record VoidAmulet() implements IAmulet {
    public static final VoidAmulet INSTANCE = new VoidAmulet();

    @Override
    public void inventoryTick(ServerPlayer player, ItemStack amulet, boolean isEnabled) {
        if (!isEnabled) return;
        TotemOfVoidHandler.saveEntityFromVoid(player, null);
    }

    @Override
    public Type getType() {
        return ViaAmuletTypes.VOID.get();
    }

    public static class Type implements IAmulet.Type<VoidAmulet> {
        public static final MapCodec<VoidAmulet> CODEC = MapCodec.unit(VoidAmulet.INSTANCE);
        public static final StreamCodec<ByteBuf, VoidAmulet> STREAM_CODEC = StreamCodec.unit(VoidAmulet.INSTANCE);

        @Override
        public MapCodec<VoidAmulet> codec() {
            return Type.CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, VoidAmulet> streamCodec() {
            return Type.STREAM_CODEC.cast();
        }
    }
}
