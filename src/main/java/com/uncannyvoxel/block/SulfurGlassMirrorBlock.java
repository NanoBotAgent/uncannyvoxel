package com.uncannyvoxel.block;

import com.uncannyvoxel.blockentity.SulfurGlassMirrorBlockEntity;
import com.uncannyvoxel.registry.ModBlockEntities;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SulfurGlassMirrorBlock extends BlockWithEntity implements BlockEntityProvider {

    public SulfurGlassMirrorBlock(Settings settings) {
        super(settings.nonOpaque().strength(0.3f).sounds(BlockSoundGroup.GLASS));
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SulfurGlassMirrorBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : validateTicker(type, ModBlockEntities.SULFUR_GLASS_MIRROR, SulfurGlassMirrorBlockEntity::tick);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient && hand == Hand.MAIN_HAND) {
            ItemStack stack = player.getStackInHand(hand);
            if (stack.isOf(com.uncannyvoxel.registry.ModItems.DESATURATED_EYE)) {
                com.uncannyvoxel.portal.PortalController.tryActivate((ServerWorld) world, pos, (net.minecraft.server.network.ServerPlayerEntity) player);
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof SulfurGlassMirrorBlockEntity mirror) {
                mirror.onPortalDeactivated();
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}