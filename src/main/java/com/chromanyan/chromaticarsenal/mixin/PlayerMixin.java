package com.chromanyan.chromaticarsenal.mixin;

import com.chromanyan.chromaticarsenal.init.CAItems;
import com.chromanyan.chromaticarsenal.util.ChromaAccessoryHelper;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public class PlayerMixin {

    @WrapOperation(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;setSprinting(Z)V"))
    private void maybeDontStopSprinting(Player instance, boolean b, Operation<Void> original) {
        if (!ChromaAccessoryHelper.isAccessoryEquipped((Player)(Object)this, CAItems.MOMENTUM_STONE.get())) {
            original.call(instance, b);
        }
    }

}
