package com.uncannyvoxel.block;

import com.uncannyvoxel.blockentity.ChestMimicBlockEntity;
import com.uncannyvoxel.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class ChestMimicBlock extends Block implements net.minecraft.world.level.block.EntityBlock {

    public ChestMimicBlock(Properties properties) {
        super(properties
                .sound(net.minecraft.world.level.block.SoundType.WOOD)
                .strength(2.5f)
                .noOcclusion()
        );
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ChestMimicBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntities.CHEST_MIMIC, ChestMimicBlockEntity::tick);
    }

    private static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(
            BlockEntityType<A> type, BlockEntityType<E> checkedType, BlockEntityTicker<? super E> ticker) {
        return checkedType == type ? (BlockEntityTicker<A>) ticker : null;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide()) {
            return InteractionResult.sidedSuccess(level.isClientSide());
        }

        if (!com.uncannyvoxel.config.HorrorConfig.get().chestMimicEnabled) {
            return InteractionResult.PASS;
        }

        BlockEntity entity = level.getBlockEntity(pos);
        if (entity instanceof ChestMimicBlockEntity mimic) {
            mimic.triggerMimic(player);
            return InteractionResult.sidedSuccess(level.isClientSide());
        }

        return InteractionResult.PASS;
    }

    @Override
    public net.minecraft.world.level.block.RenderShape getRenderShape(BlockState state) {
        return net.minecraft.world.level.block.RenderShape.MODEL;
    }
}
