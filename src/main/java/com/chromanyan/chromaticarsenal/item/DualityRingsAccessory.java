package com.chromanyan.chromaticarsenal.item;

import com.chromanyan.chromaticarsenal.ChromaticArsenal;
import com.chromanyan.chromaticarsenal.init.CAItems;
import com.chromanyan.chromaticarsenal.item.base.ChromaAccessory;
import com.chromanyan.chromaticarsenal.util.ChromaAccessoryHelper;
import com.chromanyan.chromaticarsenal.util.TooltipHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@EventBusSubscriber
public class DualityRingsAccessory extends ChromaAccessory {

    public DualityRingsAccessory() {
        setEquipSound(SoundEvents.ARROW_HIT);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, list, tooltipFlag);

        if (!Screen.hasShiftDown()) return;
        TooltipHelper.itemTooltipLine(stack, 1, list);
        TooltipHelper.itemTooltipLine(stack, 2, list, TooltipHelper.multiplierAsPercentTooltip(ChromaticArsenal.CONFIG.COMMON.dualityRingsDamageMultiplier()));
    }

    @SubscribeEvent
    public static void livingDamage(LivingDamageEvent.Pre event) {
        if (!event.getSource().is(DamageTypeTags.IS_PROJECTILE)) return;
        if (!(event.getSource().getEntity() instanceof LivingEntity livingEntity)) return;
        if (!ChromaAccessoryHelper.isAccessoryEquipped(livingEntity, CAItems.DUALITY_RINGS.get())) return;

        event.setNewDamage(event.getNewDamage() * (float) ChromaticArsenal.CONFIG.COMMON.dualityRingsDamageMultiplier());
    }
}
