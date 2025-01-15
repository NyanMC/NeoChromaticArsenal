package page.chromanyan.chromaticarsenal.init;

import page.chromanyan.chromaticarsenal.ChromaticArsenal;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public class CADamageTypes {
    public static final ResourceKey<DamageType> DEATH_CLOCK = register("death_clock");
    public static final ResourceKey<DamageType> ASCENDED = register("ascended");

    private static ResourceKey<DamageType> register(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, ChromaticArsenal.of(name));
    }
}
