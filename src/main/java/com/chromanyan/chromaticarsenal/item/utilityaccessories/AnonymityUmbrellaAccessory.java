package com.chromanyan.chromaticarsenal.item.utilityaccessories;

import com.chromanyan.chromaticarsenal.ChromaticArsenal;
import com.chromanyan.chromaticarsenal.item.base.ChromaAccessory;
import com.chromanyan.chromaticarsenal.util.TooltipHelper;
import io.wispforest.accessories.api.components.AccessoryItemAttributeModifiers;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.NeoForgeMod;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AnonymityUmbrellaAccessory extends ChromaAccessory {

    public AnonymityUmbrellaAccessory() {
        super(Rarity.UNCOMMON);
        setEquipSound(SoundEvents.WOOL_PLACE);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, @NotNull Item.TooltipContext context, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, list, tooltipFlag);
        if (!Screen.hasShiftDown()) return;

        TooltipHelper.itemTooltipLine(stack, 1, list);
    }

    @Override
    public void getStaticModifiers(Item item, AccessoryItemAttributeModifiers.Builder builder) {
        super.getStaticModifiers(item, builder);

        builder.addForAny(NeoForgeMod.NAMETAG_DISTANCE, new AttributeModifier(ChromaticArsenal.of("anonymity"), -1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), true);
    }
}
