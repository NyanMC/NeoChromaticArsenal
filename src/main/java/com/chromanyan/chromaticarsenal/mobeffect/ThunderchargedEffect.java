package com.chromanyan.chromaticarsenal.mobeffect;

import com.chromanyan.chromaticarsenal.ChromaticArsenal;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ThunderchargedEffect extends MobEffect {
    public ThunderchargedEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFFFF00);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, ChromaticArsenal.of("thundercharged_movement"), 0.3, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(Attributes.ATTACK_SPEED, ChromaticArsenal.of("thundercharged_attack"), 0.3, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }
}
