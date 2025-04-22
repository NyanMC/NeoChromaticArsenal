package page.chromanyan.chromaticarsenal.item.basicaccessories;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import page.chromanyan.chromaticarsenal.CAConfig;
import page.chromanyan.chromaticarsenal.ChromaticArsenal;
import page.chromanyan.chromaticarsenal.item.base.ChromaAccessory;
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
import top.theillusivec4.curios.api.SlotContext;

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
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> atts = LinkedHashMultimap.create();

        atts.put(Attributes.BLOCK_INTERACTION_RANGE, new AttributeModifier(ChromaticArsenal.of("amethyst_ring_block"), CAConfig.amethystRingReachModifier, AttributeModifier.Operation.ADD_VALUE));
        atts.put(Attributes.BLOCK_INTERACTION_RANGE, new AttributeModifier(ChromaticArsenal.of("amethyst_ring_block"), CAConfig.amethystRingReachModifier, AttributeModifier.Operation.ADD_VALUE));

        return atts;
    }
}
