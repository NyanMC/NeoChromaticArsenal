package page.chromanyan.chromaticarsenal.util;

import page.chromanyan.chromaticarsenal.init.CAEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class MixinHelper {

    private MixinHelper() {}

    public static boolean isInferno(Entity entity) {
        if (!(entity instanceof LivingEntity livingEntity)) {
            return false;
        }

        return livingEntity.hasEffect(CAEffects.INFERNO);
    }
}
