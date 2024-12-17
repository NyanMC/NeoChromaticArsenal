package com.chromanyan.chromaticarsenal.item.base;

import com.chromanyan.chromaticarsenal.util.TooltipHelper;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.AccessoryItem;
import io.wispforest.accessories.api.SoundEventData;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class ChromaAccessory extends AccessoryItem {

    protected static final Random rand = new Random();

    private @Nullable Holder<SoundEvent> equipSound;
    private boolean needsDummyUpdater = false;

    public ChromaAccessory(Item.Properties properties) {
        super(properties);
    }

    public ChromaAccessory(Rarity rarity) {
        this(new Item.Properties()
                .stacksTo(1)
                .rarity(rarity));
    }
    public ChromaAccessory() {
        this(Rarity.RARE);
    }

    protected void enableJankAttributeFix() {
        this.needsDummyUpdater = true;
    }

    protected void setEquipSound(@Nullable Holder<SoundEvent> equipSound) {
        this.equipSound = equipSound;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        if (!Screen.hasShiftDown()) {
            TooltipHelper.itemTooltipLine("shift", tooltipComponents);
        }
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

    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        if (!needsDummyUpdater) return;
        // this sucks. i hate doing this. if you know of a better way to do this please feel free to PR
        CustomData.update(DataComponents.CUSTOM_DATA, stack, (tag) -> tag.putDouble("dummy", Math.random()));
    }
}
