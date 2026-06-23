package com.qiushui1012.mod.voidinair.item.property.amulet;

import com.mojang.serialization.MapCodec;
import com.qiushui1012.mod.voidinair.init.item.ViaAmuletTypes;
import dev.dubhe.anvilcraft.item.property.component.amulet.IAmulet;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;

public record BeehiveAmulet() implements IAmulet {
    public static final BeehiveAmulet INSTANCE = new BeehiveAmulet();

    @Override
    public boolean shouldImmune(ServerPlayer player, DamageSource source) {
        return IAmulet.super.shouldImmune(player, source);
    }

    @Override
    public Type getType() {
        return ViaAmuletTypes.BEEHIVE.get();
    }

    public static class Type implements IAmulet.Type<BeehiveAmulet> {
        public static final MapCodec<BeehiveAmulet> CODEC = MapCodec.unit(BeehiveAmulet.INSTANCE);
        public static final StreamCodec<ByteBuf, BeehiveAmulet> STREAM_CODEC = StreamCodec.unit(BeehiveAmulet.INSTANCE);

        @Override
        public MapCodec<BeehiveAmulet> codec() {
            return Type.CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, BeehiveAmulet> streamCodec() {
            return Type.STREAM_CODEC.cast();
        }
    }
}
