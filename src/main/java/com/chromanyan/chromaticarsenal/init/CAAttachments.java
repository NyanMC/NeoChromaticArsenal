package com.chromanyan.chromaticarsenal.init;

import com.chromanyan.chromaticarsenal.ChromaticArsenal;
import com.mojang.serialization.Codec;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class CAAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, ChromaticArsenal.MODID);

    public static final Supplier<AttachmentType<Integer>> BONUS_CHARM_SLOTS = ATTACHMENT_TYPES.register(
            "bonus_charm_slots", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).copyOnDeath().build()
    );
}
