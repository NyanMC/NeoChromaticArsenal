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
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

@EventBusSubscriber
public class DiamondHeartAccessory extends SuperAccessory {

    public DiamondHeartAccessory() {
        super(new Properties()
                .stacksTo(1)
                .rarity(Rarity.EPIC)
                .durability(60)
                .setNoRepair());

        setEquipSound(SoundEvents.ARMOR_EQUIP_DIAMOND);
        setIncompatibility(CAItems.GOLDEN_HEART);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, list, tooltipFlag);
        if (!Screen.hasShiftDown()) return;

        TooltipHelper.itemTooltipLine(stack, 1, list);
        TooltipHelper.itemTooltipLine(stack, 2, list, TooltipHelper.valueTooltip((CAConfig.diamondHeartFracturedAmplifier + 1) * 10), TooltipHelper.ticksToSecondsTooltip(60 * 20 * CAConfig.diamondHeartCooldownMinutes));
        TooltipHelper.itemTooltipLine(stack, 3, list, TooltipHelper.ticksToSecondsTooltip(60 * 100));
    }

    @Override
    public int getBarColor(@NotNull ItemStack stack) {
        return 0x00FFFF;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        super.curioTick(slotContext, stack);

        LivingEntity entity = slotContext.entity();
        if (entity.getCommandSenderWorld().isClientSide()) return;
        if (stack.getDamageValue() == 0) return;
        if (entity.tickCount % (20 * CAConfig.diamondHeartCooldownMinutes) != 0) return;

        stack.setDamageValue(stack.getDamageValue() - 1);

        if (stack.getDamageValue() == 0 && entity instanceof Player playerEntity) {
            playerEntity.displayClientMessage(Component.translatable("message.chromaticarsenal.revival_cooldown_finished"), true);
            entity.getCommandSenderWorld().playSound(null, entity.blockPosition(), SoundEvents.IRON_GOLEM_REPAIR, SoundSource.PLAYERS, 0.5F, 1.0F);
        }
    }

    @SubscribeEvent
    public static void livingDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.hasEffect(CAEffects.FRACTURED)) return;
        if (event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY)) return;

        ItemStack stack = ChromaAccessoryHelper.tryGetFirstEquipped(entity, CAItems.DIAMOND_HEART.get());
        if (stack == null || stack.getDamageValue() != 0) return;

        stack.setDamageValue(stack.getMaxDamage());
        event.setCanceled(true);
        entity.addEffect(new MobEffectInstance(CAEffects.FRACTURED, 60 * 20 * CAConfig.diamondHeartCooldownMinutes, CAConfig.diamondHeartFracturedAmplifier), entity);
        entity.setHealth(entity.getMaxHealth());
        entity.getCommandSenderWorld().playSound(null, entity.blockPosition(), SoundEvents.IRON_GOLEM_DAMAGE, SoundSource.PLAYERS, 0.5F, 1.0F);
    }
}
