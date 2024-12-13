package com.chromanyan.chromaticarsenal.item;

import com.chromanyan.chromaticarsenal.ChromaticArsenal;
import com.chromanyan.chromaticarsenal.init.CAItems;
import com.chromanyan.chromaticarsenal.item.base.ChromaAccessory;
import com.chromanyan.chromaticarsenal.util.ChromaAccessoryHelper;
import com.chromanyan.chromaticarsenal.util.TooltipHelper;
import io.wispforest.accessories.api.attributes.AccessoryAttributeBuilder;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.VanillaGameEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@EventBusSubscriber
public class ShadowTreadsAccessory extends ChromaAccessory {

    public ShadowTreadsAccessory() {
        setEquipSound(SoundEvents.ARMOR_EQUIP_LEATHER);
        enableJankAttributeFix();
    }

    @OnlyIn(Dist.CLIENT)
    public static void clientInit() {
        AccessoriesRendererRegistry.registerNoRenderer(CAItems.SHADOW_TREADS.get());
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, list, tooltipFlag);

        if (!Screen.hasShiftDown()) return;
        TooltipHelper.itemTooltipLine(stack, 1, list);
        TooltipHelper.itemTooltipLine(stack, 2, list, TooltipHelper.multiplierAsPercentTooltip(ChromaticArsenal.CONFIG.COMMON.shadowTreadsSpeedModifier()), TooltipHelper.valueTooltip(ChromaticArsenal.CONFIG.COMMON.shadowTreadsMaxLightLevel()));
        TooltipHelper.itemTooltipLine(stack, 3, list);
    }

    @Override
    public void getDynamicModifiers(ItemStack stack, SlotReference reference, AccessoryAttributeBuilder builder) {
        LivingEntity livingEntity = reference.entity();
        Level world = livingEntity.getCommandSenderWorld();

        if (world.getMaxLocalRawBrightness(livingEntity.blockPosition()) <= ChromaticArsenal.CONFIG.COMMON.shadowTreadsMaxLightLevel()) {
            builder.addStackable(Attributes.MOVEMENT_SPEED, new AttributeModifier(ChromaticArsenal.of("shadow_treads_speed"), ChromaticArsenal.CONFIG.COMMON.shadowTreadsSpeedModifier(), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        } else {
            builder.addStackable(Attributes.MOVEMENT_SPEED, new AttributeModifier(ChromaticArsenal.of("shadow_treads_speed"), 0f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }
    }

    @SubscribeEvent
    public static void mobEffectApplicable(MobEffectEvent.Applicable event) {
        if (!ChromaAccessoryHelper.isAccessoryEquipped(event.getEntity(), CAItems.SHADOW_TREADS.get())) return;

        if (event.getEffectInstance().getEffect() == MobEffects.MOVEMENT_SLOWDOWN) {
            event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
        }
    }

    @SubscribeEvent
    public static void vanillaEvent(VanillaGameEvent event) {
        if (!(event.getCause() instanceof LivingEntity livingEntity)) return;
        if (!ChromaAccessoryHelper.isAccessoryEquipped(livingEntity, CAItems.SHADOW_TREADS.get())) return;
        if (!(event.getVanillaEvent() == GameEvent.STEP || event.getVanillaEvent() == GameEvent.HIT_GROUND)) return;
        if (livingEntity.fallDistance < 3.0F && !livingEntity.isSprinting()) {
            event.setCanceled(true);
        }
    }
}
