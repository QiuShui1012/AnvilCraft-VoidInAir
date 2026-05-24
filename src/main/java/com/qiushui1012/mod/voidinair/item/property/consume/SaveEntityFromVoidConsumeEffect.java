package com.qiushui1012.mod.voidinair.item.property.consume;

import com.mojang.serialization.MapCodec;
import com.qiushui1012.mod.voidinair.init.item.VIAConsumeEffects;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Direction;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.Vec3;

public record SaveEntityFromVoidConsumeEffect() implements ConsumeEffect {
    public static final SaveEntityFromVoidConsumeEffect INSTANCE = new SaveEntityFromVoidConsumeEffect();
    public static final MapCodec<SaveEntityFromVoidConsumeEffect> CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<ByteBuf, SaveEntityFromVoidConsumeEffect> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    @Override
    public Type<SaveEntityFromVoidConsumeEffect> getType() {
        return VIAConsumeEffects.SAVE_ENTITY_FROM_VOID.get();
    }

    @Override
    public boolean apply(Level level, ItemStack stack, LivingEntity user) {
        user.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 600, 0));
        if (!(level instanceof ServerLevel serverside)) return true;
        MinecraftServer server = serverside.getServer();
        if (!user.canTeleport(user.level(), server.overworld())) return false;
        Vec3 destPos = user.position();
        if (user.level().dimension() != Level.OVERWORLD) {
            double scale = DimensionType.getTeleportationScale(user.level().dimensionType(), server.overworld().dimensionType());
            destPos = destPos.multiply(scale, 1, scale);
        }
        user.teleport(new TeleportTransition(
            server.overworld(),
            destPos.with(Direction.Axis.Y, server.overworld().getMaxY()),
            Vec3.ZERO,
            90f,
            user.getXRot(),
            entity -> entity.level().broadcastEntityEvent(entity, (byte) 38)
        ));
        return true;
    }
}
