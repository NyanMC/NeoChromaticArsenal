package page.chromanyan.chromaticarsenal.item.misc;

import page.chromanyan.chromaticarsenal.util.TooltipHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HarpyFeatherItem extends Item {

    private final int maxJumps;

    public HarpyFeatherItem(Rarity rarity, int maxJumps) {
        super(new Item.Properties()
                .stacksTo(1)
                .rarity(rarity));
        this.maxJumps = maxJumps;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull Item.TooltipContext context, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        TooltipHelper.itemTooltipLine("harpy_feather", 1, list);
        if (maxJumps > 1) {
            TooltipHelper.itemTooltipLine("harpy_feather", 2, list, TooltipHelper.valueTooltip(maxJumps));
        }
        TooltipHelper.itemTooltipLine("harpy_feather", 3, list);
    }

    private boolean areJumpsCapped(CompoundTag data) {
        return data.getInt("jumps") >= maxJumps;
    }

    private void resetJumps(CompoundTag data) {
        data.putInt("jumps", 0);
    }

    private void incrementJumps(CompoundTag data) {
        data.putInt("jumps", data.getInt("jumps") + 1);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int itemSlot, boolean isSelected) {
        if (level.isClientSide()) {
            return;
        }
        if (!(entity instanceof Player player)) return;

        // there are probably better ways to do this
        CompoundTag data = player.getPersistentData();
        if (areJumpsCapped(data)) {
            if (player.onGround() || (player.getVehicle() != null && player.getVehicle().onGround())) {
                resetJumps(data);
            } else {
                player.getCooldowns().addCooldown(this, 60);
                return;
            }
        }
        player.getCooldowns().removeCooldown(this);
    }

    private static void doJump(@NotNull Level level, LivingEntity entity1, @Nullable Entity entity2) {
        if (!level.isClientSide()) {
            entity1.resetFallDistance();
        }
        if (entity2 != null) {
            entity2.resetFallDistance();
            Vec3 vec3 = entity2.getDeltaMovement();
            entity2.setDeltaMovement(vec3.x, entity1.getAttributeValue(Attributes.JUMP_STRENGTH), vec3.z);
        } else {
            Vec3 vec3 = entity1.getDeltaMovement();
            entity1.setDeltaMovement(vec3.x, entity1.getAttributeValue(Attributes.JUMP_STRENGTH), vec3.z);
        }
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (player.isDiscrete())
            return InteractionResultHolder.pass(itemstack);

        doJump(level, player, player.getVehicle());
        incrementJumps(player.getPersistentData());
        player.getCommandSenderWorld().playSound(player, player.blockPosition(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 0.8f, 5f);

        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}
