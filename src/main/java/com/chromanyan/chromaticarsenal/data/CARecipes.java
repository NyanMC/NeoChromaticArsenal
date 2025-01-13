package com.chromanyan.chromaticarsenal.data;

import com.chromanyan.chromaticarsenal.ChromaticArsenal;
import com.chromanyan.chromaticarsenal.init.CAItems;
import com.chromanyan.chromaticarsenal.init.CATags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class CARecipes extends RecipeProvider {

    private final TagKey<Item> CHROMA_SHARD = CATags.Items.GEMS_CHROMA;
    private final TagKey<Item> ASCENSION_ESSENCE = CATags.Items.DUSTS_ASCENSION;

    public CARecipes(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @SuppressWarnings("SameParameterValue")
    private void packAndUnpack(@NotNull RecipeOutput recipeOutput, ItemLike unpacked, ItemLike packed, String name, String name2) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, unpacked, 9)
                .requires(packed)
                .unlockedBy("has_unpackable", has(unpacked))
                .save(recipeOutput, ChromaticArsenal.of(name + "_unpack"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, packed, 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', unpacked)
                .unlockedBy("has_unpackable", has(unpacked))
                .save(recipeOutput, ChromaticArsenal.of(name2));
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, CAItems.HARPY_FEATHER)
                .pattern("F")
                .pattern("C")
                .pattern("G")
                .define('F', Tags.Items.FEATHERS)
                .define('C', CHROMA_SHARD)
                .define('G', Tags.Items.INGOTS_GOLD)
                .unlockedBy("has_chroma_shard", has(CHROMA_SHARD))
                .save(recipeOutput, ChromaticArsenal.of("harpy_feather"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, CAItems.POLYCHROMATIC_FEATHER)
                .requires(CAItems.HARPY_FEATHER)
                .requires(ASCENSION_ESSENCE)
                .unlockedBy("has_ascension_essence", has(ASCENSION_ESSENCE))
                .save(recipeOutput, ChromaticArsenal.of("super_harpy_feather"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, CAItems.MAGIC_GARLIC_BREAD, 4)
                .requires(Items.BREAD)
                .requires(Items.BREAD)
                .requires(Items.BREAD)
                .requires(Items.BREAD)
                .requires(CHROMA_SHARD)
                .unlockedBy("has_chroma_shard", has(CHROMA_SHARD))
                .save(recipeOutput, ChromaticArsenal.of("magic_garlic_bread"));

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, CAItems.COSMICOLA)
                .pattern("ICI")
                .pattern("IAI")
                .pattern("ICI")
                .define('C', Items.CHORUS_FRUIT)
                .define('A', ASCENSION_ESSENCE)
                .define('I', Tags.Items.INGOTS_IRON)
                .unlockedBy("has_ascension_essence", has(ASCENSION_ESSENCE))
                .save(recipeOutput, ChromaticArsenal.of("cosmicola"));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, CAItems.GLASS_SHIELD)
                .pattern("GCG")
                .pattern("GGG")
                .pattern(" G ")
                .define('G', Tags.Items.GLASS_BLOCKS)
                .define('C', CHROMA_SHARD)
                .unlockedBy("has_chroma_shard", has(CHROMA_SHARD))
                .save(recipeOutput, ChromaticArsenal.of("glass_shield"));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, CAItems.WARD_CRYSTAL)
                .pattern("FAF")
                .pattern("ACA")
                .pattern("BAB")
                .define('A', Tags.Items.GEMS_AMETHYST)
                .define('F', Items.FERMENTED_SPIDER_EYE)
                .define('B', Items.BLAZE_POWDER)
                .define('C', CHROMA_SHARD)
                .unlockedBy("has_chroma_shard", has(CHROMA_SHARD))
                .save(recipeOutput, ChromaticArsenal.of("ward_crystal"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, CAItems.SHADOW_TREADS)
                .pattern("ELE")
                .pattern(" C ")
                .define('E', Items.ECHO_SHARD)
                .define('L', Items.LEATHER_BOOTS)
                .define('C', CHROMA_SHARD)
                .unlockedBy("has_echo_shard", has(Items.ECHO_SHARD))
                .save(recipeOutput, ChromaticArsenal.of("shadow_treads"));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, CAItems.DUALITY_RINGS)
                .pattern("W  ")
                .pattern("RCR")
                .pattern("  B")
                .define('W', Tags.Items.DYES_WHITE)
                .define('B', Tags.Items.DYES_BLACK)
                .define('R', CAItems.AMETHYST_RING)
                .define('C', CHROMA_SHARD)
                .unlockedBy("has_chroma_shard", has(CHROMA_SHARD))
                .save(recipeOutput, ChromaticArsenal.of("duality_rings"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, CAItems.FRIENDLY_FIRE_FLOWER)
                .pattern(" O ")
                .pattern("BCB")
                .pattern(" N ")
                .define('N', Tags.Items.INGOTS_NETHERITE)
                .define('B', Items.BLAZE_POWDER)
                .define('O', Items.ORANGE_TULIP)
                .define('C', CHROMA_SHARD)
                .unlockedBy("has_chroma_shard", has(CHROMA_SHARD))
                .save(recipeOutput, ChromaticArsenal.of("friendly_fire_flower"));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, CAItems.ADVANCING_HEART)
                .pattern("BGB")
                .define('G', CAItems.GOLDEN_HEART)
                .define('B', Items.DRAGON_BREATH)
                .unlockedBy("has_dragon_breath", has(Items.DRAGON_BREATH))
                .save(recipeOutput, ChromaticArsenal.of("advancing_heart"));

        packAndUnpack(recipeOutput, CAItems.CHROMA_SHARD, CAItems.CHROMA_BLOCK, "chroma_shard", "chroma_block");
    }
}
