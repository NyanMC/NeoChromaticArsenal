package com.chromanyan.chromaticarsenal.triggers;

import com.chromanyan.chromaticarsenal.init.CATriggers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class GlassShieldBlockTrigger extends SimpleCriterionTrigger<GlassShieldBlockTrigger.TriggerInstance> {

    public void trigger(ServerPlayer player, int amount) {
        this.trigger(player, triggerInstance -> triggerInstance.test(amount));
    }

    @Override
    public @NotNull Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<Integer> damage)
            implements SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(
                triggerInstance -> triggerInstance.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                        Codec.INT.optionalFieldOf("damage").forGetter(TriggerInstance::damage)
                ).apply(triggerInstance, TriggerInstance::new));

        boolean test(int amount) {
            return damage.isEmpty() || amount >= damage.get();
        }

        public static Criterion<TriggerInstance> blockedAboveDamage(int amount) {
            return CATriggers.GLASS_SHIELD_BLOCK.get().createCriterion(
                    new TriggerInstance(Optional.empty(), Optional.of(amount))
            );
        }
    }
}
