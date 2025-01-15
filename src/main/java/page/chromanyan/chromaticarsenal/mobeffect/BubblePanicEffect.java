package page.chromanyan.chromaticarsenal.mobeffect;

import page.chromanyan.chromaticarsenal.ChromaticArsenal;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.neoforged.neoforge.common.NeoForgeMod;

public class BubblePanicEffect extends MobEffect {
    public BubblePanicEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x4287f5);
        addAttributeModifier(NeoForgeMod.SWIM_SPEED, ChromaticArsenal.of("bubble_panic_modifier"), 0.3, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }
}
