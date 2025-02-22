package page.chromanyan.chromaticarsenal.init;

import page.chromanyan.chromaticarsenal.ChromaticArsenal;
import page.chromanyan.chromaticarsenal.block.BlahajBlock;
import page.chromanyan.chromaticarsenal.block.ChromaNyanBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CABlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ChromaticArsenal.MODID);

    public static final DeferredBlock<RotatedPillarBlock> CHROMA_BLOCK = BLOCKS.register("chroma_block",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.DIAMOND)
                    .requiresCorrectToolForDrops()
                    .strength(5.0F, 6.0F)
                    .sound(SoundType.METAL)
                    .lightLevel((state) -> 15))
    );

    public static final DeferredBlock<BlahajBlock> BLAHAJ = BLOCKS.register("blahaj", () -> new BlahajBlock());
    public static final DeferredBlock<ChromaNyanBlock> CHROMANYAN = BLOCKS.register("chromanyan_plush", ChromaNyanBlock::new);

    @SuppressWarnings("unused")
    public static boolean never(BlockState p_50806_, BlockGetter p_50807_, BlockPos p_50808_) {
        return false;
    }
}
