package io.github.apfelrauber.block.custom;

import io.github.apfelrauber.block.entity.DeepslateChestBlockEntity;
import io.github.apfelrauber.block.entity.ModBlockEntities;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class DeepslateChestBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final IntProperty PROGRESS = IntProperty.of("progress",0,4);

    public DeepslateChestBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(PROGRESS,0));
    }

    private static VoxelShape SHAPE = Block.createCuboidShape(0, 0, 0, 16, 11, 16);

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx).with(Properties.HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(PROGRESS);
    }

    /* BLOCK ENTITY */

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof DeepslateChestBlockEntity) {
                ItemScatterer.spawn(world, pos, (DeepslateChestBlockEntity)blockEntity);
                world.updateComparators(pos,this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos,
                              PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            DeepslateChestBlockEntity blockEntity = (DeepslateChestBlockEntity) world.getBlockEntity(pos);
            NbtCompound nbt = new NbtCompound();
            blockEntity.writeNbt(nbt);

            int progress = nbt.getInt("deepslate_chest.progress");
            int cooldown = nbt.getInt("deepslate_chest.cooldown");

            if(cooldown == 0 && progress < 5) {
                world.playSound(null, pos, SoundEvents.BLOCK_POLISHED_DEEPSLATE_STEP, SoundCategory.BLOCKS, 1f, 1f);

                cooldown++;
                nbt.remove("deepslate_chest.cooldown");
                nbt.putInt("deepslate_chest.cooldown", cooldown);
            }

            world.setBlockState(pos, state.with(PROGRESS, progress));

            blockEntity.readNbt(nbt);
            blockEntity.markDirty();

            if(progress < 4) return ActionResult.SUCCESS;

            if(progress >= 4){
                world.playSound(null, pos, SoundEvents.BLOCK_POLISHED_DEEPSLATE_STEP, SoundCategory.BLOCKS, 1f, 1f);
            }

            NamedScreenHandlerFactory screenHandlerFactory = (DeepslateChestBlockEntity) world.getBlockEntity(pos);

            if (screenHandlerFactory != null) {
                player.openHandledScreen(screenHandlerFactory);
            }
        }

        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DeepslateChestBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntities.DEEPLSATE_CHEST, DeepslateChestBlockEntity::tick);
    }
}
