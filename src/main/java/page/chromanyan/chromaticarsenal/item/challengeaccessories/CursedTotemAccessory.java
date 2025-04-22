package page.chromanyan.chromaticarsenal.item.challengeaccessories;

import org.jetbrains.annotations.Nullable;
import page.chromanyan.chromaticarsenal.CAConfig;
import page.chromanyan.chromaticarsenal.init.CAEffects;
import page.chromanyan.chromaticarsenal.init.CAItems;
import page.chromanyan.chromaticarsenal.init.CARarities;
import page.chromanyan.chromaticarsenal.init.CATags;
import page.chromanyan.chromaticarsenal.item.base.ChromaAccessory;
import page.chromanyan.chromaticarsenal.util.ChromaAccessoryHelper;
import page.chromanyan.chromaticarsenal.util.TooltipHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.storage.loot.LootContext;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.List;

@EventBusSubscriber
public class CursedTotemAccessory extends ChromaAccessory {

    private static final String CURSED_REVIVAL_STATE_LOCATION = "chromaticarsenal.cursedRevivalState";

    public CursedTotemAccessory() {
        super(Rarity.valueOf(CARarities.CHALLENGE));
        setEquipSound(SoundEvents.TOTEM_USE);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, list, tooltipFlag);
        if (!Screen.hasShiftDown()) return;

        TooltipHelper.itemTooltipLine(stack, 1, list);
        TooltipHelper.itemTooltipLine(stack, 2, list, TooltipHelper.percentTooltip((CAConfig.cursedTotemFracturedAmplifier + 1) * 0.1F));
        TooltipHelper.itemTooltipLine(stack, 3, list, TooltipHelper.valueTooltip(CAConfig.cursedTotemLootingBonus));
    }

    @Override
    public @NotNull ICurio.DropRule getDropRule(SlotContext slotContext, DamageSource source, boolean recentlyHit, ItemStack stack) {
        return ICurio.DropRule.ALWAYS_KEEP;
    }

    @SubscribeEvent
    public static void livingDamage(LivingDamageEvent.Post event) {
        LivingEntity target = event.getEntity();
        if (target.getPersistentData().getInt(CURSED_REVIVAL_STATE_LOCATION) > 0) return;
        // this has a *really funny* interaction with bosses, but it also breaks the game. don't proc for bosses
        if (target.getType().is(CATags.EntityTypes.IMMUNE_TO_CURSED_REVIVAL)) return;

        if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) return;
        if (!ChromaAccessoryHelper.isAccessoryEquipped(attacker, CAItems.CURSED_TOTEM.get())) return;

        target.getPersistentData().putInt(CURSED_REVIVAL_STATE_LOCATION, 1);
    }

    @SubscribeEvent
    public static void livingDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.getPersistentData().getInt(CURSED_REVIVAL_STATE_LOCATION) != 1) return;

        event.setCanceled(true);
        entity.getPersistentData().putInt(CURSED_REVIVAL_STATE_LOCATION, 2);
        entity.getCommandSenderWorld().playSound(null, entity.blockPosition(), SoundEvents.IRON_GOLEM_DAMAGE, SoundSource.PLAYERS, 0.5F, 1.0F);
        entity.addEffect(new MobEffectInstance(CAEffects.FRACTURED, MobEffectInstance.INFINITE_DURATION, CAConfig.cursedTotemFracturedAmplifier, true, false));
        entity.setHealth(entity.getMaxHealth());
    }

    @Override
    public int getLootingLevel(SlotContext slotContext, @Nullable LootContext lootContext, ItemStack stack) {
        return CAConfig.cursedTotemLootingBonus;
    }
}
