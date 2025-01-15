package page.chromanyan.chromaticarsenal.item.misc;

import page.chromanyan.chromaticarsenal.ChromaticArsenal;
import page.chromanyan.chromaticarsenal.init.CAAttachments;
import page.chromanyan.chromaticarsenal.util.TooltipHelper;
import com.google.common.collect.HashMultimap;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@EventBusSubscriber
public class CosmicolaItem extends Item {

    private static final ResourceLocation BONUS_CHARM_MOD = ChromaticArsenal.of("bonus_charm_slots");

    public CosmicolaItem() {
        super(new Item.Properties()
                .stacksTo(64)
                .rarity(Rarity.EPIC)
                .food(new FoodProperties.Builder()
                        .nutrition(0)
                        .saturationModifier(0)
                        .alwaysEdible()
                        .build()));
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        TooltipHelper.itemTooltipLine(stack, 1, list);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity livingEntity) {
        if (!(livingEntity instanceof ServerPlayer serverPlayer)) return super.finishUsingItem(stack, level, livingEntity);

        livingEntity.getCommandSenderWorld().playSound(null, livingEntity.blockPosition(), SoundEvents.BEACON_POWER_SELECT, SoundSource.PLAYERS, 0.8f, 1f);

        int bonus = serverPlayer.getData(CAAttachments.BONUS_CHARM_SLOTS);
        if (bonus < 1) {
            serverPlayer.setData(CAAttachments.BONUS_CHARM_SLOTS, bonus + 1);
        }

        tryResyncBonusCharms(serverPlayer);

        return super.finishUsingItem(stack, level, livingEntity);
    }

    private static void tryResyncBonusCharms(Player player) {
        int bonus = player.getData(CAAttachments.BONUS_CHARM_SLOTS);
        AccessoriesCapability cap = AccessoriesCapability.get(player);

        if (cap == null) return;

        var map = HashMultimap.<String, AttributeModifier>create();
        map.put("charm", new AttributeModifier(BONUS_CHARM_MOD, bonus, AttributeModifier.Operation.ADD_VALUE));

        cap.addPersistentSlotModifiers(map);
    }

    @SubscribeEvent
    public static void playerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity().getCommandSenderWorld().isClientSide()) return;
        tryResyncBonusCharms(event.getEntity());
    }

    @SubscribeEvent
    public static void playerLogIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity().getCommandSenderWorld().isClientSide()) return;
        tryResyncBonusCharms(event.getEntity());
    }
}
