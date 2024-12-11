package com.chromanyan.chromaticarsenal.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class CATags {
    public static class Items {
        private static TagKey<Item> forgeTag(String name) {
            return ItemTags.create(ResourceLocation.parse("gems/chroma"));
        }

        public static final TagKey<Item> GEMS_CHROMA = forgeTag("gems/chroma");
    }
}
