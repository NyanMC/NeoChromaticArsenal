package com.chromanyan.chromaticarsenal.mixin;

import com.chromanyan.chromaticarsenal.util.MixinHelper;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {

    @Inject(method = "clearFire", at = @At("HEAD"), cancellable = true)
    private void clearFire(CallbackInfo ci) {
        if (MixinHelper.isInferno((Entity) (Object) this)) ci.cancel();
    }

    @Inject(method = "playEntityOnFireExtinguishedSound", at = @At("HEAD"), cancellable = true)
    private void playEntityOnFireExtinguishedSound(CallbackInfo ci) {
        if (MixinHelper.isInferno((Entity) (Object) this)) ci.cancel();
    }

    @Inject(method = "setRemainingFireTicks", at = @At("HEAD"), cancellable = true)
    private void setRemainingFireTicks(int newTime, CallbackInfo ci) {
        Entity entity = (Entity) (Object) this;

        if (entity.getRemainingFireTicks() > newTime) {
            if (MixinHelper.isInferno(entity)) ci.cancel();
        }
    }
}
