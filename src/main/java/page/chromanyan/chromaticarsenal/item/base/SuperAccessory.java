package page.chromanyan.chromaticarsenal.item.base;

import page.chromanyan.chromaticarsenal.util.ChromaAccessoryHelper;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SuperAccessory extends ChromaAccessory {

    private ArrayList<DeferredItem<? extends Item>> incompatibilities = new ArrayList<>();

    public SuperAccessory() {
        super(Rarity.EPIC);
    }

    public SuperAccessory(Properties properties) {
        super(properties);
    }

    public void setIncompatibilities(ArrayList<DeferredItem<? extends Item>> incompatibilities) {
        this.incompatibilities = incompatibilities;
    }

    public void setIncompatibility(DeferredItem<? extends Item> incompatibility) {
        this.setIncompatibilities(new ArrayList<>(List.of(incompatibility)));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        StringBuilder incompatibilityString = new StringBuilder();

        if (incompatibilities.isEmpty()) {
            super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
            return;
        }

        for (int i = 0; i < incompatibilities.size(); i++) {
            Item item = incompatibilities.get(i).get();
            incompatibilityString.append(item.getName(new ItemStack(item)).getString());
            if (i < incompatibilities.size() - 1) {
                incompatibilityString.append(", ");
            }
        }
        tooltipComponents.add(Component.translatable("tooltip.chromaticarsenal.incompatibilities", incompatibilityString.toString()).withStyle(ChatFormatting.RED));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        super.tick(stack, reference);
        LivingEntity livingEntity = reference.entity();
        if (livingEntity.getCommandSenderWorld().isClientSide) return;

        for (DeferredItem<? extends Item> incompatibility : incompatibilities) {
            ItemStack possiblestack = ChromaAccessoryHelper.tryGetFirstEquipped(livingEntity, incompatibility.get());
            if (possiblestack == null) return;

            if (livingEntity instanceof Player player) {
                player.drop(possiblestack.copy(), true);
                possiblestack.setCount(0);
            }
        }

    }
}
