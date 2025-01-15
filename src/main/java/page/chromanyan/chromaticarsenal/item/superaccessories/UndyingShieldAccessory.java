package page.chromanyan.chromaticarsenal.item.superaccessories;

import page.chromanyan.chromaticarsenal.ChromaticArsenal;
import page.chromanyan.chromaticarsenal.init.CADamageTypes;
import page.chromanyan.chromaticarsenal.init.CAItems;
import page.chromanyan.chromaticarsenal.item.base.SuperAccessory;
import page.chromanyan.chromaticarsenal.util.ChromaAccessoryHelper;
import page.chromanyan.chromaticarsenal.util.TooltipHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@EventBusSubscriber
public class UndyingShieldAccessory extends SuperAccessory {

    public static final String DEATH_CLOCK = "chromaticarsenal.deathclock";
    public static final String DELAYED_DEATH = "chromaticarsenal.delaydeath";

    public UndyingShieldAccessory() {
        setEquipSound(SoundEvents.GLASS_PLACE);
        setIncompatibility(CAItems.GLASS_SHIELD);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, list, tooltipFlag);
        if (!Screen.hasShiftDown()) return;

        TooltipHelper.itemTooltipLine(stack, 1, list, TooltipHelper.ticksToSecondsTooltip(ChromaticArsenal.CONFIG.COMMON.undyingShieldDeathClockTicks()));
        TooltipHelper.itemTooltipLine(stack, 2, list);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void livingDeath(LivingDeathEvent event) {
        if (event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY)) return;
        LivingEntity entity = event.getEntity();
        CompoundTag persistentData = entity.getPersistentData();

        if (persistentData.getBoolean(DELAYED_DEATH) && persistentData.getInt(DEATH_CLOCK) > 0) {
            event.setCanceled(true);
            entity.setHealth(1);
            return;
        }

        ItemStack stack = ChromaAccessoryHelper.tryGetFirstEquipped(entity, CAItems.UNDYING_SHIELD.get());
        if (stack == null) return;
        event.setCanceled(true);
        entity.setHealth(1);
        persistentData.putBoolean(DELAYED_DEATH, true);
        persistentData.putInt(DEATH_CLOCK, ChromaticArsenal.CONFIG.COMMON.undyingShieldDeathClockTicks());
        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, MobEffectInstance.INFINITE_DURATION));
        entity.getCommandSenderWorld().playSound(null, entity.blockPosition(), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 0.5F, 1.0F);
    }

    @SubscribeEvent
    public static void entityTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof LivingEntity entity)) return;
        CompoundTag persistentData = entity.getPersistentData();
        if (!persistentData.getBoolean(DELAYED_DEATH)) return;

        int clock = persistentData.getInt(DEATH_CLOCK);

        if (clock <= 0) {
            entity.hurt(entity.damageSources().source(CADamageTypes.DEATH_CLOCK), 100000F);
            // this is bound to kill them eventually...right?
            return;
        }

        if (clock % 20 == 0 && entity instanceof Player player) {
            player.displayClientMessage(Component.literal("" + clock / 20).withStyle(ChatFormatting.DARK_RED), true);
            entity.getCommandSenderWorld().playSound(null, entity.blockPosition(), SoundEvents.NOTE_BLOCK_CHIME.value(), SoundSource.PLAYERS, 0.5F, 1.0F);
        }

        persistentData.putInt(DEATH_CLOCK, clock - 1);
    }
}
