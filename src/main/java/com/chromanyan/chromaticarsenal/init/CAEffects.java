package com.chromanyan.chromaticarsenal.init;

import com.chromanyan.chromaticarsenal.ChromaticArsenal;
import com.chromanyan.chromaticarsenal.mobeffect.BubblePanicEffect;
import com.chromanyan.chromaticarsenal.mobeffect.FracturedEffect;
import com.chromanyan.chromaticarsenal.mobeffect.ThunderchargedEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CAEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, ChromaticArsenal.MODID);

    public static final DeferredHolder<MobEffect, BubblePanicEffect> BUBBLE_PANIC = MOB_EFFECTS.register("bubble_panic", BubblePanicEffect::new);
    public static final DeferredHolder<MobEffect, ThunderchargedEffect> THUNDERCHARGED = MOB_EFFECTS.register("thundercharged", ThunderchargedEffect::new);
    public static final DeferredHolder<MobEffect, FracturedEffect> FRACTURED = MOB_EFFECTS.register("fractured", FracturedEffect::new);
}
