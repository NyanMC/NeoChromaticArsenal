package com.chromanyan.chromaticarsenal.item.base;

import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.AccessoryItem;
import io.wispforest.accessories.api.SoundEventData;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.Nullable;

public class ChromaAccessory extends AccessoryItem {

    private final @Nullable Holder<SoundEvent> equipSound;

    public ChromaAccessory(Rarity rarity, @Nullable Holder<SoundEvent> equipSound) {
        super(new Item.Properties()
                .stacksTo(1)
                .rarity(rarity));
        this.equipSound = equipSound;
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
