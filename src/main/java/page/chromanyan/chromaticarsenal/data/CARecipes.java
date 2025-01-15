package page.chromanyan.chromaticarsenal.data;

import page.chromanyan.chromaticarsenal.ChromaticArsenal;
import page.chromanyan.chromaticarsenal.init.CAItems;
import page.chromanyan.chromaticarsenal.init.CATags;
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

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, CAItems.DIAMOND_HEART)
                .pattern("D D")
                .pattern("BHB")
                .pattern(" A ")
                .define('D', Tags.Items.GEMS_DIAMOND)
                .define('B', Tags.Items.STORAGE_BLOCKS_DIAMOND)
                .define('H', CAItems.GOLDEN_HEART)
                .define('A', ASCENSION_ESSENCE)
                .unlockedBy("has_ascension_essence", has(ASCENSION_ESSENCE))
                .save(recipeOutput, ChromaticArsenal.of("super_golden_heart"));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, CAItems.UNDYING_SHIELD)
                .pattern("TAT")
                .pattern("TST")
                .pattern(" T ")
                .define('T', Tags.Items.GLASS_BLOCKS_TINTED)
                .define('S', CAItems.GLASS_SHIELD)
                .define('A', ASCENSION_ESSENCE)
                .unlockedBy("has_ascension_essence", has(ASCENSION_ESSENCE))
                .save(recipeOutput, ChromaticArsenal.of("super_glass_shield"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, CAItems.DISPELLING_CRYSTAL)
                .requires(CAItems.WARD_CRYSTAL)
                .requires(Tags.Items.BUCKETS_MILK)
                .requires(ASCENSION_ESSENCE)
                .unlockedBy("has_ascension_essence", has(ASCENSION_ESSENCE))
                .save(recipeOutput, ChromaticArsenal.of("super_ward_crystal"));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, CAItems.CELESTIAL_CHARM)
                .pattern("FSF")
                .pattern(" A ")
                .define('F', Items.FIRE_CHARGE)
                .define('S', CAItems.SHADOW_TREADS)
                .define('A', ASCENSION_ESSENCE)
                .unlockedBy("has_ascension_essence", has(ASCENSION_ESSENCE))
                .save(recipeOutput, ChromaticArsenal.of("super_shadow_treads"));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, CAItems.INFERNO_FLOWER)
                .pattern(" T ")
                .pattern("BAB")
                .pattern(" F ")
                .define('F', CAItems.FRIENDLY_FIRE_FLOWER)
                .define('B', Items.BLAZE_POWDER)
                .define('T', Items.TORCHFLOWER)
                .define('A', ASCENSION_ESSENCE)
                .unlockedBy("has_ascension_essence", has(ASCENSION_ESSENCE))
                .save(recipeOutput, ChromaticArsenal.of("super_friendly_fire_flower"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, CAItems.PRISMATIC_CRYSTAL)
                .pattern(" A ")
                .pattern("E E")
                .pattern("ELE")
                .define('L', CAItems.LUNAR_CRYSTAL)
                .define('E', Tags.Items.END_STONES)
                .define('A', ASCENSION_ESSENCE)
                .unlockedBy("has_ascension_essence", has(ASCENSION_ESSENCE))
                .save(recipeOutput, ChromaticArsenal.of("super_lunar_crystal"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, CAItems.ILLUMINATED_SOUL)
                .pattern("AI ")
                .pattern("I I")
                .pattern(" I ")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('A', ASCENSION_ESSENCE)
                .unlockedBy("has_ascension_essence", has(ASCENSION_ESSENCE))
                .save(recipeOutput, ChromaticArsenal.of("super_glow_ring"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, CAItems.ASCENDED_STAR)
                .requires(Tags.Items.NETHER_STARS)
                .requires(ASCENSION_ESSENCE)
                .unlockedBy("has_ascension_essence", has(ASCENSION_ESSENCE))
                .save(recipeOutput, ChromaticArsenal.of("ascended_star"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, CAItems.WORLD_ANCHOR)
                .requires(Items.ANVIL)
                .requires(CHROMA_SHARD)
                .unlockedBy("has_chroma_shard", has(CHROMA_SHARD))
                .save(recipeOutput, ChromaticArsenal.of("world_anchor"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, CAItems.CURSED_TOTEM)
                .requires(Items.TOTEM_OF_UNDYING)
                .requires(CHROMA_SHARD)
                .unlockedBy("has_item", has(Items.TOTEM_OF_UNDYING))
                .save(recipeOutput, ChromaticArsenal.of("cursed_totem"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, CAItems.GRAVITY_STONE)
                .requires(Tags.Items.END_STONES)
                .requires(CHROMA_SHARD)
                .unlockedBy("has_end_stone", has(Tags.Items.END_STONES))
                .save(recipeOutput, ChromaticArsenal.of("gravity_stone"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, CAItems.VERTICAL_STASIS)
                .pattern(" S ")
                .pattern("PCP")
                .pattern(" S ")
                .define('P', Items.POPPED_CHORUS_FRUIT)
                .define('S', Items.SHULKER_SHELL)
                .define('C', CHROMA_SHARD)
                .unlockedBy("has_shulker_shell", has(Items.SHULKER_SHELL))
                .save(recipeOutput, ChromaticArsenal.of("vertical_stasis_stone"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, CAItems.ANONYMITY_UMBRELLA)
                .pattern("ECE")
                .pattern(" / ")
                .pattern(" / ")
                .define('E', Items.ECHO_SHARD)
                .define('/', Tags.Items.RODS_WOODEN)
                .define('C', CHROMA_SHARD)
                .unlockedBy("has_echo_shard", has(Items.ECHO_SHARD))
                .save(recipeOutput, ChromaticArsenal.of("anonymity_umbrella"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, CAItems.COPPER_RING)
                .pattern(" # ")
                .pattern("# #")
                .pattern(" # ")
                .define('#', Tags.Items.INGOTS_COPPER)
                .unlockedBy("has_copper", has(Tags.Items.INGOTS_COPPER))
                .save(recipeOutput, ChromaticArsenal.of("copper_ring"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, CAItems.AMETHYST_RING)
                .pattern(" # ")
                .pattern("# #")
                .pattern(" # ")
                .define('#', Tags.Items.GEMS_AMETHYST)
                .unlockedBy("has_amethyst", has(Tags.Items.GEMS_AMETHYST))
                .save(recipeOutput, ChromaticArsenal.of("amethyst_ring"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, CAItems.VITAL_STONE)
                .pattern("# #")
                .pattern("#M#")
                .pattern(" # ")
                .define('#', Tags.Items.GEMS_AMETHYST)
                .define('M', Items.GLISTERING_MELON_SLICE)
                .unlockedBy("has_melon", has(Items.MELON))
                .save(recipeOutput, ChromaticArsenal.of("vital_stone"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CAItems.CHROMANYAN)
                .pattern("TNS")
                .pattern("#M#")
                .pattern("ACE")
                .define('#', CHROMA_SHARD)
                .define('T', Items.LIGHT_BLUE_WOOL)
                .define('N', Items.PINK_WOOL)
                .define('S', Items.WHITE_WOOL)
                .define('A', Items.BLACK_WOOL)
                .define('C', Items.GRAY_WOOL)
                .define('E', Items.PURPLE_WOOL)
                .define('M', Items.MAGENTA_WOOL)
                .unlockedBy("impossible_under_normal_circumstances", has(Items.BARRIER))
                .save(recipeOutput, ChromaticArsenal.of("chromanyan"));

        packAndUnpack(recipeOutput, CAItems.CHROMA_SHARD, CAItems.CHROMA_BLOCK, "chroma_shard", "chroma_block");
    }
}
