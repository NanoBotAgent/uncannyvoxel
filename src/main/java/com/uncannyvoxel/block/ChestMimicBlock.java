package com.uncannyvoxel.block;

import com.uncannyvoxel.blockentity.ChestMimicBlockEntity;
import com.uncannyvoxel.registry.ModBlockEntities;
import com.uncannyvoxel.registry.ModSoundEvents;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ChestMimicBlock extends BlockWithEntity {

    public ChestMimicBlock(Settings settings) {
        super(settings
                .sounds(BlockSoundGroup.WOOD)
                .strength(2.5f)
                .nonOpaque()
        );
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ChestMimicBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlockEntities.CHEST_MIMIC, ChestMimicBlockEntity::tick);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }

        if (!com.uncannyvoxel.config.HorrorConfig.get().chestMimicEnabled) {
            return ActionResult.PASS;
        }

        BlockEntity entity = world.getBlockEntity(pos);
        if (entity instanceof ChestMimicBlockEntity mimic) {
            mimic.onPlayerInteract(player);
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}