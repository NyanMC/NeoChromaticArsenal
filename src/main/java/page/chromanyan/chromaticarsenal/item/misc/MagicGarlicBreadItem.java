package page.chromanyan.chromaticarsenal.item.misc;

import page.chromanyan.chromaticarsenal.CAConfig;
import page.chromanyan.chromaticarsenal.init.CAItems;
import page.chromanyan.chromaticarsenal.util.ChromaAccessoryHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class MagicGarlicBreadItem extends Item {

    public MagicGarlicBreadItem() {
        super(new Item.Properties()
                .stacksTo(64)
                .rarity(Rarity.UNCOMMON)
                .food(new FoodProperties.Builder()
                        .nutrition(10)
                        .saturationModifier(0.7f)
                        .alwaysEdible()
                        .build()));
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, Level world, @NotNull LivingEntity player) {
        if (world.isClientSide()) return super.finishUsingItem(stack, world, player);
        if (!ChromaAccessoryHelper.isAccessoryEquipped(player, CAItems.DUALITY_RINGS.get())) return super.finishUsingItem(stack, world, player);

        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, CAConfig.dualityRingsStrengthDuration, CAConfig.dualityRingsStrengthAmplifier, true, true), player);
        player.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, CAConfig.dualityRingsHealthBoostDuration, CAConfig.dualityRingsHealthBoostAmplifier, true, true), player);

        return super.finishUsingItem(stack, world, player);
    }

}
