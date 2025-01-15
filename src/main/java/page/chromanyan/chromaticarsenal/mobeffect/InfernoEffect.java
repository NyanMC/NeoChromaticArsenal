package page.chromanyan.chromaticarsenal.mobeffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class InfernoEffect extends MobEffect {
    public InfernoEffect() {
        super(MobEffectCategory.HARMFUL, 0xFF7700);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity.getRemainingFireTicks() < 10) {
            entity.setRemainingFireTicks(20);
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
