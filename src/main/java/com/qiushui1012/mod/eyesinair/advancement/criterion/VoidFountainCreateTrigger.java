package com.qiushui1012.mod.eyesinair.advancement.criterion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.qiushui1012.mod.eyesinair.init.block.VIACriterionTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.ContextAwarePredicate;
import net.minecraft.advancements.criterion.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public class VoidFountainCreateTrigger extends SimpleCriterionTrigger<VoidFountainCreateTrigger.TriggerInstance> {
    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, TriggerInstance::matches);
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player) implements SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(ins -> ins.group(
            ContextAwarePredicate.CODEC
                .optionalFieldOf("player")
                .forGetter(TriggerInstance::player)
        ).apply(ins, TriggerInstance::new));

        public static Criterion<TriggerInstance> create() {
            return VIACriterionTriggers.VOID_FOUNTAIN_CREATE.get().createCriterion(new TriggerInstance(Optional.empty()));
        }

        public boolean matches() {
            return true;
        }
    }
}
