package page.chromanyan.chromaticarsenal.item;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import page.chromanyan.chromaticarsenal.CAConfig;
import page.chromanyan.chromaticarsenal.ChromaticArsenal;
import page.chromanyan.chromaticarsenal.init.CAEffects;
import page.chromanyan.chromaticarsenal.item.base.ChromaAccessory;
import page.chromanyan.chromaticarsenal.util.TooltipHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.NeoForgeMod;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class BubbleAmuletAccessory extends ChromaAccessory {

    public BubbleAmuletAccessory() {
        super(new Properties()
                .stacksTo(1)
                .rarity(Rarity.RARE)
                .durability(300)
                .setNoRepair()
                .fireResistant());
        setEquipSound(SoundEvents.BUCKET_FILL);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, list, tooltipFlag);
        if (!Screen.hasShiftDown()) return;

        TooltipHelper.itemTooltipLine(stack, 1, list);
        TooltipHelper.itemTooltipLine(stack, 2, list);
        TooltipHelper.itemTooltipLine(stack, 3, list);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity living = slotContext.entity();
        if (living.getCommandSenderWorld().isClientSide) return;

        int airSupply = living.getAirSupply();
        int maxAirSupply = living.getMaxAirSupply();
        int currentDamage = stack.getDamageValue();

        if (airSupply <= 0) {
            int damageRemaining = stack.getMaxDamage() - currentDamage;
            if (damageRemaining > 0) {
                living.setAirSupply(damageRemaining);
                stack.setDamageValue(stack.getMaxDamage());
                living.getCommandSenderWorld().playSound(null, living.blockPosition(), SoundEvents.BOTTLE_EMPTY, SoundSource.PLAYERS, 0.5F, 1.0F);
            } else {
                living.addEffect(new MobEffectInstance(CAEffects.BUBBLE_PANIC, 10));
            }
        }

        if (airSupply >= maxAirSupply && !living.isUnderWater() && currentDamage > 0) {
            stack.setDamageValue(currentDamage - 1);
            if (currentDamage - 1 == 0) {
                living.getCommandSenderWorld().playSound(null, living.blockPosition(), SoundEvents.BOTTLE_FILL, SoundSource.PLAYERS, 0.5F, 1.0F);
            }
        }
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(NeoForgeMod.SWIM_SPEED, new AttributeModifier(ChromaticArsenal.of("bubble_amulet_swim_speed"), CAConfig.bubbleAmuletSwimSpeedModifier, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        return atts;
    }

    @Override
    public int getBarColor(@NotNull ItemStack stack) {
        return 0xAAAAFF;
    }
}
