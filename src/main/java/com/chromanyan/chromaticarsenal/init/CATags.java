package com.chromanyan.chromaticarsenal.init;

import com.chromanyan.chromaticarsenal.ChromaticArsenal;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

public class CATags {
    public static class Items {
        private static TagKey<Item> commonTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", name));
        }

        public static final TagKey<Item> GEMS_CHROMA = commonTag("gems/chroma");
        public static final TagKey<Item> DUSTS_ASCENSION = commonTag("dusts/ascension");
    }

    public static class DamageTypes {
        public static final TagKey<DamageType> IMMUNE_TO_WARD_CRYSTAL = TagKey.create(Registries.DAMAGE_TYPE, ChromaticArsenal.of("immune_to_ward_crystal"));
    }

    public static class EntityTypes {
        public static final TagKey<EntityType<?>> IMMUNE_TO_CURSED_REVIVAL = TagKey.create(Registries.ENTITY_TYPE, ChromaticArsenal.of("immune_to_cursed_revival"));
    }
}
