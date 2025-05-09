package page.chromanyan.chromaticarsenal.mixin;

import page.chromanyan.chromaticarsenal.util.ClientMixinHelper;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.resources.PlayerSkin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractClientPlayer.class)
public class AbstractClientPlayerMixin {

    // if the curio is equipped, replace their skin with our anonymous one
    @ModifyReturnValue(method = "getSkin", at = @At("RETURN"))
    private PlayerSkin getSkin(PlayerSkin original) {
        if (ClientMixinHelper.shouldCloak((AbstractClientPlayer)(Object) this)) {
            return ClientMixinHelper.ANON_SKIN;
        }
        return original;
    }

    // effectively, this will mean the player won't be associated with their UUID for *anything* regarding rendering
    @ModifyReturnValue(method = "getPlayerInfo", at = @At("RETURN"))
    private PlayerInfo getPlayerInfo(PlayerInfo original) {
        if (ClientMixinHelper.shouldCloak((AbstractClientPlayer)(Object) this)) {
            return null;
        }
        return original;
    }
}
