package com.qiushui1012.mod.voidinair.event;

import com.qiushui1012.mod.voidinair.init.item.ViaAmulets;
import dev.anvilcraft.lib.v2.util.Util;
import dev.dubhe.anvilcraft.api.amulet.AmuletManager;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@EventBusSubscriber
public class LivingEntityEventListener {
    @SubscribeEvent
    public static void onChangeTarget(LivingChangeTargetEvent event) {
        if (event.getTargetType() != LivingChangeTargetEvent.LivingTargetType.MOB_TARGET) return;
        Mob entity = Util.castSafely(event.getEntity(), Mob.class).orElse(null);
        if (entity == null) return;
        if (!(event.getNewAboutToBeSetTarget() instanceof Player player)) return;
        AmuletManager manager = AmuletManager.get(player.registryAccess());
        if (
            entity.getType().is(EntityTypeTags.BEEHIVE_INHABITORS) && manager.hasAmuletInInventory(player, ViaAmulets.BEEHIVE)
        ) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onTick(EntityTickEvent.Post event) {
        Mob entity = Util.castSafely(event.getEntity(), Mob.class).orElse(null);
        if (entity == null) return;
        if (!(entity.getTarget() instanceof Player player)) return;
        AmuletManager manager = AmuletManager.get(player.registryAccess());
        if (
            entity.getType().is(EntityTypeTags.BEEHIVE_INHABITORS) && manager.hasAmuletInInventory(player, ViaAmulets.BEEHIVE)
        ) {
            entity.setTarget(null);
            if (
                entity instanceof NeutralMob neutral
                && neutral.getPersistentAngerTarget() != null
                && neutral.getPersistentAngerTarget().equals(player.getUUID())
            ) {
                neutral.setRemainingPersistentAngerTime(0);
                neutral.setPersistentAngerTarget(null);
            }
        }
    }
}
