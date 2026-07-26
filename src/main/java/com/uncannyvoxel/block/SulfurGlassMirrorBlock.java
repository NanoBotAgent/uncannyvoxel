package com.uncannyvoxel.block;

import com.uncannyvoxel.blockentity.SulfurGlassMirrorBlockEntity;
import com.uncannyvoxel.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;

public class SulfurGlassMirrorBlock extends Block implements EntityBlock {

    public SulfurGlassMirrorBlock(Properties properties) {
        super(properties.noOcclusion().strength(0.3f).sound(net.minecraft.world.level.block.SoundType.GLASS));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SulfurGlassMirrorBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide() ? null : createTickerHelper(type, ModBlockEntities.SULFUR_GLASS_MIRROR, SulfurGlassMirrorBlockEntity::tick);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide() && hand == InteractionHand.MAIN_HAND) {
            ItemStack stack = player.getItemInHand(hand);
            if (stack.is(com.uncannyvoxel.registry.ModItems.DESATURATED_EYE)) {
                com.uncannyvoxel.portal.PortalController.tryActivate((ServerLevel) level, pos, (net.minecraft.server.level.ServerPlayer) player);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof SulfurGlassMirrorBlockEntity mirror) {
                mirror.onPortalDeactivated();
            }
            super.onRemove(state, level, pos, newState, moved);
        }
    }

    @Override
    public net.minecraft.world.level.block.RenderShape getRenderShape(BlockState state) {
        return net.minecraft.world.level.block.RenderShape.MODEL;
    }
}