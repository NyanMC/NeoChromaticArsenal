package page.chromanyan.chromaticarsenal.util;

import page.chromanyan.chromaticarsenal.ChromaticArsenal;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;

import java.util.List;

public class ConfigHelper {

    private ConfigHelper() {

    }

    public static boolean effectInBlacklist(List<? extends String> stringList, MobEffect mobEffect) {
        if (stringList.isEmpty()) return false;

        for (String blacklisted : stringList) {
            ResourceLocation blacklistedRL = ResourceLocation.tryParse(blacklisted);

            if (blacklistedRL == null) {
                ChromaticArsenal.LOGGER.error("CONFIG PARSE ERROR: Failed to parse \"{}\" as ResourceLocation, skipping", blacklisted);
                continue;
            }

            MobEffect effect = BuiltInRegistries.MOB_EFFECT.get(blacklistedRL);

            if (effect == null) {
                ChromaticArsenal.LOGGER.error("CONFIG PARSE ERROR: The resource location \"{}\" was not recognized as a potion effect, skipping", blacklisted);
                continue;
            }

            if (mobEffect == effect) {
                return true;
            }
        }

        return false;
    }
}
