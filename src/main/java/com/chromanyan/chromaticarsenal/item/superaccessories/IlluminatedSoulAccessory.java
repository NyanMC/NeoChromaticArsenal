package com.chromanyan.chromaticarsenal.item.superaccessories;

import com.chromanyan.chromaticarsenal.ChromaticArsenal;
import com.chromanyan.chromaticarsenal.init.CAItems;
import com.chromanyan.chromaticarsenal.item.base.SuperAccessory;
import com.chromanyan.chromaticarsenal.util.ChromaAccessoryHelper;
import com.chromanyan.chromaticarsenal.util.TooltipHelper;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@EventBusSubscriber
public class IlluminatedSoulAccessory extends SuperAccessory {

    public IlluminatedSoulAccessory() {
        setEquipSound(SoundEvents.RESPAWN_ANCHOR_CHARGE);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, list, tooltipFlag);
        if (!Screen.hasShiftDown()) return;
        TooltipHelper.itemTooltipLine(stack, 1, list);
        if (ChromaticArsenal.CONFIG.COMMON.illuminatedSoulGlowingDuration() > 0)
            TooltipHelper.itemTooltipLine(stack, 2, list, TooltipHelper.ticksToSecondsTooltip(ChromaticArsenal.CONFIG.COMMON.illuminatedSoulGlowingDuration()));
        if (ChromaticArsenal.CONFIG.COMMON.illuminatedSoulUndeadMultiplier() > 1)
            TooltipHelper.itemTooltipLine(stack, 3, list, TooltipHelper.multiplierAsPercentTooltip(ChromaticArsenal.CONFIG.COMMON.illuminatedSoulUndeadMultiplier()));
    }

    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        super.tick(stack, reference);
        LivingEntity entity = reference.entity();
        if (!entity.getCommandSenderWorld().isClientSide) {
            entity.setGlowingTag(true);
            entity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 410, 0, false, false), entity);
        }
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference reference) {
        LivingEntity entity = reference.entity();
        if (entity.getCommandSenderWorld().isClientSide)
            return;
        entity.removeEffect(MobEffects.NIGHT_VISION);
        entity.setGlowingTag(false);
    }

    @SubscribeEvent
    public static void mobEffectApplicable(MobEffectEvent.Applicable event) {
        if (!ChromaAccessoryHelper.isAccessoryEquipped(event.getEntity(), CAItems.ILLUMINATED_SOUL.get())) return;

        if (event.getEffectInstance().getEffect() == MobEffects.BLINDNESS || event.getEffectInstance().getEffect() == MobEffects.DARKNESS) {
            event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
        }
    }

    @SubscribeEvent
    public static void livingDamage(LivingDamageEvent.Pre event) {
        if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) return;
        if (!ChromaAccessoryHelper.isAccessoryEquipped(attacker, CAItems.ILLUMINATED_SOUL.get())) return;

        if (ChromaticArsenal.CONFIG.COMMON.illuminatedSoulGlowingDuration() > 0)
            event.getEntity().addEffect(new MobEffectInstance(MobEffects.GLOWING, ChromaticArsenal.CONFIG.COMMON.illuminatedSoulGlowingDuration()), attacker);
        if (event.getEntity().getType().is(EntityTypeTags.SENSITIVE_TO_SMITE))
            event.setNewDamage(event.getNewDamage() * ChromaticArsenal.CONFIG.COMMON.illuminatedSoulUndeadMultiplier());
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerVariants() {
        ItemProperties.register(CAItems.ILLUMINATED_SOUL.get(), ChromaticArsenal.of("equipped"),
                (stack, world, entity, thing) -> { // what a nightmare
                    if (entity == null) {
                        return 0;
                    }

                    ItemStack equippedStack = ChromaAccessoryHelper.tryGetFirstEquipped(entity, CAItems.ILLUMINATED_SOUL.get());

                    if (equippedStack == stack) {
                        return 1;
                    }

                    return 0;
                });
    }
}
