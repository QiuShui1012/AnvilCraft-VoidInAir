package com.qiushui1012.mod.voidinair.item.totem;

import com.qiushui1012.mod.voidinair.init.item.ViaItems;
import dev.dubhe.anvilcraft.api.totem.handler.TotemHandler;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.Direction;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.EffectCures;

import javax.annotation.Nullable;

public class TotemOfVoidHandler implements TotemHandler {
    public static final TotemHandler INSTANCE = new TotemOfVoidHandler();

    @Override
    public boolean canExecute(DamageSource source, LivingEntity entity, ItemStack totem) {
        return source.is(DamageTypes.FELL_OUT_OF_WORLD);
    }

    @Override
    public boolean execute(DamageSource source, LivingEntity entity, ItemStack totem) {
        if (entity instanceof ServerPlayer player) {
            player.awardStat(Stats.ITEM_USED.get(ViaItems.TOTEM_OF_VOID.asItem()), 1);
            CriteriaTriggers.USED_TOTEM.trigger(player, ViaItems.TOTEM_OF_VOID.asStack());
            entity.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
        }
        entity.setHealth(1.0f);
        entity.removeEffectsCuredBy(EffectCures.PROTECTED_BY_TOTEM);
        entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
        entity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
        entity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
        entity.level().broadcastEntityEvent(entity, (byte) 38);
        TotemOfVoidHandler.saveEntityFromVoid(entity, entity1 -> entity1.level().broadcastEntityEvent(entity1, (byte) 38));
        return true;
    }

    @Override
    public void shrink(ItemStack totem) {
        totem.shrink(1);
    }

    public static void saveEntityFromVoid(LivingEntity entity, @Nullable DimensionTransition.PostDimensionTransition postTransition) {
        entity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 600, 0));
        MinecraftServer server = entity.getServer();
        if (server == null) return;
        if (!entity.canChangeDimensions(entity.level(), server.overworld())) return;
        Vec3 destPos = entity.position();
        if (entity.level().dimension() != Level.OVERWORLD) {
            double scale = DimensionType.getTeleportationScale(entity.level().dimensionType(), server.overworld().dimensionType());
            destPos = destPos.multiply(scale, 1, scale);
        }
        entity.changeDimension(new DimensionTransition(
            server.overworld(),
            destPos.with(Direction.Axis.Y, server.overworld().getMaxBuildHeight()),
            Vec3.ZERO,
            90f,
            entity.getXRot(),
            postTransition == null ? a -> {} : postTransition
        ));
    }
}
