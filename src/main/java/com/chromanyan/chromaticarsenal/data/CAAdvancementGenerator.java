package com.chromanyan.chromaticarsenal.data;

import com.chromanyan.chromaticarsenal.ChromaticArsenal;
import com.chromanyan.chromaticarsenal.init.CABlocks;
import com.chromanyan.chromaticarsenal.init.CAItems;
import com.chromanyan.chromaticarsenal.triggers.GlassShieldBlockTrigger;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class CAAdvancementGenerator implements AdvancementProvider.AdvancementGenerator {

    private static final ItemLike[] ACCESSORIES = new ItemLike[] {
        CAItems.GOLDEN_HEART, CAItems.GLASS_SHIELD, CAItems.WARD_CRYSTAL, CAItems.SHADOW_TREADS, CAItems.DUALITY_RINGS, CAItems.FRIENDLY_FIRE_FLOWER,
            CAItems.LUNAR_CRYSTAL, CAItems.CRYO_RING, CAItems.BUBBLE_AMULET, CAItems.MOMENTUM_STONE, CAItems.ADVANCING_HEART, CAItems.THUNDERGUARD,
            CAItems.DIAMOND_HEART, CAItems.UNDYING_SHIELD, CAItems.DISPELLING_CRYSTAL, CAItems.CELESTIAL_CHARM, CAItems.INFERNO_FLOWER, CAItems.PRISMATIC_CRYSTAL, CAItems.ILLUMINATED_SOUL,
            CAItems.ASCENDED_STAR, CAItems.WORLD_ANCHOR, CAItems.CURSED_TOTEM, CAItems.GRAVITY_STONE, CAItems.VERTICAL_STASIS, CAItems.BLAHAJ, CAItems.ANONYMITY_UMBRELLA,
            CAItems.COPPER_RING, CAItems.AMETHYST_RING, CAItems.VITAL_STONE
    };

    private Advancement.Builder displayedHasItemBase(Item item, AdvancementType type) {
        return Advancement.Builder.advancement().addCriterion("has_item", hasItem(item)).display(
                item,
                Component.translatable("advancement.chromaticarsenal." + ResourceLocation.parse(item.toString()).getPath() + ".title"),
                Component.translatable("advancement.chromaticarsenal." + ResourceLocation.parse(item.toString()).getPath() + ".description"),
                null,
                type,
                true,
                true,
                false
        );
    }

    @SuppressWarnings("removal")
    private AdvancementHolder displayedHasItem(Item item, @NotNull Consumer<AdvancementHolder> saver, @NotNull ExistingFileHelper existingFileHelper, AdvancementType type, ResourceLocation parent) {
        return displayedHasItemBase(item, type)
                .parent(parent)
                .save(saver, ResourceLocation.parse(item.toString()), existingFileHelper);
    }

    private AdvancementHolder displayedHasItem(Item item, @NotNull Consumer<AdvancementHolder> saver, @NotNull ExistingFileHelper existingFileHelper, AdvancementType type, AdvancementHolder parent) {
        return displayedHasItemBase(item, type)
                .parent(parent)
                .save(saver, ResourceLocation.parse(item.asItem().toString()), existingFileHelper);
    }

    @SuppressWarnings("unused")
    @Override
    public void generate(HolderLookup.@NotNull Provider registries, @NotNull Consumer<AdvancementHolder> saver, @NotNull ExistingFileHelper existingFileHelper) {
        AdvancementHolder chromaShard = displayedHasItem(CAItems.CHROMA_SHARD.get(), saver, existingFileHelper, AdvancementType.TASK, ResourceLocation.withDefaultNamespace("adventure/root"));
        AdvancementHolder ascensionEssence = displayedHasItem(CAItems.ASCENSION_ESSENCE.get(), saver, existingFileHelper, AdvancementType.GOAL, ResourceLocation.withDefaultNamespace("adventure/revaulting"));
        AdvancementHolder goldenHeart = displayedHasItem(CAItems.GOLDEN_HEART.get(), saver, existingFileHelper, AdvancementType.TASK, ResourceLocation.withDefaultNamespace("nether/loot_bastion"));
        AdvancementHolder lunarCrystal = displayedHasItem(CAItems.LUNAR_CRYSTAL.get(), saver, existingFileHelper, AdvancementType.GOAL, ResourceLocation.withDefaultNamespace("end/find_end_city"));
        AdvancementHolder cryoRing = displayedHasItem(CAItems.CRYO_RING.get(), saver, existingFileHelper, AdvancementType.TASK, ResourceLocation.withDefaultNamespace("adventure/walk_on_powder_snow_with_leather_boots"));
        AdvancementHolder bubbleAmulet = displayedHasItem(CAItems.BUBBLE_AMULET.get(), saver, existingFileHelper, AdvancementType.TASK, ResourceLocation.withDefaultNamespace("adventure/salvage_sherd"));
        AdvancementHolder momentumStone = displayedHasItem(CAItems.MOMENTUM_STONE.get(), saver, existingFileHelper, AdvancementType.TASK, ResourceLocation.withDefaultNamespace("adventure/trade"));
        AdvancementHolder advancingHeart = displayedHasItem(CAItems.ADVANCING_HEART.get(), saver, existingFileHelper, AdvancementType.TASK, goldenHeart);
        AdvancementHolder thunderguard = displayedHasItem(CAItems.THUNDERGUARD.get(), saver, existingFileHelper, AdvancementType.GOAL, ResourceLocation.withDefaultNamespace("adventure/lightning_rod_with_villager_no_fire"));
        AdvancementHolder ascendedStar = displayedHasItem(CAItems.ASCENDED_STAR.get(), saver, existingFileHelper, AdvancementType.GOAL, ascensionEssence);
        AdvancementHolder blahaj = displayedHasItem(CAItems.BLAHAJ.get(), saver, existingFileHelper, AdvancementType.TASK, ResourceLocation.withDefaultNamespace("adventure/trade"));

        AdvancementHolder feedChromanyanPlush = Advancement.Builder.advancement()
                .addCriterion("feed_chromanyan_plush", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
                        LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(CABlocks.CHROMANYAN.get())),
                        ItemPredicate.Builder.item().of(CAItems.CHROMA_SHARD)
                ))
                .display(
                        CAItems.CHROMANYAN,
                        Component.translatable("advancement.chromaticarsenal.feed_chromanyan_plush.title"),
                        Component.translatable("advancement.chromaticarsenal.feed_chromanyan_plush.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        true
                )
                .parent(chromaShard)
                .save(saver, ChromaticArsenal.of("feed_chromanyan_plush"), existingFileHelper);

        AdvancementHolder cosmicola = Advancement.Builder.advancement()
                .addCriterion("cosmicola_consumed", ConsumeItemTrigger.TriggerInstance.usedItem(CAItems.COSMICOLA))
                .display(
                        CAItems.COSMICOLA,
                        Component.translatable("advancement.chromaticarsenal.cosmicola.title"),
                        Component.translatable("advancement.chromaticarsenal.cosmicola.description"),
                        null,
                        AdvancementType.GOAL,
                        true,
                        true,
                        false
                )
                .parent(ascensionEssence)
                .save(saver, ChromaticArsenal.of("cosmicola"), existingFileHelper);

        AdvancementHolder arsenalAccumulated = addAccessories(Advancement.Builder.advancement())
                .display(
                        CAItems.ASCENDED_STAR,
                        Component.translatable("advancement.chromaticarsenal.arsenal_accumulated.title"),
                        Component.translatable("advancement.chromaticarsenal.arsenal_accumulated.description"),
                        null,
                        AdvancementType.CHALLENGE,
                        true,
                        true,
                        false
                )
                .rewards(AdvancementRewards.Builder.experience(100))
                .parent(ascendedStar)
                .save(saver, ChromaticArsenal.of("arsenal_accumulated"), existingFileHelper);

        AdvancementHolder blockOneHundred = Advancement.Builder.advancement()
                .addCriterion("blocked_100", GlassShieldBlockTrigger.TriggerInstance.blockedAboveDamage(100))
                .display(
                        CAItems.GLASS_SHIELD,
                        Component.translatable("advancement.chromaticarsenal.block_100.title"),
                        Component.translatable("advancement.chromaticarsenal.block_100.description"),
                        null,
                        AdvancementType.CHALLENGE,
                        true,
                        true,
                        false
                )
                .rewards(AdvancementRewards.Builder.experience(100))
                .parent(chromaShard)
                .save(saver, ChromaticArsenal.of("block_100"), existingFileHelper);
    }

    private Advancement.Builder addAccessories(Advancement.Builder advancement) {
        for (ItemLike item : ACCESSORIES) {
            advancement.addCriterion("has_" + item, hasItem(item));
        }
        return advancement;
    }

    private Criterion<?> hasItem(ItemLike item) {
        return InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(item).build());
    }
}
