package com.chromanyan.chromaticarsenal.item;

import com.chromanyan.chromaticarsenal.ChromaticArsenal;
import com.chromanyan.chromaticarsenal.item.base.ChromaAccessory;
import com.chromanyan.chromaticarsenal.util.TooltipHelper;
import io.wispforest.accessories.api.events.extra.PiglinNeutralInducer;
import io.wispforest.accessories.api.slot.SlotReference;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GoldenHeartAccessory extends ChromaAccessory implements PiglinNeutralInducer {

    public GoldenHeartAccessory() {
        setEquipSound(SoundEvents.ARMOR_EQUIP_GOLD);
    }

    @Override
    public void onEquip(ItemStack stack, SlotReference reference) {
        if (!reference.entity().getCommandSenderWorld().isClientSide()) {
            reference.entity().addEffect(new MobEffectInstance(MobEffects.ABSORPTION, ChromaticArsenal.CONFIG.COMMON.goldenHeartDuration(), ChromaticArsenal.CONFIG.COMMON.goldenHeartAmplifier(), true, false), reference.entity());
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, list, tooltipFlag);
        if (!Screen.hasShiftDown()) return;
        TooltipHelper.itemTooltipLine(stack, 3, list);
        TooltipHelper.itemTooltipLine(stack, 1, list, TooltipHelper.potionAmplifierTooltip(ChromaticArsenal.CONFIG.COMMON.goldenHeartAmplifier()));
        TooltipHelper.itemTooltipLine(stack, 2, list, TooltipHelper.ticksToSecondsTooltip(ChromaticArsenal.CONFIG.COMMON.goldenHeartDuration()));
    }

    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        LivingEntity living = reference.entity();
        if (!reference.entity().getCommandSenderWorld().isClientSide() && living.tickCount % ChromaticArsenal.CONFIG.COMMON.goldenHeartDuration() == 0) {
            reference.entity().addEffect(new MobEffectInstance(MobEffects.ABSORPTION, ChromaticArsenal.CONFIG.COMMON.goldenHeartDuration(), ChromaticArsenal.CONFIG.COMMON.goldenHeartAmplifier(), true, false), reference.entity());
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
