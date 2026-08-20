package com.qiushui1012.mod.voidinair.item.property.custom;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.MapCodec;
import com.qiushui1012.mod.voidinair.init.item.ViaCustomDataComponents;
import com.qiushui1012.mod.voidinair.item.property.amulet.BlackCatAmulet;
import dev.anvilcraft.lib.v2.util.Util;
import dev.dubhe.anvilcraft.api.recipe.data.ICustomDataComponent;
import dev.dubhe.anvilcraft.api.recipe.result.ResultContext;
import dev.dubhe.anvilcraft.init.item.ModComponents;
import dev.dubhe.anvilcraft.item.property.component.amulet.ComradeAmulet;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;

public class BlackCatAmuletData implements ICustomDataComponent<BlackCatAmulet> {
    public static final BlackCatAmuletData INSTANCE = new BlackCatAmuletData();

    @Override
    public DataComponentType<BlackCatAmulet> getDataComponentType() {
        return Util.cast(ModComponents.AMULET);
    }

    @Override
    public Type getType() {
        return ViaCustomDataComponents.BLACK_CAT.get();
    }

    @Override
    public @Nullable BlackCatAmulet make(ResultContext ctx) {
        for (ItemStack stack : ctx.getInputs().values()) {
            if (!stack.has(ModComponents.AMULET)) continue;
            if (!(stack.get(ModComponents.AMULET) instanceof ComradeAmulet(List<UUID> players))) continue;
            return new BlackCatAmulet(ImmutableList.copyOf(players));
        }
        return null;
    }

    @Override
    public BlackCatAmulet merge(BlackCatAmulet oldData, BlackCatAmulet newData) {
        ImmutableList.Builder<UUID> players = ImmutableList.builder();
        players.addAll(oldData.players());
        players.addAll(newData.players());
        return new BlackCatAmulet(players.build());
    }

    public static class Type implements ICustomDataComponent.Type<BlackCatAmuletData> {
        public static final MapCodec<BlackCatAmuletData> CODEC = MapCodec.unit(BlackCatAmuletData.INSTANCE);
        public static final StreamCodec<ByteBuf, BlackCatAmuletData> STREAM_CODEC = StreamCodec.unit(BlackCatAmuletData.INSTANCE);

        @Override
        public MapCodec<BlackCatAmuletData> codec() {
            return Type.CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, BlackCatAmuletData> streamCodec() {
            return Type.STREAM_CODEC.cast();
        }
    }
}
