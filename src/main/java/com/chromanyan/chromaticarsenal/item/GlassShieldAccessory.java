package com.chromanyan.chromaticarsenal.item;

import com.chromanyan.chromaticarsenal.Config;
import com.chromanyan.chromaticarsenal.init.CAItems;
import com.chromanyan.chromaticarsenal.item.base.ChromaAccessory;
import com.chromanyan.chromaticarsenal.util.ChromaAccessoryHelper;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber
public class GlassShieldAccessory extends ChromaAccessory {

    public GlassShieldAccessory() {
        super(Holder.direct(SoundEvents.GLASS_PLACE));
    }

    @Override
    public void onDestroyed(@NotNull ItemEntity itemEntity, @NotNull DamageSource damageSource) {
        //TODO Thunderguard transform
    }

    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        if (!(reference.entity() instanceof Player player)) return;
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
        LivingEntity entity = event.getEntity();
        ItemStack stack = ChromaAccessoryHelper.tryGetFirstEquipped(entity, CAItems.GLASS_SHIELD.get());
        if (stack == null) return;

        if (!(entity instanceof Player player)) return;
        if (player.getCooldowns().isOnCooldown(CAItems.GLASS_SHIELD.get())) return;

        player.getCommandSenderWorld().playSound(null, player.blockPosition(), SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 0.5F, 1.0F);
        player.getCooldowns().addCooldown(CAItems.GLASS_SHIELD.get(), Config.glassShieldCooldown);

        //TODO this is where we would handle statistics...if they were implemented

        //TODO this is where we would handle LTDAD advancement logic...if that was implemented

        event.setCanceled(true);
    }
}
