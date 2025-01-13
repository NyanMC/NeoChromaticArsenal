package com.chromanyan.chromaticarsenal.item.basicaccessories;

import com.chromanyan.chromaticarsenal.ChromaticArsenal;
import com.chromanyan.chromaticarsenal.item.base.ChromaAccessory;
import io.wispforest.accessories.api.attributes.AccessoryAttributeBuilder;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AmethystRingAccessory extends ChromaAccessory {

    public AmethystRingAccessory() {
        super(Rarity.COMMON);
        setEquipSound(SoundEvents.AMETHYST_BLOCK_PLACE);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        // yeah it's literally nothing. we do this just so that the "hold shift" message doesn't show
    }

    @Override
    public void getDynamicModifiers(ItemStack stack, SlotReference reference, AccessoryAttributeBuilder builder) {
        super.getDynamicModifiers(stack, reference, builder);
        builder.addStackable(Attributes.BLOCK_INTERACTION_RANGE, new AttributeModifier(ChromaticArsenal.of("amethyst_ring_block"), ChromaticArsenal.CONFIG.COMMON.amethystRingReachModifier(), AttributeModifier.Operation.ADD_VALUE));
        builder.addStackable(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(ChromaticArsenal.of("amethyst_ring_entity"), ChromaticArsenal.CONFIG.COMMON.amethystRingReachModifier(), AttributeModifier.Operation.ADD_VALUE));
    }
}
