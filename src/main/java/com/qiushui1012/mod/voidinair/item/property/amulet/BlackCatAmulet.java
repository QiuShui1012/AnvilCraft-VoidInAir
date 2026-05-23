package com.qiushui1012.mod.voidinair.item.property.amulet;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.MapCodec;
import com.qiushui1012.mod.voidinair.init.item.ViaAmuletTypes;
import com.qiushui1012.mod.voidinair.init.item.ViaAmulets;
import com.qiushui1012.mod.voidinair.init.item.ViaItems;
import dev.anvilcraft.lib.v2.codec.CodecUtil;
import dev.anvilcraft.lib.v2.util.InventoryUtil;
import dev.anvilcraft.lib.v2.util.Util;
import dev.dubhe.anvilcraft.init.item.ModComponents;
import dev.dubhe.anvilcraft.item.property.component.amulet.ComradeAmulet;
import dev.dubhe.anvilcraft.item.property.component.amulet.IAmulet;
import io.netty.buffer.ByteBuf;
import net.minecraft.ChatFormatting;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.NameAndId;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public record BlackCatAmulet(List<UUID> players) implements IAmulet {
    private static final List<IAmulet> ACTING = List.of(
        ViaAmulets.VOID,
        ViaAmulets.SNOWFLAKE,
        ViaAmulets.BEEHIVE,
        ViaAmulets.GOLD,
        ViaAmulets.DOLPHIN,
        ViaAmulets.PUMPKIN,
        ViaAmulets.ANVILCRAFT
    );

    public static BlackCatAmulet empty() {
        return new BlackCatAmulet(List.of());
    }

    public BlackCatAmulet sign(Player player) {
        UUID id = player.getGameProfile().id();
        for (UUID uuid : this.players) {
            if (uuid.equals(id)) {
                return this;
            }
        }
        ImmutableList.Builder<UUID> players = ImmutableList.builder();
        players.addAll(this.players);
        players.add(id);
        return new BlackCatAmulet(players.build());
    }

    @Override
    public void inventoryTick(ServerPlayer player, ItemStack stack, boolean isEnabled) {
        for (IAmulet amulet : BlackCatAmulet.ACTING) {
            amulet.inventoryTick(player, stack, isEnabled);
        }
    }

    @Override
    public boolean shouldImmune(ServerPlayer player, DamageSource source) {
        for (IAmulet amulet : BlackCatAmulet.ACTING) {
            if (amulet.shouldImmune(player, source)) {
                return true;
            }
        }
        ItemStack comrade = Optional.of(InventoryUtil.getFirstItem(player.getInventory(), ViaItems.BLACK_CAT_AMULET))
            .filter(ItemStack::isEmpty)
            .orElse(InventoryUtil.getItemInCompat(player, stack -> stack.is(ViaItems.BLACK_CAT_AMULET)));
        return Optional.ofNullable(source.getEntity())
            .flatMap(entity -> Util.castSafely(entity, Player.class))
            .map(p -> p.getGameProfile().id())
            .filter(id -> !comrade.isEmpty() && this.players.contains(id))
            .isPresent();
    }

    @Override
    public int getWeight() {
        return ViaAmulets.ANVILCRAFT.getWeight();
    }

    @Override
    public boolean canActAs(IAmulet other) {
        for (IAmulet amulet : BlackCatAmulet.ACTING) {
            if (amulet.canActAs(other)) {
                return true;
            }
        }
        return other instanceof ComradeAmulet || other instanceof BlackCatAmulet;
    }

    @Override
    public Type getType() {
        return ViaAmuletTypes.BLACK_CAT.get();
    }

    @Override
    public void addToTooltip(Item.TooltipContext ctx, Consumer<Component> builder, TooltipFlag flag, DataComponentGetter components) {
        IAmulet amulet = components.get(ModComponents.AMULET);
        if (!(amulet instanceof BlackCatAmulet(List<UUID> ids))) return;
        builder.accept(Component.translatable("item.anvilcraft.comrade_amulet.tooltip").withStyle(ChatFormatting.GRAY));
        for (UUID id : ids) {
            Level level = ctx.level();
            Component entry;
            if (level != null) {
                Player player = level.getPlayerInAnyDimension(id);
                if (player == null) {
                    entry = Component.literal(
                        dev.dubhe.anvilcraft.util.Util.getServices(level).nameToIdCache().get(id).map(NameAndId::name).orElse(id.toString())
                    );
                } else {
                    entry = player.getDisplayName();
                }
            } else {
                entry = Component.literal(id.toString());
            }
            builder.accept(Component.literal("· ").append(entry));
        }
    }

    public static class Type implements IAmulet.Type<BlackCatAmulet> {
        public static final MapCodec<BlackCatAmulet> CODEC = CodecUtil.mapCodec(
            UUIDUtil.CODEC
                .listOf()
                .fieldOf("players")
                .forGetter(BlackCatAmulet::players),
            BlackCatAmulet::new
        );
        public static final StreamCodec<ByteBuf, BlackCatAmulet> STREAM_CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC.apply(ByteBufCodecs.list()),
            BlackCatAmulet::players,
            BlackCatAmulet::new
        );

        @Override
        public MapCodec<BlackCatAmulet> codec() {
            return Type.CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, BlackCatAmulet> streamCodec() {
            return Type.STREAM_CODEC.cast();
        }
    }
}
