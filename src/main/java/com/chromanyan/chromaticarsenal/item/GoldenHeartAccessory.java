package com.chromanyan.chromaticarsenal.item;

import com.chromanyan.chromaticarsenal.Config;
import com.chromanyan.chromaticarsenal.item.base.ChromaAccessory;
import io.wispforest.accessories.api.events.extra.PiglinNeutralInducer;
import io.wispforest.accessories.api.slot.SlotReference;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class GoldenHeartAccessory extends ChromaAccessory implements PiglinNeutralInducer {

    public GoldenHeartAccessory() {
        super(SoundEvents.ARMOR_EQUIP_GOLD);
    }

    @Override
    public void onEquip(ItemStack stack, SlotReference reference) {
        if (!reference.entity().getCommandSenderWorld().isClientSide()) {
            reference.entity().addEffect(new MobEffectInstance(MobEffects.ABSORPTION, Config.goldenHeartDuration, Config.goldenHeartAmplifier, true, false), reference.entity());
        }
    }

    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        LivingEntity living = reference.entity();
        if (!reference.entity().getCommandSenderWorld().isClientSide() && living.tickCount % Config.goldenHeartDuration == 0) {
            reference.entity().addEffect(new MobEffectInstance(MobEffects.ABSORPTION, Config.goldenHeartDuration, Config.goldenHeartAmplifier, true, false), reference.entity());
        }
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference reference) {
        LivingEntity living = reference.entity();
        if (living.getCommandSenderWorld().isClientSide) return;
        living.removeEffect(MobEffects.ABSORPTION);
    }

    @Override
    public TriState makePiglinsNeutral(ItemStack stack, SlotReference reference) {
        return TriState.TRUE;
    }
}
