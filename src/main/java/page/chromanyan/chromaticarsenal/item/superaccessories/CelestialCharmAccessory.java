package page.chromanyan.chromaticarsenal.item.superaccessories;

import page.chromanyan.chromaticarsenal.ChromaticArsenal;
import page.chromanyan.chromaticarsenal.init.CAItems;
import page.chromanyan.chromaticarsenal.item.base.SuperAccessory;
import page.chromanyan.chromaticarsenal.util.TooltipHelper;
import io.wispforest.accessories.api.attributes.AccessoryAttributeBuilder;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CelestialCharmAccessory extends SuperAccessory {

    private static final ResourceLocation SPEED = ChromaticArsenal.of("celestial_charm_speed");
    private static final ResourceLocation DAMAGE = ChromaticArsenal.of("celestial_charm_damage");

    public CelestialCharmAccessory() {
        setEquipSound(SoundEvents.GENERIC_EXTINGUISH_FIRE);
        setIncompatibility(CAItems.SHADOW_TREADS);
        enableJankAttributeFix();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, list, tooltipFlag);
        if (!Screen.hasShiftDown()) return;
        TooltipHelper.itemTooltipLine(stack, 1, list);
    }

    @Override
    public void getDynamicModifiers(ItemStack stack, SlotReference reference, AccessoryAttributeBuilder builder) {
        LivingEntity entity = reference.entity();

        if (entity == null) {
            ChromaticArsenal.LOGGER.warn("entity is null");
            super.getDynamicModifiers(stack, reference, builder);
            return;
        }

        long time = entity.getCommandSenderWorld().getDayTime() % 24000; // no see
        if (time <= 6000) {
            long compareTime = time + 6000;
            builder.addStackable(Attributes.MOVEMENT_SPEED, new AttributeModifier(SPEED, ChromaticArsenal.CONFIG.COMMON.celestialCharmSpeedMultiplier() * (1 - ((float) compareTime / 12000F)), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
            builder.addStackable(Attributes.ATTACK_DAMAGE, new AttributeModifier(DAMAGE, ChromaticArsenal.CONFIG.COMMON.celestialCharmDamageMultiplier() * ((float) compareTime / 12000F), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        } else if (time <= 18000) {
            long compareTime = time - 6000;
            builder.addStackable(Attributes.MOVEMENT_SPEED, new AttributeModifier(SPEED, ChromaticArsenal.CONFIG.COMMON.celestialCharmSpeedMultiplier() * ((float) compareTime / 12000F), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
            builder.addStackable(Attributes.ATTACK_DAMAGE, new AttributeModifier(DAMAGE, ChromaticArsenal.CONFIG.COMMON.celestialCharmDamageMultiplier() * (1 - ((float) compareTime / 12000F)), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        } else {
            long compareTime = time - 18000;
            builder.addStackable(Attributes.MOVEMENT_SPEED, new AttributeModifier(SPEED, ChromaticArsenal.CONFIG.COMMON.celestialCharmSpeedMultiplier() * (1 - ((float) compareTime / 12000F)), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
            builder.addStackable(Attributes.ATTACK_DAMAGE, new AttributeModifier(DAMAGE, ChromaticArsenal.CONFIG.COMMON.celestialCharmDamageMultiplier() * ((float) compareTime / 12000F), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }

        super.getDynamicModifiers(stack, reference, builder);
    }
}
