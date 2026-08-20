package com.qiushui1012.mod.voidinair.mixin;

import com.qiushui1012.mod.voidinair.init.item.ViaAmulets;
import dev.dubhe.anvilcraft.api.amulet.AmuletManager;
import dev.dubhe.anvilcraft.mixin.accessor.TargetingConditionsAccessor;
import dev.dubhe.anvilcraft.util.mixin.ModifiedSelector;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Optional;

@Mixin(NearestAttackableTargetGoal.class)
abstract class NearestAttackableTargetGoalMixin extends TargetGoal {
    public NearestAttackableTargetGoalMixin(Mob mob, boolean mustSee) {
        super(mob, mustSee);
    }

    @ModifyArg(
        method = "findTarget",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/Level;getNearestPlayer("
                     + "Lnet/minecraft/world/entity/ai/targeting/TargetingConditions;"
                     + "Lnet/minecraft/world/entity/LivingEntity;DDD"
                     + ")Lnet/minecraft/world/entity/player/Player;"
        ),
        index = 0
    )
    private TargetingConditions addAmuletScare(TargetingConditions conditions) {
        if (!this.mob.getType().is(EntityTypeTags.BEEHIVE_INHABITORS)) return conditions;
        return conditions.selector(
            Optional.ofNullable(((TargetingConditionsAccessor) conditions).getSelector())
                .map(p -> ModifiedSelector.toModified(
                    p,
                    () -> entity ->
                        entity instanceof Player player
                        && !AmuletManager.get(player.registryAccess()).hasAmuletInInventory(player, ViaAmulets.BEEHIVE)
                ))
                .orElse(entity ->
                            entity instanceof Player player
                            && !AmuletManager.get(player.registryAccess()).hasAmuletInInventory(player, ViaAmulets.BEEHIVE)
                )
        );
    }
}
