package page.chromanyan.chromaticarsenal.item.utilityaccessories;

import page.chromanyan.chromaticarsenal.item.base.ChromaAccessory;
import page.chromanyan.chromaticarsenal.util.TooltipHelper;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class VerticalStasisAccessory extends ChromaAccessory {

    public static final String ACTIVE = "chromaticarsenal.verticalstasisactive";

    public VerticalStasisAccessory() {
        super(Rarity.UNCOMMON);
        setEquipSound(SoundEvents.SHULKER_BULLET_HIT);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, @NotNull Item.TooltipContext context, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        TooltipHelper.itemTooltipLine(stack, 1, list);
        TooltipHelper.itemTooltipLine(stack, 2, list);
    }

    public static boolean isVerticalStasisActive(Entity entity) {
        return entity.getPersistentData().getBoolean(ACTIVE);
    }

    private static void setVerticalStasisActive(Entity entity) {
        entity.getPersistentData().putBoolean(ACTIVE, entity.isDiscrete() && (entity.onGround() || isVerticalStasisActive(entity)));
    }

    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        LivingEntity entity = reference.entity();

        setVerticalStasisActive(entity);
        entity.setNoGravity(isVerticalStasisActive(entity));
        if (!entity.getCommandSenderWorld().isClientSide() || !isVerticalStasisActive(entity)) return;

        Vec3 deltaMovement = entity.getDeltaMovement();
        entity.setDeltaMovement(deltaMovement.x, 0, deltaMovement.z); // zero out their Y momentum completely, prevents a lot of cheesing
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference reference) {
        LivingEntity entity = reference.entity();
        if (entity.getCommandSenderWorld().isClientSide()) return;
        entity.getPersistentData().putBoolean("active", false);
        entity.setNoGravity(false);
    }
}
