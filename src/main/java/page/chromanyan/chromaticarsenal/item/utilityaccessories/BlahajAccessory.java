package page.chromanyan.chromaticarsenal.item.utilityaccessories;

import page.chromanyan.chromaticarsenal.init.CABlocks;
import page.chromanyan.chromaticarsenal.init.CAItems;
import page.chromanyan.chromaticarsenal.util.ChromaAccessoryHelper;
import page.chromanyan.chromaticarsenal.util.TooltipHelper;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.SoundEventData;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.player.CanPlayerSleepEvent;
import net.neoforged.neoforge.event.entity.player.PlayerSpawnPhantomsEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@EventBusSubscriber
public class BlahajAccessory extends BlockItem implements Accessory {

    public BlahajAccessory() {
        super(CABlocks.BLAHAJ.get(), new Item.Properties()
                .stacksTo(1)
                .rarity(Rarity.UNCOMMON));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        TooltipHelper.itemTooltipLine("can_place", list);
        if (!Screen.hasShiftDown()) {
            TooltipHelper.itemTooltipLine("shift", list);
            return;
        }
        TooltipHelper.itemTooltipLine(stack, 1, list);
        TooltipHelper.itemTooltipLine(stack, 2, list);
    }

    @SubscribeEvent
    public static void playerSpawnPhantoms(PlayerSpawnPhantomsEvent event) {
        if (!ChromaAccessoryHelper.isAccessoryEquipped(event.getEntity(), CAItems.BLAHAJ.get())) return;

        event.setResult(PlayerSpawnPhantomsEvent.Result.DENY);
    }

    @SubscribeEvent
    public static void livingVisibility(LivingEvent.LivingVisibilityEvent event) {
        if (!ChromaAccessoryHelper.isAccessoryEquipped(event.getEntity(), CAItems.BLAHAJ.get())) return;

        if (event.getLookingEntity() instanceof Phantom) {
            event.modifyVisibility(0);
        }
    }

    // thanks neoforge for adding an event for this
    @SubscribeEvent
    public static void canPlayerSleep(CanPlayerSleepEvent event) {
        if (!(event.getProblem() == Player.BedSleepingProblem.NOT_SAFE)) return;
        if (!ChromaAccessoryHelper.isAccessoryEquipped(event.getEntity(), CAItems.BLAHAJ.get())) return;

        event.setProblem(null);
    }

    @Override
    public @Nullable SoundEventData getEquipSound(ItemStack stack, SlotReference reference) {
        return new SoundEventData(Holder.direct(SoundEvents.WOOL_PLACE), 0.5f, 1);
    }

    @Override
    public boolean canEquip(ItemStack stack, SlotReference reference) {
        AccessoriesCapability cap = AccessoriesCapability.get(reference.entity());
        if (cap == null) return false;

        return !cap.isAnotherEquipped(stack, reference, this);
    }
}
