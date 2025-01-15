package page.chromanyan.chromaticarsenal.init;

import page.chromanyan.chromaticarsenal.ChromaticArsenal;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;
import page.chromanyan.chromaticarsenal.triggers.GlassShieldBlockTrigger;

import java.util.function.Supplier;

public class CATriggers {
    public static final DeferredRegister<CriterionTrigger<?>> TRIGGERS = DeferredRegister.create(Registries.TRIGGER_TYPE, ChromaticArsenal.MODID);

    public static final Supplier<GlassShieldBlockTrigger> GLASS_SHIELD_BLOCK = TRIGGERS.register("glass_shield_block", GlassShieldBlockTrigger::new);
}
