package com.chromanyan.chromaticarsenal.item.utilityaccessories;

import com.chromanyan.chromaticarsenal.item.base.ChromaAccessory;
import com.chromanyan.chromaticarsenal.util.TooltipHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GravityStoneAccessory extends ChromaAccessory {

    public GravityStoneAccessory() {
        super(Rarity.UNCOMMON);
        setEquipSound(SoundEvents.DEEPSLATE_FALL);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, @NotNull Item.TooltipContext context, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, list, tooltipFlag);
        if (!Screen.hasShiftDown()) return;

        TooltipHelper.itemTooltipLine(stack, 1, list);
        TooltipHelper.itemTooltipLine(stack, 2, list);
        TooltipHelper.itemTooltipLine(stack, 3, list);
    }
}
