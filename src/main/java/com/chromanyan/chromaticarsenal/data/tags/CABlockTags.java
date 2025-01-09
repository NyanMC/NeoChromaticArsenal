package com.chromanyan.chromaticarsenal.data.tags;

import com.chromanyan.chromaticarsenal.ChromaticArsenal;
import com.chromanyan.chromaticarsenal.init.CABlocks;
import com.chromanyan.chromaticarsenal.init.CATags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class CABlockTags extends net.neoforged.neoforge.common.data.BlockTagsProvider {

    public CABlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, ChromaticArsenal.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(CABlocks.CHROMA_BLOCK.get());
        tag(BlockTags.NEEDS_STONE_TOOL).add(CABlocks.CHROMA_BLOCK.get());
        tag(BlockTags.BEACON_BASE_BLOCKS).add(CABlocks.CHROMA_BLOCK.get());
        tag(CATags.Blocks.STORAGE_BLOCKS_CHROMA).add(CABlocks.CHROMA_BLOCK.get());
        tag(Tags.Blocks.STORAGE_BLOCKS).add(CABlocks.CHROMA_BLOCK.get());

        //tag(BlockTags.OCCLUDES_VIBRATION_SIGNALS).add(ModBlocks.BLAHAJ.get(), ModBlocks.CHROMANYAN.get());
    }
}
