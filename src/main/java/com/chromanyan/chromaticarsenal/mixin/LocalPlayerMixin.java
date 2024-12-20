package com.chromanyan.chromaticarsenal.mixin;

import com.chromanyan.chromaticarsenal.init.CAItems;
import com.chromanyan.chromaticarsenal.util.ChromaAccessoryHelper;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {
    @ModifyReturnValue(method = "hasEnoughFoodToStartSprinting", at = @At("RETURN"))
    private boolean canSprintDespiteLowHunger(boolean original) {
        if (ChromaAccessoryHelper.isAccessoryEquipped((LocalPlayer)(Object)this, CAItems.MOMENTUM_STONE.get())) {
            return true;
        }
        return original;
    }
}
