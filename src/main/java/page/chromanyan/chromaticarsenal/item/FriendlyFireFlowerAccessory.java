package page.chromanyan.chromaticarsenal.item;

import page.chromanyan.chromaticarsenal.ChromaticArsenal;
import page.chromanyan.chromaticarsenal.init.CAItems;
import page.chromanyan.chromaticarsenal.item.base.ChromaAccessory;
import page.chromanyan.chromaticarsenal.util.ChromaAccessoryHelper;
import page.chromanyan.chromaticarsenal.util.TooltipHelper;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@EventBusSubscriber
public class FriendlyFireFlowerAccessory extends ChromaAccessory {

    public FriendlyFireFlowerAccessory() {
        super(new Properties()
                .stacksTo(1)
                .rarity(Rarity.RARE)
                .durability(25)
                .setNoRepair()
                .fireResistant());
        setEquipSound(SoundEvents.FIRECHARGE_USE);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, list, tooltipFlag);
        if (!Screen.hasShiftDown()) return;
        TooltipHelper.itemTooltipLine(stack, 1, list);
        TooltipHelper.itemTooltipLine(stack, 2, list);
        TooltipHelper.itemTooltipLine(stack, 3, list, TooltipHelper.ticksToSecondsTooltip(ChromaticArsenal.CONFIG.COMMON.friendlyFireFlowerOverheatCooldown()));
    }

    @Override
    public int getBarColor(@NotNull ItemStack stack) {
        return 0xFFAA00;
    }

    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        LivingEntity living = reference.entity();
        int recoveryRate = living.isInPowderSnow ? ChromaticArsenal.CONFIG.COMMON.friendlyFireFlowerRecoveryTime() / 2 : ChromaticArsenal.CONFIG.COMMON.friendlyFireFlowerRecoveryTime();
        if (living instanceof Player player && player.getCooldowns().isOnCooldown(CAItems.FRIENDLY_FIRE_FLOWER.get())) return;
        if (living.getCommandSenderWorld().isClientSide() || living.tickCount % recoveryRate != 0 || stack.getDamageValue() <= 0) return;

        stack.setDamageValue(stack.getDamageValue() - 1);
    }

    @SubscribeEvent
    public static void incomingDamage(LivingIncomingDamageEvent event) {
        if (event.getAmount() == 0 || event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY)) return;

        Entity attacker = event.getSource().getEntity();
        if (event.getSource().is(DamageTypes.THORNS)) {
            event.getEntity().getCommandSenderWorld().playSound(null, event.getEntity().blockPosition(), SoundEvents.ENDER_EYE_DEATH, SoundSource.PLAYERS, 0.5F, 1.0F);
        } else {
            if (!(attacker instanceof LivingEntity livingAttacker)) return;
            if (!ChromaAccessoryHelper.isAccessoryEquipped(event.getEntity(), CAItems.FRIENDLY_FIRE_FLOWER.get())) return;
            if (!(attacker == event.getEntity())) return;
            livingAttacker.getCommandSenderWorld().playSound(null, attacker.blockPosition(), SoundEvents.ENDER_EYE_DEATH, SoundSource.PLAYERS, 0.5F, 1.0F);
        }

        event.setCanceled(true);
    }

    @SubscribeEvent
    public static void livingDamage(LivingDamageEvent.Pre event) {
        if (!event.getSource().is(DamageTypeTags.IS_FIRE)) return;
        if (event.getNewDamage() == 0 || event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY)) return;

        LivingEntity entity = event.getEntity();
        ItemStack stack = ChromaAccessoryHelper.tryGetFirstEquipped(entity, CAItems.FRIENDLY_FIRE_FLOWER.get());
        if (stack == null) return;

        // if the flower's overheating, reset its cooldown. not required but skips doing a bunch of unnecessary stuff
        if (entity instanceof Player player && player.getCooldowns().isOnCooldown(CAItems.FRIENDLY_FIRE_FLOWER.get())) {
            player.getCooldowns().addCooldown(CAItems.FRIENDLY_FIRE_FLOWER.get(), ChromaticArsenal.CONFIG.COMMON.friendlyFireFlowerOverheatCooldown());
            return;
        }

        handleFlowerDamage(event, stack);
    }

    private static void handleFlowerDamage(LivingDamageEvent.Pre event, ItemStack stack) {
        LivingEntity entity = event.getEntity();

        int stackDamageRemaining = stack.getMaxDamage() - stack.getDamageValue(); // how much damage this can still take
        int damageTaken = Mth.ceil(event.getNewDamage()); // ceiled because otherwise you could achieve total fire immunity with protection

        if (damageTaken <= stackDamageRemaining) { // can the flower fully block this?
            event.setNewDamage(0);
            stack.setDamageValue(stack.getDamageValue() + damageTaken);
            return;
        }

        event.setNewDamage(event.getNewDamage() - stackDamageRemaining);
        stack.setDamageValue(stack.getMaxDamage());

        if (!(entity instanceof Player player)) return;
        player.getCooldowns().addCooldown(CAItems.FRIENDLY_FIRE_FLOWER.get(), ChromaticArsenal.CONFIG.COMMON.friendlyFireFlowerOverheatCooldown());
        player.getCommandSenderWorld().playSound(null, player.blockPosition(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 0.5F, 1.0F);
    }
}
