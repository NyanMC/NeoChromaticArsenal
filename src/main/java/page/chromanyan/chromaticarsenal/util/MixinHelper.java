package page.chromanyan.chromaticarsenal.util;

import page.chromanyan.chromaticarsenal.ChromaticArsenal;
import page.chromanyan.chromaticarsenal.init.CAEffects;
import page.chromanyan.chromaticarsenal.init.CAItems;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class MixinHelper {

    public static final PlayerSkin ANON_SKIN = new PlayerSkin(ChromaticArsenal.of("textures/entity/anonymous.png"), null, null, null, PlayerSkin.Model.WIDE, true);

    private MixinHelper() {}

    public static boolean isInferno(Entity entity) {
        if (!(entity instanceof LivingEntity livingEntity)) {
            return false;
        }

        return livingEntity.hasEffect(CAEffects.INFERNO);
    }

    public static boolean shouldCloak(LivingEntity entity) {
        if (ChromaticArsenal.CONFIG.CLIENT.anonymityOptOut()) return false;
        return ChromaAccessoryHelper.isAccessoryEquipped(entity, CAItems.ANONYMITY_UMBRELLA.get());
    }
}
