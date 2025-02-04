package page.chromanyan.chromaticarsenal.util;

import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.world.entity.LivingEntity;
import page.chromanyan.chromaticarsenal.ChromaticArsenal;
import page.chromanyan.chromaticarsenal.init.CAItems;

public class ClientMixinHelper {
    // these are in a separate class to make sure the server doesn't try to load classes that don't exist on the server
    public static final PlayerSkin ANON_SKIN = new PlayerSkin(ChromaticArsenal.of("textures/entity/anonymous.png"), null, null, null, PlayerSkin.Model.WIDE, true);

    public static boolean shouldCloak(LivingEntity entity) {
        if (ChromaticArsenal.CONFIG.CLIENT.anonymityOptOut()) return false;
        return ChromaAccessoryHelper.isAccessoryEquipped(entity, CAItems.ANONYMITY_UMBRELLA.get());
    }
}
