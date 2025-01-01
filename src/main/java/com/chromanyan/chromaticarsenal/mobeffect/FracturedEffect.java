package com.chromanyan.chromaticarsenal.mobeffect;

import com.chromanyan.chromaticarsenal.ChromaticArsenal;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class FracturedEffect extends MobEffect {
    public FracturedEffect() {
        super(MobEffectCategory.HARMFUL, 0xFFFF00);
        this.addAttributeModifier(Attributes.MAX_HEALTH, ChromaticArsenal.of("fractured_max_health"), -0.1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }
}
