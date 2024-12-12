package com.chromanyan.chromaticarsenal.init;

import com.chromanyan.chromaticarsenal.ChromaticArsenal;
import com.chromanyan.chromaticarsenal.item.GlassShieldAccessory;
import com.chromanyan.chromaticarsenal.item.GoldenHeartAccessory;
import com.chromanyan.chromaticarsenal.item.WardCrystalAccessory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CAItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ChromaticArsenal.MODID);

    public static final DeferredItem<Item> CHROMA_SHARD = ITEMS.registerSimpleItem("chroma_shard", new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredItem<GoldenHeartAccessory> GOLDEN_HEART = ITEMS.register("golden_heart", GoldenHeartAccessory::new);
    public static final DeferredItem<GlassShieldAccessory> GLASS_SHIELD = ITEMS.register("glass_shield", GlassShieldAccessory::new);
    public static final DeferredItem<WardCrystalAccessory> WARD_CRYSTAL = ITEMS.register("ward_crystal", WardCrystalAccessory::new);
}
