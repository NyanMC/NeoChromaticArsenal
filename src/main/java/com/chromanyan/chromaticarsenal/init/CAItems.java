package com.chromanyan.chromaticarsenal.init;

import com.chromanyan.chromaticarsenal.ChromaticArsenal;
import com.chromanyan.chromaticarsenal.item.*;
import com.chromanyan.chromaticarsenal.item.food.MagicGarlicBreadItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CAItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ChromaticArsenal.MODID);

    public static final DeferredItem<Item> CHROMA_SHARD = ITEMS.registerSimpleItem("chroma_shard", new Item.Properties().rarity(Rarity.UNCOMMON));
    public static final DeferredItem<MagicGarlicBreadItem> MAGIC_GARLIC_BREAD = ITEMS.register("magic_garlic_bread", MagicGarlicBreadItem::new);

    public static final DeferredItem<GoldenHeartAccessory> GOLDEN_HEART = ITEMS.register("golden_heart", GoldenHeartAccessory::new);
    public static final DeferredItem<GlassShieldAccessory> GLASS_SHIELD = ITEMS.register("glass_shield", GlassShieldAccessory::new);
    public static final DeferredItem<WardCrystalAccessory> WARD_CRYSTAL = ITEMS.register("ward_crystal", WardCrystalAccessory::new);
    public static final DeferredItem<ShadowTreadsAccessory> SHADOW_TREADS = ITEMS.register("shadow_treads", ShadowTreadsAccessory::new);
    public static final DeferredItem<DualityRingsAccessory> DUALITY_RINGS = ITEMS.register("duality_rings", DualityRingsAccessory::new);
    public static final DeferredItem<FriendlyFireFlowerAccessory> FRIENDLY_FIRE_FLOWER = ITEMS.register("friendly_fire_flower", FriendlyFireFlowerAccessory::new);
}
