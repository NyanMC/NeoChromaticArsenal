package com.chromanyan.chromaticarsenal.item;

import com.chromanyan.chromaticarsenal.ChromaticArsenal;
import com.chromanyan.chromaticarsenal.init.CAItems;
import com.chromanyan.chromaticarsenal.init.CATags;
import com.chromanyan.chromaticarsenal.item.base.ChromaAccessory;
import com.chromanyan.chromaticarsenal.util.ChromaAccessoryHelper;
import com.chromanyan.chromaticarsenal.util.TooltipHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder;
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
public class WardCrystalAccessory extends ChromaAccessory {

    public WardCrystalAccessory() {
        setEquipSound(Holder.direct(SoundEvents.AMETHYST_BLOCK_PLACE));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, list, tooltipFlag);
        if (!Screen.hasShiftDown()) return;
        TooltipHelper.itemTooltipLine(stack, 1, list, TooltipHelper.multiplierAsPercentTooltip(ChromaticArsenal.CONFIG.COMMON.wardCrystalIncomingMultiplier()));
        TooltipHelper.itemTooltipLine(stack, 2, list, TooltipHelper.multiplierAsPercentTooltip(ChromaticArsenal.CONFIG.COMMON.wardCrystalOutgoingMultiplier()));
    }

    @SubscribeEvent
    public static void livingDamage(LivingDamageEvent.Pre event) {
        if (event.getSource().is(CATags.DamageTypes.IMMUNE_TO_WARD_CRYSTAL)) return;
        if (event.getNewDamage() == 0 || event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY)) return;

        if (ChromaAccessoryHelper.isAccessoryEquipped(event.getEntity(), CAItems.WARD_CRYSTAL.get())) {
            event.setNewDamage(event.getNewDamage() * (float) ChromaticArsenal.CONFIG.COMMON.wardCrystalIncomingMultiplier());
        }

        if (!(event.getSource().getEntity() instanceof LivingEntity livingEntity)) return;
        if (!ChromaAccessoryHelper.isAccessoryEquipped(livingEntity, CAItems.WARD_CRYSTAL.get())) return;

        event.setNewDamage(event.getNewDamage() * (float) ChromaticArsenal.CONFIG.COMMON.wardCrystalOutgoingMultiplier());
    }
}
