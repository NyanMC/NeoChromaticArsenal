package com.chromanyan.chromaticarsenal.init;

import com.chromanyan.chromaticarsenal.ChromaticArsenal;
import com.chromanyan.chromaticarsenal.item.*;
import com.chromanyan.chromaticarsenal.item.challengeaccessories.AscendedStarAccessory;
import com.chromanyan.chromaticarsenal.item.challengeaccessories.CursedTotemAccessory;
import com.chromanyan.chromaticarsenal.item.challengeaccessories.WorldAnchorAccessory;
import com.chromanyan.chromaticarsenal.item.misc.HarpyFeatherItem;
import com.chromanyan.chromaticarsenal.item.misc.MagicGarlicBreadItem;
import com.chromanyan.chromaticarsenal.item.superaccessories.*;
import com.chromanyan.chromaticarsenal.item.utilityaccessories.AnonymityUmbrellaAccessory;
import com.chromanyan.chromaticarsenal.item.utilityaccessories.GravityStoneAccessory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CAItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ChromaticArsenal.MODID);

    public static final DeferredItem<Item> CHROMA_SHARD = ITEMS.registerSimpleItem("chroma_shard", new Item.Properties().rarity(Rarity.UNCOMMON));
    public static final DeferredItem<Item> ASCENSION_ESSENCE = ITEMS.registerSimpleItem("ascension_essence", new Item.Properties().rarity(Rarity.EPIC));
    public static final DeferredItem<BlockItem> CHROMA_BLOCK = ITEMS.registerSimpleBlockItem(CABlocks.CHROMA_BLOCK, new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredItem<MagicGarlicBreadItem> MAGIC_GARLIC_BREAD = ITEMS.register("magic_garlic_bread", MagicGarlicBreadItem::new);
    public static final DeferredItem<HarpyFeatherItem> HARPY_FEATHER = ITEMS.register("harpy_feather", () -> new HarpyFeatherItem(Rarity.UNCOMMON, ChromaticArsenal.CONFIG.COMMON.harpyFeatherMaxJumps()));
    public static final DeferredItem<HarpyFeatherItem> POLYCHROMATIC_FEATHER = ITEMS.register("super_harpy_feather", () -> new HarpyFeatherItem(Rarity.EPIC, ChromaticArsenal.CONFIG.COMMON.superHarpyFeatherMaxJumps()));

    public static final DeferredItem<GoldenHeartAccessory> GOLDEN_HEART = ITEMS.register("golden_heart", GoldenHeartAccessory::new);
    public static final DeferredItem<GlassShieldAccessory> GLASS_SHIELD = ITEMS.register("glass_shield", GlassShieldAccessory::new);
    public static final DeferredItem<WardCrystalAccessory> WARD_CRYSTAL = ITEMS.register("ward_crystal", WardCrystalAccessory::new);
    public static final DeferredItem<ShadowTreadsAccessory> SHADOW_TREADS = ITEMS.register("shadow_treads", ShadowTreadsAccessory::new);
    public static final DeferredItem<DualityRingsAccessory> DUALITY_RINGS = ITEMS.register("duality_rings", DualityRingsAccessory::new);
    public static final DeferredItem<FriendlyFireFlowerAccessory> FRIENDLY_FIRE_FLOWER = ITEMS.register("friendly_fire_flower", FriendlyFireFlowerAccessory::new);
    public static final DeferredItem<LunarCrystalAccessory> LUNAR_CRYSTAL = ITEMS.register("lunar_crystal", LunarCrystalAccessory::new);
    public static final DeferredItem<CryoRingAccessory> CRYO_RING = ITEMS.register("cryo_ring", CryoRingAccessory::new);
    public static final DeferredItem<BubbleAmuletAccessory> BUBBLE_AMULET = ITEMS.register("bubble_amulet", BubbleAmuletAccessory::new);
    public static final DeferredItem<MomentumStoneAccessory> MOMENTUM_STONE = ITEMS.register("momentum_stone", MomentumStoneAccessory::new);
    public static final DeferredItem<AdvancingHeartAccessory> ADVANCING_HEART = ITEMS.register("advancing_heart", AdvancingHeartAccessory::new);
    public static final DeferredItem<ThunderguardAccessory> THUNDERGUARD = ITEMS.register("thunderguard", ThunderguardAccessory::new);

    public static final DeferredItem<DiamondHeartAccessory> DIAMOND_HEART = ITEMS.register("super_golden_heart", DiamondHeartAccessory::new);
    public static final DeferredItem<UndyingShieldAccessory> UNDYING_SHIELD = ITEMS.register("super_glass_shield", UndyingShieldAccessory::new);
    public static final DeferredItem<DispellingCrystalAccessory> DISPELLING_CRYSTAL = ITEMS.register("super_ward_crystal", DispellingCrystalAccessory::new);
    public static final DeferredItem<CelestialCharmAccessory> CELESTIAL_CHARM = ITEMS.register("super_shadow_treads", CelestialCharmAccessory::new);
    public static final DeferredItem<InfernoFlowerAccessory> INFERNO_FLOWER = ITEMS.register("super_friendly_fire_flower", InfernoFlowerAccessory::new);
    public static final DeferredItem<PrismaticCrystalAccessory> PRISMATIC_CRYSTAL = ITEMS.register("super_lunar_crystal", PrismaticCrystalAccessory::new);

    public static final DeferredItem<AscendedStarAccessory> ASCENDED_STAR = ITEMS.register("ascended_star", AscendedStarAccessory::new);
    public static final DeferredItem<WorldAnchorAccessory> WORLD_ANCHOR = ITEMS.register("world_anchor", WorldAnchorAccessory::new);
    public static final DeferredItem<CursedTotemAccessory> CURSED_TOTEM = ITEMS.register("cursed_totem", CursedTotemAccessory::new);

    public static final DeferredItem<GravityStoneAccessory> GRAVITY_STONE = ITEMS.register("gravity_stone", GravityStoneAccessory::new);
    public static final DeferredItem<AnonymityUmbrellaAccessory> ANONYMITY_UMBRELLA = ITEMS.register("anonymity_umbrella", AnonymityUmbrellaAccessory::new);
}
