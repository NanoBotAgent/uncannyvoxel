package com.uncannyvoxel.block;

import com.uncannyvoxel.blockentity.CompactedHairBlockEntity;
import com.uncannyvoxel.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;

public class CompactedHairBlock extends Block implements EntityBlock {

    private static final VoxelShape SHAPE = Shapes.box(0, 0, 0, 1, 1, 1);

    public CompactedHairBlock(Properties properties) {
        super(properties
                .sound(SoundType.GRAVEL)
                .strength(1.5f, 3.0f)
        );
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CompactedHairBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(net.minecraft.world.level.Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntities.COMPACTED_HAIR, CompactedHairBlockEntity::tick);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean canSurvive(BlockState state, net.minecraft.world.level.LevelAccessor level, BlockPos pos) {
        return true;
    }
}
