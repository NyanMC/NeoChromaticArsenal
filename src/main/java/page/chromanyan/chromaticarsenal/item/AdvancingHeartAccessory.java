package page.chromanyan.chromaticarsenal.item;

import page.chromanyan.chromaticarsenal.ChromaticArsenal;
import page.chromanyan.chromaticarsenal.init.CAItems;
import page.chromanyan.chromaticarsenal.item.base.ChromaAccessory;
import page.chromanyan.chromaticarsenal.util.AdvancementCompletionHelper;
import page.chromanyan.chromaticarsenal.util.ChromaAccessoryHelper;
import page.chromanyan.chromaticarsenal.util.TooltipHelper;
import io.wispforest.accessories.api.attributes.AccessoryAttributeBuilder;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.AdvancementEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@EventBusSubscriber
public class AdvancingHeartAccessory extends ChromaAccessory {

    public AdvancingHeartAccessory() {
        setEquipSound(SoundEvents.UI_TOAST_IN);
        enableJankAttributeFix();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("deprecation")
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, list, tooltipFlag);
        if (!Screen.hasShiftDown()) return;

        TooltipHelper.itemTooltipLine(stack, 1, list);
        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);

        int completed = 0;
        int total = 0;

        if (customData != null) {
            completed = customData.getUnsafe().getInt("completedAdvancements");
            total = customData.getUnsafe().getInt("totalAdvancements");
        }


        if (total > 0)
            TooltipHelper.itemTooltipLine(stack, "progresstracker", list, TooltipHelper.valueTooltip(completed), TooltipHelper.valueTooltip(total));
        else
            TooltipHelper.itemTooltipLine(stack, "progresstracker.equipfirst", list);
    }

    public static void updateNBTForStack(Entity entity, ItemStack stack) {
        if (!(entity instanceof ServerPlayer player)) return;

        Tuple<Integer, Integer> advancementCount = AdvancementCompletionHelper.getCompletedAndTotalAdvancements(player);
        if (advancementCount == null) return;

        CustomData.update(DataComponents.CUSTOM_DATA, stack, (tag) -> {
            tag.putInt("completedAdvancements", advancementCount.getA());
            tag.putInt("totalAdvancements", advancementCount.getB());
        });
    }

    @Override
    public void onEquip(ItemStack stack, SlotReference reference) {
        if (!ChromaAccessoryHelper.isAccessoryEquipped(reference.entity(), this)) return;
        updateNBTForStack(reference.entity(), stack);
    }

    @SuppressWarnings("deprecation")
    private static int getHealthModifier(ItemStack stack) {
        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
        if (customData == null) return 0;
        float percentage = (float) customData.getUnsafe().getInt("completedAdvancements") / customData.getUnsafe().getInt("totalAdvancements");
        if (percentage == Float.POSITIVE_INFINITY) percentage = 0; // in case the item's NBT is manually edited or a weird bug happens
        return (int) (ChromaticArsenal.CONFIG.COMMON.advancingHeartHealthModifier() * percentage / 2) * 2; // the divide and re-multiply by 2 is to ensure an even number
    }

    @Override
    public void getDynamicModifiers(ItemStack stack, SlotReference reference, AccessoryAttributeBuilder builder) {
        builder.addStackable(Attributes.MAX_HEALTH, new AttributeModifier(ChromaticArsenal.of("advancing_heart_health"), getHealthModifier(stack), AttributeModifier.Operation.ADD_VALUE));
    }

    @SubscribeEvent
    public static void advancementEarn(AdvancementEvent.AdvancementEarnEvent event) {
        Player player = event.getEntity();

        ItemStack stack = ChromaAccessoryHelper.tryGetFirstEquipped(player, CAItems.ADVANCING_HEART.get());
        if (stack == null) return;

        updateNBTForStack(player, stack);
    }
}
