package page.chromanyan.chromaticarsenal.item.challengeaccessories;

import page.chromanyan.chromaticarsenal.ChromaticArsenal;
import page.chromanyan.chromaticarsenal.init.CARarities;
import page.chromanyan.chromaticarsenal.item.base.ChromaAccessory;
import page.chromanyan.chromaticarsenal.util.TooltipHelper;
import io.wispforest.accessories.api.attributes.AccessoryAttributeBuilder;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.NeoForgeMod;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LunaFlowerAccessory extends ChromaAccessory {

    public LunaFlowerAccessory() {
        super(Rarity.valueOf(CARarities.CHALLENGE));
        setEquipSound(SoundEvents.GRASS_BREAK);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, list, tooltipFlag);
        if (!Screen.hasShiftDown()) return;

        TooltipHelper.itemTooltipLine(stack, 1, list);
        TooltipHelper.itemTooltipLine(stack, 2, list);
    }

    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        LivingEntity entity = reference.entity();

        if (!entity.horizontalCollision) return;

        if (entity.zza > 0 && entity.getDeltaMovement().y < 0.1) {
            entity.setDeltaMovement(entity.getDeltaMovement().x, 0.1, entity.getDeltaMovement().z);
            entity.resetFallDistance();
        }
    }

    @Override
    public void getDynamicModifiers(ItemStack stack, SlotReference reference, AccessoryAttributeBuilder builder) {
        super.getDynamicModifiers(stack, reference, builder);
        builder.addStackable(Attributes.SCALE, new AttributeModifier(ChromaticArsenal.of("luna_flower_scale"), -0.75, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        builder.addStackable(Attributes.ARMOR, new AttributeModifier(ChromaticArsenal.of("luna_flower_armor"), -0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        builder.addStackable(Attributes.ATTACK_DAMAGE, new AttributeModifier(ChromaticArsenal.of("luna_flower_damage"), -0.75, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        builder.addStackable(Attributes.MAX_HEALTH, new AttributeModifier(ChromaticArsenal.of("luna_flower_health"), -0.6, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        builder.addStackable(Attributes.MOVEMENT_SPEED, new AttributeModifier(ChromaticArsenal.of("luna_flower_speed"), -0.25, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        builder.addStackable(NeoForgeMod.SWIM_SPEED, new AttributeModifier(ChromaticArsenal.of("luna_flower_swim_speed"), -0.25, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        builder.addStackable(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(ChromaticArsenal.of("luna_flower_toughness"), -0.25, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        builder.addStackable(Attributes.BLOCK_INTERACTION_RANGE, new AttributeModifier(ChromaticArsenal.of("luna_flower_block_reach"), -0.35, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        builder.addStackable(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(ChromaticArsenal.of("luna_flower_entity_reach"), -0.35, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        builder.addStackable(Attributes.JUMP_STRENGTH, new AttributeModifier(ChromaticArsenal.of("luna_flower_jump"), -0.25, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        builder.addStackable(Attributes.STEP_HEIGHT, new AttributeModifier(ChromaticArsenal.of("luna_flower_step"), -0.35, AttributeModifier.Operation.ADD_VALUE));
    }
}
