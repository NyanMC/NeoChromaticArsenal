package page.chromanyan.chromaticarsenal.item.superaccessories;

import page.chromanyan.chromaticarsenal.CAConfig;
import page.chromanyan.chromaticarsenal.init.CAEffects;
import page.chromanyan.chromaticarsenal.init.CAItems;
import page.chromanyan.chromaticarsenal.item.base.SuperAccessory;
import page.chromanyan.chromaticarsenal.util.ChromaAccessoryHelper;
import page.chromanyan.chromaticarsenal.util.TooltipHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

@EventBusSubscriber
public class InfernoFlowerAccessory extends SuperAccessory {

    public InfernoFlowerAccessory() {
        setEquipSound(SoundEvents.FIRECHARGE_USE);
        setIncompatibility(CAItems.FRIENDLY_FIRE_FLOWER);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, list, tooltipFlag);
        if (!Screen.hasShiftDown()) return;
        TooltipHelper.itemTooltipLine(stack, 1, list);
        TooltipHelper.itemTooltipLine(stack, 2, list);
        TooltipHelper.itemTooltipLine(stack, 3, list, TooltipHelper.multiplierAsPercentTooltip(CAConfig.infernoFlowerDamageMultiplier));
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        super.curioTick(slotContext, stack);
        LivingEntity living = slotContext.entity();

        if (living.getCommandSenderWorld().isClientSide) return;

        living.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 25, 0, true, false));
    }

    @SubscribeEvent
    public static void livingDamage(LivingDamageEvent.Pre event) {
        LivingEntity target = event.getEntity();
        if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) return;
        if (!ChromaAccessoryHelper.isAccessoryEquipped(attacker, CAItems.INFERNO_FLOWER.get())) return;

        if (!event.getSource().is(DamageTypeTags.IS_PROJECTILE)) {
            target.addEffect(new MobEffectInstance(CAEffects.INFERNO, CAConfig.infernoFlowerDuration));
        }

        if (target.isOnFire()) {
            event.setNewDamage(event.getNewDamage() * CAConfig.infernoFlowerDamageMultiplier);
        }
    }
}
