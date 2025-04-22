package page.chromanyan.chromaticarsenal.item.superaccessories;

import page.chromanyan.chromaticarsenal.CAConfig;
import page.chromanyan.chromaticarsenal.init.CAItems;
import page.chromanyan.chromaticarsenal.init.CATags;
import page.chromanyan.chromaticarsenal.item.base.SuperAccessory;
import page.chromanyan.chromaticarsenal.util.ChromaAccessoryHelper;
import page.chromanyan.chromaticarsenal.util.ConfigHelper;
import page.chromanyan.chromaticarsenal.util.TooltipHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@EventBusSubscriber
public class DispellingCrystalAccessory extends SuperAccessory {

    public DispellingCrystalAccessory() {
        setEquipSound(SoundEvents.AMETHYST_BLOCK_PLACE);
        setIncompatibility(CAItems.WARD_CRYSTAL);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, list, tooltipFlag);
        if (!Screen.hasShiftDown()) return;
        TooltipHelper.itemTooltipLine("ward_crystal", 1, list, TooltipHelper.multiplierAsPercentTooltip(CAConfig.wardCrystalIncomingMultiplier));
        TooltipHelper.itemTooltipLine("ward_crystal", 2, list, TooltipHelper.multiplierAsPercentTooltip(CAConfig.wardCrystalOutgoingMultiplier));
        TooltipHelper.itemTooltipLine(stack, 1, list, TooltipHelper.multiplierAsPercentTooltip(CAConfig.dispellingCrystalDurationMultiplier));
    }

    @SubscribeEvent
    public static void livingDamage(LivingDamageEvent.Pre event) {
        if (event.getSource().is(CATags.DamageTypes.IMMUNE_TO_WARD_CRYSTAL)) return;
        if (event.getNewDamage() == 0 || event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY)) return;

        if (ChromaAccessoryHelper.isAccessoryEquipped(event.getEntity(), CAItems.DISPELLING_CRYSTAL.get())) {
            event.setNewDamage(event.getNewDamage() * (float) CAConfig.wardCrystalIncomingMultiplier);
        }

        if (!(event.getSource().getEntity() instanceof LivingEntity livingEntity)) return;
        if (!ChromaAccessoryHelper.isAccessoryEquipped(livingEntity, CAItems.DISPELLING_CRYSTAL.get())) return;

        event.setNewDamage(event.getNewDamage() * (float) CAConfig.wardCrystalOutgoingMultiplier);
    }

    @SubscribeEvent
    public static void effectAdded(MobEffectEvent.Added event) {
        if (!ChromaAccessoryHelper.isAccessoryEquipped(event.getEntity(), CAItems.DISPELLING_CRYSTAL.get())) return;
        if (ConfigHelper.effectInBlacklist(CAConfig.dispellingCrystalEffectBlacklist, event.getEffectInstance().getEffect().value())) return;
        if (event.getEffectInstance().getDuration() == MobEffectInstance.INFINITE_DURATION) return;
        // eat your heart out minecraft. i don't give a damn if you don't want me accessing this
        event.getEffectInstance().duration = (int) (event.getEffectInstance().duration * CAConfig.dispellingCrystalDurationMultiplier);
    }
}
