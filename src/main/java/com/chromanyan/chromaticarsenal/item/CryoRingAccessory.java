package com.chromanyan.chromaticarsenal.item;

import com.chromanyan.chromaticarsenal.ChromaticArsenal;
import com.chromanyan.chromaticarsenal.init.CAItems;
import com.chromanyan.chromaticarsenal.item.base.ChromaAccessory;
import com.chromanyan.chromaticarsenal.util.ChromaAccessoryHelper;
import com.chromanyan.chromaticarsenal.util.TooltipHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@EventBusSubscriber
public class CryoRingAccessory extends ChromaAccessory {

    public CryoRingAccessory() {
        setEquipSound(SoundEvents.PLAYER_HURT_FREEZE);
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

    private static boolean isGolemEnhanced(Entity entity) {
        if (!(entity instanceof SnowGolem golem)) return false;
        return golem.getPersistentData().getBoolean("cryoRingEnhanced");
    }

    @SubscribeEvent
    public static void livingDamage(LivingDamageEvent.Pre event) {
        if (!(event.getSource().getEntity() instanceof LivingEntity livingEntity)) return;
        if (!ChromaAccessoryHelper.isAccessoryEquipped(livingEntity, CAItems.CRYO_RING.get()) && !isGolemEnhanced(livingEntity)) return;
        if (!(event.getSource().getDirectEntity() instanceof Snowball)) return;

        LivingEntity target = event.getEntity();

        if (target instanceof SnowGolem) {
            target.heal(ChromaticArsenal.CONFIG.COMMON.cryoRingSnowballDamage());
        } else if (target.canFreeze()) {
            int bonusDamage = target.getType().is(EntityTypeTags.FREEZE_HURTS_EXTRA_TYPES) ? ChromaticArsenal.CONFIG.COMMON.cryoRingVulnerableSnowballDamage() : ChromaticArsenal.CONFIG.COMMON.cryoRingSnowballDamage();
            event.setNewDamage(event.getNewDamage() + bonusDamage);
            target.setTicksFrozen(target.getTicksFrozen() + ChromaticArsenal.CONFIG.COMMON.cryoRingFreezeTicks());
        }
    }

    @SubscribeEvent
    public static void entityJoinLevel(EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof SnowGolem golem)) return;

        for (LivingEntity livingEntity : event.getLevel().getEntitiesOfClass(LivingEntity.class, golem.getBoundingBox().inflate(5.0))) {
            if (!ChromaAccessoryHelper.isAccessoryEquipped(livingEntity, CAItems.CRYO_RING.get())) continue;
            golem.getPersistentData().putBoolean("cryoRingEnhanced", true);
            break;
        }
    }
}
