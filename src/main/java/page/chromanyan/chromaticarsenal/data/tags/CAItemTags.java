package page.chromanyan.chromaticarsenal.data.tags;

import page.chromanyan.chromaticarsenal.ChromaticArsenal;
import page.chromanyan.chromaticarsenal.init.CAItems;
import page.chromanyan.chromaticarsenal.init.CATags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class CAItemTags extends ItemTagsProvider {


    public CAItemTags(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, ChromaticArsenal.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        copy(CATags.Blocks.STORAGE_BLOCKS_CHROMA, CATags.Items.STORAGE_BLOCKS_CHROMA);
        copy(Tags.Blocks.STORAGE_BLOCKS, Tags.Items.STORAGE_BLOCKS);

        tag(CATags.Items.GEMS_CHROMA).add(CAItems.CHROMA_SHARD.get());
        tag(Tags.Items.GEMS).add(CAItems.CHROMA_SHARD.get());
        tag(ItemTags.BEACON_PAYMENT_ITEMS).add(CAItems.CHROMA_SHARD.get());

        tag(ItemTags.FREEZE_IMMUNE_WEARABLES).add(CAItems.CRYO_RING.get());

        tag(CATags.Items.DUSTS_ASCENSION).add(CAItems.ASCENSION_ESSENCE.get());
        tag(Tags.Items.DUSTS).add(CAItems.ASCENSION_ESSENCE.get());

        tag(CATags.Items.SECRET).add(CAItems.CHROMANYAN.get(), CAItems.LUNA_FLOWER.get());
        tag(Tags.Items.HIDDEN_FROM_RECIPE_VIEWERS).addTag(CATags.Items.SECRET);
    }
}
