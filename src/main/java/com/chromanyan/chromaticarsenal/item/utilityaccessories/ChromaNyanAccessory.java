package com.chromanyan.chromaticarsenal.item.utilityaccessories;

import com.chromanyan.chromaticarsenal.init.CABlocks;
import com.chromanyan.chromaticarsenal.util.TooltipHelper;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.SoundEventData;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ChromaNyanAccessory extends BlockItem implements Accessory {

    public ChromaNyanAccessory() {
        super(CABlocks.CHROMANYAN.get(), new Item.Properties()
                .stacksTo(1)
                .rarity(Rarity.UNCOMMON));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        TooltipHelper.itemTooltipLine("vanity", list);
        TooltipHelper.itemTooltipLine("can_place", list);
        TooltipHelper.itemTooltipLine("impersonation", list);
    }

    @Override
    public @Nullable SoundEventData getEquipSound(ItemStack stack, SlotReference reference) {
        return new SoundEventData(Holder.direct(SoundEvents.WOOL_PLACE), 0.5f, 1);
    }

    @Override
    public boolean canEquip(ItemStack stack, SlotReference reference) {
        AccessoriesCapability cap = AccessoriesCapability.get(reference.entity());
        if (cap == null) return false;

        return !cap.isAnotherEquipped(stack, reference, this);
    }
}
