package com.chromanyan.chromaticarsenal.item;

import com.chromanyan.chromaticarsenal.ChromaticArsenal;
import com.chromanyan.chromaticarsenal.init.CAEffects;
import com.chromanyan.chromaticarsenal.init.CAItems;
import com.chromanyan.chromaticarsenal.item.base.ChromaAccessory;
import com.chromanyan.chromaticarsenal.util.ChromaAccessoryHelper;
import com.chromanyan.chromaticarsenal.util.TooltipHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@EventBusSubscriber
public class ThunderguardAccessory extends ChromaAccessory {

    public ThunderguardAccessory() {
        super(new Item.Properties()
                .stacksTo(1)
                .rarity(Rarity.RARE)
                .fireResistant()); // lightning places fire. we don't want to instantly destroy the thunderguard when it gets created
        setEquipSound(SoundEvents.LIGHTNING_BOLT_THUNDER);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, list, tooltipFlag);
        if (!Screen.hasShiftDown()) return;

        TooltipHelper.itemTooltipLine(stack, 1, list);
        TooltipHelper.itemTooltipLine(stack, 2, list, TooltipHelper.valueTooltip(ChromaticArsenal.CONFIG.COMMON.thunderguardZapDamage()));
    }

    @SubscribeEvent
    public static void livingIncomingDamage(LivingIncomingDamageEvent event) {
        if (!event.getSource().is(DamageTypes.LIGHTNING_BOLT)) return;
        if (!ChromaAccessoryHelper.isAccessoryEquipped(event.getEntity(), CAItems.THUNDERGUARD.get())) return;

        event.setCanceled(true);
        event.getEntity().addEffect(new MobEffectInstance(CAEffects.THUNDERCHARGED, ChromaticArsenal.CONFIG.COMMON.thunderchargedDuration()));
    }

    @SubscribeEvent
    public static void livingDamage(LivingDamageEvent.Post event) {
        if (!ChromaAccessoryHelper.isAccessoryEquipped(event.getEntity(), CAItems.THUNDERGUARD.get())) return;

        if (event.getSource().getDirectEntity() instanceof LivingEntity livingEntity) {
            livingEntity.hurt(livingEntity.getCommandSenderWorld().damageSources().lightningBolt(), (float) ChromaticArsenal.CONFIG.COMMON.thunderguardZapDamage());
        }
    }

    @Override
    public boolean canBeHurtBy(@NotNull ItemStack stack, DamageSource source) {
        return !source.is(DamageTypes.LIGHTNING_BOLT) && super.canBeHurtBy(stack, source);
        // the lightning protection item itself should be immune to lightning
    }
}
