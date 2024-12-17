package com.chromanyan.chromaticarsenal.mixin;

import com.chromanyan.chromaticarsenal.item.base.ChromaAccessory;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.slot.SlotEntryReference;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

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
}
