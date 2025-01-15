package page.chromanyan.chromaticarsenal.block;

import page.chromanyan.chromaticarsenal.init.CABlocks;
import page.chromanyan.chromaticarsenal.init.CAItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ParticleUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class ChromaNyanBlock extends BlahajBlock {

    // ugly trinkets hitboxes that i'm way too lazy to refine
    protected static final VoxelShape DEFAULT_AABB = Shapes.box(0.2D, 0.0D, 0.2D, 0.8D, 0.8D, 0.8D);
    protected static final VoxelShape NORTH_AABB = Shapes.box(0.2D, 0.0D, 0.15D, 0.8D, 0.8D, 0.85D);
    protected static final VoxelShape SOUTH_AABB = Shapes.box(0.2D, 0.0D, 0.15D, 0.8D, 0.8D, 0.85D);
    protected static final VoxelShape WEST_AABB = Shapes.box(0.15D, 0.0D, 0.2D, 0.85D, 0.8D, 0.8D);
    protected static final VoxelShape EAST_AABB = Shapes.box(0.15D, 0.0D, 0.2D, 0.85D, 0.8D, 0.8D);

    public ChromaNyanBlock() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_PINK)
                .strength(0.8F)
                .sound(SoundType.WOOL)
                .noOcclusion()
                .isSuffocating(CABlocks::never)
                .isViewBlocking(CABlocks::never));
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos, @NotNull CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case NORTH -> NORTH_AABB;
            case SOUTH -> SOUTH_AABB;
            case WEST -> WEST_AABB;
            case EAST -> EAST_AABB;
            default -> DEFAULT_AABB;
        };
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        if (!stack.is(CAItems.CHROMA_SHARD)) return super.useItemOn(stack, state, level, pos, player, hand, hitResult);

        if (level.isClientSide()) {
            ParticleUtils.spawnParticleInBlock(level, pos, 20, new BlockParticleOption(ParticleTypes.BLOCK, CABlocks.CHROMA_BLOCK.get().defaultBlockState()));
        } else if (!player.hasInfiniteMaterials()) {
            stack.shrink(1);
            ItemStack offhand = player.getItemInHand(InteractionHand.OFF_HAND);
            if (offhand.is(ItemTags.SMALL_FLOWERS)) {
                offhand.shrink(1);
                level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5f, pos.getY() + 1f, pos.getZ() + 0.5f, CAItems.LUNA_FLOWER.toStack(), 0, 0.5f, 0));
            }
        }

        level.playSound(player, pos, SoundEvents.GENERIC_EAT, SoundSource.BLOCKS);

        return ItemInteractionResult.sidedSuccess(level.isClientSide);
    }
}
