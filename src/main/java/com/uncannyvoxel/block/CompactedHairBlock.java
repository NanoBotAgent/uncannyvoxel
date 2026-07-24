package com.uncannyvoxel.block;

import com.uncannyvoxel.blockentity.CompactedHairBlockEntity;
import com.uncannyvoxel.registry.ModBlockEntities;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class CompactedHairBlock extends BlockWithEntity {

    private static final VoxelShape SHAPE = VoxelShapes.cuboid(0, 0, 0, 1, 1, 1);

    public CompactedHairBlock(Settings settings) {
        super(settings
                .sounds(BlockSoundGroup.GRAVEL)
                .strength(1.5f, 3.0f)
        );
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CompactedHairBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlockEntities.COMPACTED_HAIR, CompactedHairBlockEntity::tick);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return true;
    }
}