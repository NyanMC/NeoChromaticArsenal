package page.chromanyan.chromaticarsenal.item.base;

import page.chromanyan.chromaticarsenal.util.ChromaAccessoryHelper;
import page.chromanyan.chromaticarsenal.util.TooltipHelper;
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
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.Random;

public class ChromaAccessory extends Item implements ICurioItem {

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

    protected void setEquipSound(SoundEvent equipSound) {
        this.setEquipSound(Holder.direct(equipSound));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        if (!Screen.hasShiftDown()) {
            TooltipHelper.itemTooltipLine("shift", tooltipComponents);
        }
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ChromaAccessoryHelper.getCurio(slotContext.entity(), this).isEmpty();
    }

    @Override
    public @NotNull ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        if (equipSound == null)
            return ICurioItem.super.getEquipSound(slotContext, stack);
        else
            return new ICurio.SoundInfo(equipSound.value(), 1, 1);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (!needsDummyUpdater) return;
        // this sucks. i hate doing this. if you know of a better way to do this please feel free to PR
        CustomData.update(DataComponents.CUSTOM_DATA, stack, (tag) -> tag.putDouble("dummy", Math.random()));
    }
}
