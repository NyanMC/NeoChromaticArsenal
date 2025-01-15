package page.chromanyan.chromaticarsenal.item.superaccessories;

import page.chromanyan.chromaticarsenal.ChromaticArsenal;
import page.chromanyan.chromaticarsenal.init.CAItems;
import page.chromanyan.chromaticarsenal.init.CASounds;
import page.chromanyan.chromaticarsenal.item.base.SuperAccessory;
import page.chromanyan.chromaticarsenal.util.ChromaAccessoryHelper;
import page.chromanyan.chromaticarsenal.util.TooltipHelper;
import io.wispforest.accessories.api.attributes.AccessoryAttributeBuilder;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@EventBusSubscriber
public class PrismaticCrystalAccessory extends SuperAccessory {

    public PrismaticCrystalAccessory() {
        setEquipSound(SoundEvents.END_PORTAL_FRAME_FILL);
        setIncompatibility(CAItems.LUNAR_CRYSTAL);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, list, tooltipFlag);
        if (!Screen.hasShiftDown()) return;
        TooltipHelper.itemTooltipLine(stack, 1, list);
        TooltipHelper.itemTooltipLine(stack, 2, list, TooltipHelper.valueTooltip(ChromaticArsenal.CONFIG.COMMON.prismaticCrystalVoidBounceDamage()));
    }

    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        super.tick(stack, reference);
        LivingEntity living = reference.entity();
        Vec3 vec3 = living.getDeltaMovement();
        if (living.blockPosition().getY() < living.getCommandSenderWorld().getMinBuildHeight() && vec3.y < 0) {
            living.setDeltaMovement(vec3.x, vec3.y * -ChromaticArsenal.CONFIG.COMMON.prismaticCrystalVoidBounceMultiplier(), vec3.z);
            living.hurt(living.getCommandSenderWorld().damageSources().fellOutOfWorld(), ChromaticArsenal.CONFIG.COMMON.prismaticCrystalVoidBounceDamage());
            if (stack.getHoverName().getString().toLowerCase().contains("spring")) {
                living.getCommandSenderWorld().playSound(null, living.blockPosition(), CASounds.SPRING, SoundSource.PLAYERS, 0.5F, 1.0F);
            }
        }
        living.resetFallDistance();
    }

    @Override
    public void getDynamicModifiers(ItemStack stack, SlotReference reference, AccessoryAttributeBuilder builder) {
        builder.addStackable(Attributes.GRAVITY, new AttributeModifier(ChromaticArsenal.of("prismatic_crystal_gravity"), ChromaticArsenal.CONFIG.COMMON.prismaticCrystalGravityModifier(), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    }

    @SubscribeEvent
    public static void mobEffectApplicable(MobEffectEvent.Applicable event) {
        if (!ChromaAccessoryHelper.isAccessoryEquipped(event.getEntity(), CAItems.PRISMATIC_CRYSTAL.get())) return;

        if (event.getEffectInstance().getEffect() == MobEffects.LEVITATION) {
            event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
        }
    }

    @SubscribeEvent
    public static void livingIncomingDamage(LivingIncomingDamageEvent event) {
        if (!ChromaAccessoryHelper.isAccessoryEquipped(event.getEntity(), CAItems.PRISMATIC_CRYSTAL.get())) return;

        // will any mod trigger the second check? probably not. doesn't hurt to be safe though
        if (event.getSource().is(DamageTypeTags.IS_FALL) && !event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            event.setCanceled(true);
        }
    }
}
