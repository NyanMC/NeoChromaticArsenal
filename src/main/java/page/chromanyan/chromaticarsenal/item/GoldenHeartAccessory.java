package page.chromanyan.chromaticarsenal.item;

import page.chromanyan.chromaticarsenal.CAConfig;
import page.chromanyan.chromaticarsenal.item.base.ChromaAccessory;
import page.chromanyan.chromaticarsenal.util.TooltipHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class GoldenHeartAccessory extends ChromaAccessory {

    public GoldenHeartAccessory() {
        setEquipSound(SoundEvents.ARMOR_EQUIP_GOLD);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (!slotContext.entity().getCommandSenderWorld().isClientSide()) {
            slotContext.entity().addEffect(new MobEffectInstance(MobEffects.ABSORPTION, CAConfig.goldenHeartDuration, CAConfig.goldenHeartAmplifier, true, false), slotContext.entity());
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, list, tooltipFlag);
        if (!Screen.hasShiftDown()) return;
        TooltipHelper.itemTooltipLine(stack, 3, list);
        TooltipHelper.itemTooltipLine(stack, 1, list, TooltipHelper.potionAmplifierTooltip(CAConfig.goldenHeartAmplifier));
        TooltipHelper.itemTooltipLine(stack, 2, list, TooltipHelper.ticksToSecondsTooltip(CAConfig.goldenHeartDuration));
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity living = slotContext.entity();
        if (!living.getCommandSenderWorld().isClientSide() && living.tickCount % CAConfig.goldenHeartDuration == 0) {
            living.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, CAConfig.goldenHeartDuration, CAConfig.goldenHeartAmplifier, true, false), slotContext.entity());
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        LivingEntity living = slotContext.entity();
        if (living.getCommandSenderWorld().isClientSide) return;
        living.removeEffect(MobEffects.ABSORPTION);
    }

    @Override
    public boolean makesPiglinsNeutral(SlotContext slotContext, ItemStack stack) {
        return true;
    }
}
