package com.chromanyan.chromaticarsenal.mixin;

import com.chromanyan.chromaticarsenal.ChromaticArsenal;
import com.chromanyan.chromaticarsenal.init.CAItems;
import com.chromanyan.chromaticarsenal.item.base.ChromaAccessory;
import com.chromanyan.chromaticarsenal.util.ChromaAccessoryHelper;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.slot.SlotEntryReference;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Unique
    private static final float BLUE_ICE_FRICTION = 0.989F; // the most slippery block in vanilla

    @ModifyReturnValue(method = "canFreeze", at = @At("RETURN"))
    private boolean canFreeze(boolean original) {
        if (!original) return false;
        LivingEntity trueThis = (LivingEntity)(Object)this;

        AccessoriesCapability cap = AccessoriesCapability.get(trueThis);
        if (cap == null) return original;

        for (SlotEntryReference ref : cap.getAllEquipped()) {
            // only affect ourselves
            if (!(ref.stack().getItem() instanceof ChromaAccessory)) continue;
            if (ref.stack().is(ItemTags.FREEZE_IMMUNE_WEARABLES)) return false;
        }

        return original;
    }

    @ModifyExpressionValue(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getFriction(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/Entity;)F"))
    private float getFriction(float original) {
        LivingEntity trueThis = (LivingEntity)(Object) this;

        if (original > BLUE_ICE_FRICTION && !ChromaticArsenal.CONFIG.COMMON.momentumStoneExtremelyUnbalancedMode()) return original;

        // don't even try if the momentum stone isn't equipped
        if (!ChromaAccessoryHelper.isAccessoryEquipped(trueThis, CAItems.MOMENTUM_STONE.get())) return original;
        if (trueThis.getBlockSpeedFactor() > 1 && !ChromaticArsenal.CONFIG.COMMON.momentumStoneExtremelyUnbalancedMode()) return original; // high block speed factors are buggy

        float newFriction = (float) (original + ChromaticArsenal.CONFIG.COMMON.momentumStoneFrictionAddition());

        if (ChromaticArsenal.CONFIG.COMMON.momentumStoneExtremelyUnbalancedMode()) return newFriction;

        return Math.min(newFriction, BLUE_ICE_FRICTION); // high levels of friction are buggy
    }
}
