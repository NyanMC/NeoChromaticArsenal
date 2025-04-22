package page.chromanyan.chromaticarsenal.item.utilityaccessories;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import page.chromanyan.chromaticarsenal.ChromaticArsenal;
import page.chromanyan.chromaticarsenal.item.base.ChromaAccessory;
import page.chromanyan.chromaticarsenal.util.TooltipHelper;
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
import top.theillusivec4.curios.api.SlotContext;

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
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> atts = LinkedHashMultimap.create();

        atts.put(NeoForgeMod.NAMETAG_DISTANCE, new AttributeModifier(ChromaticArsenal.of("anonymity"), -1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        return atts;
    }
}
