package com.chromanyan.chromaticarsenal.item.challengeaccessories;

import com.chromanyan.chromaticarsenal.ChromaticArsenal;
import com.chromanyan.chromaticarsenal.init.CADamageTypes;
import com.chromanyan.chromaticarsenal.init.CAItems;
import com.chromanyan.chromaticarsenal.init.CARarities;
import com.chromanyan.chromaticarsenal.item.base.ChromaAccessory;
import com.chromanyan.chromaticarsenal.util.ChromaAccessoryHelper;
import com.chromanyan.chromaticarsenal.util.TooltipHelper;
import io.wispforest.accessories.api.DropRule;
import io.wispforest.accessories.api.attributes.AccessoryAttributeBuilder;
import io.wispforest.accessories.api.attributes.SlotAttribute;
import io.wispforest.accessories.api.events.extra.FortuneAdjustment;
import io.wispforest.accessories.api.events.extra.v2.LootingAdjustment;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.storage.loot.LootContext;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@EventBusSubscriber
public class AscendedStarAccessory extends ChromaAccessory implements LootingAdjustment, FortuneAdjustment {

    public AscendedStarAccessory() {
        super(Rarity.valueOf(CARarities.CHALLENGE));
        setEquipSound(SoundEvents.BEACON_DEACTIVATE);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, list, tooltipFlag);
        if (!Screen.hasShiftDown()) return;
        TooltipHelper.itemTooltipLine(stack, 1, list, TooltipHelper.valueTooltip(ChromaticArsenal.CONFIG.COMMON.ascendedStarFortune()), TooltipHelper.valueTooltip(ChromaticArsenal.CONFIG.COMMON.ascendedStarLooting()));
        TooltipHelper.itemTooltipLine(stack, 2, list, TooltipHelper.valueTooltip(ChromaticArsenal.CONFIG.COMMON.ascendedStarDamage()));
        TooltipHelper.itemTooltipLine("cursed", list);
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference reference) {
        LivingEntity entity = reference.entity();

        if (entity instanceof Player playerEntity) {
            if (playerEntity.isCreative()) {
                return;
            }
        }
        entity.hurt(entity.damageSources().source(CADamageTypes.ASCENDED), 100000F);
    }

    @Override
    public DropRule getDropRule(ItemStack stack, SlotReference reference, DamageSource source) {
        return DropRule.KEEP;
    }

    @Override
    public int getLootingAdjustment(ItemStack stack, SlotReference reference, LivingEntity target, LootContext context, DamageSource damageSource, int currentLevel) {
        return ChromaticArsenal.CONFIG.COMMON.ascendedStarLooting();
    }

    @Override
    public int getFortuneAdjustment(ItemStack stack, SlotReference reference, LootContext context, int currentLevel) {
        return ChromaticArsenal.CONFIG.COMMON.ascendedStarFortune();
    }

    @Override
    public void getDynamicModifiers(ItemStack stack, SlotReference reference, AccessoryAttributeBuilder builder) {
        builder.addStackable(SlotAttribute.getAttributeHolder("super_accessory"), ChromaticArsenal.of("ascended_star_slots"), ChromaticArsenal.CONFIG.COMMON.ascendedStarSlots(), AttributeModifier.Operation.ADD_VALUE);
    }

    @SubscribeEvent
    public static void livingDamage(LivingDamageEvent.Pre event) {
        if (!ChromaAccessoryHelper.isAccessoryEquipped(event.getEntity(), CAItems.ASCENDED_STAR.get())) return;
        event.setNewDamage(event.getNewDamage() * ChromaticArsenal.CONFIG.COMMON.ascendedStarDamage());
    }
}
