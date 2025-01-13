package com.chromanyan.chromaticarsenal.mixin;

import com.chromanyan.chromaticarsenal.ChromaticArsenal;
import com.chromanyan.chromaticarsenal.init.CAItems;
import com.chromanyan.chromaticarsenal.util.ChromaAccessoryHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Inject(method = "hurtAndBreak(ILnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/LivingEntity;Ljava/util/function/Consumer;)V", at = @At("HEAD"), cancellable = true)
    private void hurtAndBreak(int p_220158_, ServerLevel p_346256_, LivingEntity p_220160_, Consumer<Item> p_348596_, CallbackInfo ci) {
        if (p_220160_ == null) return;
        if (!ChromaAccessoryHelper.isAccessoryEquipped(p_220160_, CAItems.COPPER_RING.get())) return;

        if (p_220160_.getRandom().nextDouble() <= ChromaticArsenal.CONFIG.COMMON.copperRingUnbreakingChance()) {
            ci.cancel();
        }
    }
}
