package page.chromanyan.chromaticarsenal.item.superaccessories;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import page.chromanyan.chromaticarsenal.CAConfig;
import page.chromanyan.chromaticarsenal.ChromaticArsenal;
import page.chromanyan.chromaticarsenal.init.CAItems;
import page.chromanyan.chromaticarsenal.item.base.SuperAccessory;
import page.chromanyan.chromaticarsenal.util.TooltipHelper;
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
import top.theillusivec4.curios.api.SlotContext;

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
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> atts = LinkedHashMultimap.create();
        LivingEntity entity = slotContext.entity();

        if (entity == null) {
            ChromaticArsenal.LOGGER.warn("entity is null");
            return super.getAttributeModifiers(slotContext, id, stack);
        }

        long time = entity.getCommandSenderWorld().getDayTime() % 24000; // no see
        if (time <= 6000) {
            long compareTime = time + 6000;
            atts.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(SPEED, CAConfig.celestialCharmSpeedMultiplier * (1 - ((float) compareTime / 12000F)), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
            atts.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(DAMAGE, CAConfig.celestialCharmDamageMultiplier * ((float) compareTime / 12000F), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        } else if (time <= 18000) {
            long compareTime = time - 6000;
            atts.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(SPEED, CAConfig.celestialCharmSpeedMultiplier * ((float) compareTime / 12000F), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
            atts.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(DAMAGE, CAConfig.celestialCharmDamageMultiplier * (1 - ((float) compareTime / 12000F)), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        } else {
            long compareTime = time - 18000;
            atts.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(SPEED, CAConfig.celestialCharmSpeedMultiplier * (1 - ((float) compareTime / 12000F)), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
            atts.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(DAMAGE, CAConfig.celestialCharmDamageMultiplier * ((float) compareTime / 12000F), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }

        return atts;
    }
}
