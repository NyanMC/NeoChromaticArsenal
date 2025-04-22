package page.chromanyan.chromaticarsenal.item;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import page.chromanyan.chromaticarsenal.CAConfig;
import page.chromanyan.chromaticarsenal.ChromaticArsenal;
import page.chromanyan.chromaticarsenal.init.CAItems;
import page.chromanyan.chromaticarsenal.item.base.ChromaAccessory;
import page.chromanyan.chromaticarsenal.util.ChromaAccessoryHelper;
import page.chromanyan.chromaticarsenal.util.TooltipHelper;
import com.mojang.authlib.properties.PropertyMap;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.Optional;

@EventBusSubscriber
public class LunarCrystalAccessory extends ChromaAccessory {

    public LunarCrystalAccessory() {
        setEquipSound(SoundEvents.END_PORTAL_FRAME_FILL);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, list, tooltipFlag);
        if (!Screen.hasShiftDown()) return;
        TooltipHelper.itemTooltipLine(stack, 1, list);
        TooltipHelper.itemTooltipLine(stack, 2, list, TooltipHelper.valueTooltip(CAConfig.lunarCrystalLevitationChance), TooltipHelper.potionAmplifierTooltip(CAConfig.lunarCrystalLevitationAmplifier), TooltipHelper.ticksToSecondsTooltip(CAConfig.lunarCrystalLevitationDuration));
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(Attributes.GRAVITY, new AttributeModifier(ChromaticArsenal.of("lunar_crystal_gravity"), CAConfig.lunarCrystalGravityModifier, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        atts.put(Attributes.SAFE_FALL_DISTANCE, new AttributeModifier(ChromaticArsenal.of("lunar_crystal_safe_fall"), CAConfig.lunarCrystalSafeFallDistanceModifier, AttributeModifier.Operation.ADD_VALUE));
        return atts;
    }

    @Override
    public @NotNull Component getName(ItemStack stack) {
        ResolvableProfile resolvableprofile = stack.get(DataComponents.PROFILE);
        // the second statement *should* always be true...unless you use commands to cheat one in with bad data
        if (resolvableprofile == null || resolvableprofile.id().isEmpty()) return super.getName(stack);
        if (!resolvableprofile.id().get().toString().equalsIgnoreCase("854adc0b-ae55-48d6-b7ba-e641a1eebf42")) return super.getName(stack);
        return Component.translatable(this.getDescriptionId(stack) + ".luna");
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slotId, boolean isSelected) {
        if (stack.get(DataComponents.PROFILE) != null) return;
        if (!(entity instanceof Player player)) return;
        // we never need the player name so we can leave that empty
        stack.set(DataComponents.PROFILE, new ResolvableProfile(Optional.empty(), Optional.of(player.getUUID()), new PropertyMap()));
    }

    @SubscribeEvent
    public static void mobEffectApplicable(MobEffectEvent.Applicable event) {
        if (!ChromaAccessoryHelper.isAccessoryEquipped(event.getEntity(), CAItems.LUNAR_CRYSTAL.get())) return;

        if (event.getEffectInstance().getEffect() == MobEffects.LEVITATION) {
            event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
        }
    }

    @SubscribeEvent
    public static void livingDamage(LivingDamageEvent.Post event) {
        LivingEntity target = event.getEntity();
        if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) return;
        if (!ChromaAccessoryHelper.isAccessoryEquipped(attacker, CAItems.LUNAR_CRYSTAL.get())) return;

        int randresult = rand.nextInt(CAConfig.lunarCrystalLevitationChance - 1);
        if (randresult > 0) return;

        target.addEffect(new MobEffectInstance(MobEffects.LEVITATION, CAConfig.lunarCrystalLevitationDuration, CAConfig.lunarCrystalLevitationAmplifier), attacker);
    }
}
