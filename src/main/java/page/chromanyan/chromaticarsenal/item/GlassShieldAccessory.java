package page.chromanyan.chromaticarsenal.item;

import page.chromanyan.chromaticarsenal.CAConfig;
import page.chromanyan.chromaticarsenal.init.CAItems;
import page.chromanyan.chromaticarsenal.init.CATriggers;
import page.chromanyan.chromaticarsenal.item.base.ChromaAccessory;
import page.chromanyan.chromaticarsenal.util.ChromaAccessoryHelper;
import page.chromanyan.chromaticarsenal.util.TooltipHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

@EventBusSubscriber
public class GlassShieldAccessory extends ChromaAccessory {

    public GlassShieldAccessory() {
        setEquipSound(SoundEvents.GLASS_PLACE);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, list, tooltipFlag);
        if (!Screen.hasShiftDown()) return;
        TooltipHelper.itemTooltipLine(stack, 1, list);
        TooltipHelper.itemTooltipLine(stack, 2, list, TooltipHelper.ticksToSecondsTooltip(CAConfig.glassShieldCooldown));
    }

    @Override
    public void onDestroyed(@NotNull ItemEntity itemEntity, @NotNull DamageSource damageSource) {
        if (!damageSource.is(DamageTypes.LIGHTNING_BOLT) || !CAConfig.thunderguardDefaultRecipe) return;
        ItemEntity newEntity = new ItemEntity(itemEntity.getCommandSenderWorld(), itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(), new ItemStack(CAItems.THUNDERGUARD.get()));
        itemEntity.getCommandSenderWorld().addFreshEntity(newEntity);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (!(slotContext.entity() instanceof Player player)) return;
        ItemCooldowns cooldowns = player.getCooldowns();
        ItemCooldowns.CooldownInstance cooldownInstance = cooldowns.cooldowns.get(CAItems.GLASS_SHIELD.get());
        if (cooldownInstance == null) return;
        //ChromaticArsenal.LOGGER.debug("" + (cooldownInstance.endTime - cooldowns.tickCount));
        if (cooldownInstance.endTime - cooldowns.tickCount == 1) {
            player.getCommandSenderWorld().playSound(null, player.blockPosition(), SoundEvents.GLASS_PLACE, SoundSource.PLAYERS, 0.5F, 1.0F);
        }
    }

    @SubscribeEvent
    public static void livingIncomingDamage(LivingIncomingDamageEvent event) {
        if (event.getAmount() == 0 || event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY) || event.getAmount() >= CAConfig.glassShieldMaxBaseDamage) return;

        LivingEntity entity = event.getEntity();

        if (!ChromaAccessoryHelper.isAccessoryEquipped(entity, CAItems.GLASS_SHIELD.get())) return;

        if (!(entity instanceof Player player)) return;
        if (player.getCooldowns().isOnCooldown(CAItems.GLASS_SHIELD.get())) return;

        player.getCommandSenderWorld().playSound(null, player.blockPosition(), SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 0.5F, 1.0F);
        player.getCooldowns().addCooldown(CAItems.GLASS_SHIELD.get(), CAConfig.glassShieldCooldown);

        if (player instanceof ServerPlayer serverPlayer) {
            CATriggers.GLASS_SHIELD_BLOCK.get().trigger(serverPlayer, Math.round(event.getAmount()));
        }

        event.setCanceled(true);
    }
}
