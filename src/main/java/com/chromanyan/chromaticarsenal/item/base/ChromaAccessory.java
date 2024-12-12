package com.chromanyan.chromaticarsenal.item.base;

import com.chromanyan.chromaticarsenal.util.TooltipHelper;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.AccessoryItem;
import io.wispforest.accessories.api.SoundEventData;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ChromaAccessory extends AccessoryItem {

    private final @Nullable Holder<SoundEvent> equipSound;

    public ChromaAccessory(Rarity rarity, @Nullable Holder<SoundEvent> equipSound) {
        super(new Item.Properties()
                .stacksTo(1)
                .rarity(rarity));
        this.equipSound = equipSound;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        if (!Screen.hasShiftDown()) {
            TooltipHelper.itemTooltipLine("shift", tooltipComponents);
        }
    }

    public ChromaAccessory(@Nullable Holder<SoundEvent> equipSound) {
        this(Rarity.RARE, equipSound);
    }

    public ChromaAccessory() {
        this(null);
    }

    @Override
    public boolean canEquip(ItemStack stack, SlotReference reference) {
        AccessoriesCapability cap = AccessoriesCapability.get(reference.entity());
        if (cap == null) return false;

        return !cap.isAnotherEquipped(stack, reference, this);
    }

    @Override
    public @Nullable SoundEventData getEquipSound(ItemStack stack, SlotReference reference) {
        if (equipSound == null) return null;

        return new SoundEventData(equipSound, 0.5f, 1);
    }
}
