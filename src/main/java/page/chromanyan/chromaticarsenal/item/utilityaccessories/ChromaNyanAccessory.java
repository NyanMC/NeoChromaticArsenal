package page.chromanyan.chromaticarsenal.item.utilityaccessories;

import page.chromanyan.chromaticarsenal.init.CABlocks;
import page.chromanyan.chromaticarsenal.util.ChromaAccessoryHelper;
import page.chromanyan.chromaticarsenal.util.TooltipHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class ChromaNyanAccessory extends BlockItem implements ICurioItem {

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
    public @NotNull ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.WOOL_PLACE, 0.5f, 1);
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ChromaAccessoryHelper.getCurio(slotContext.entity(), this).isEmpty();
    }
}
