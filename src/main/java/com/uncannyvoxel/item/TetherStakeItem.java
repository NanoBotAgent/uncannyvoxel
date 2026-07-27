package com.uncannyvoxel.item;

import com.uncannyvoxel.horror.VantablackChunkManager;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class TetherStakeItem extends Item {

    public TetherStakeItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (!level.isClientSide()) {
            Player player = context.getPlayer();
            if (player != null) {
                BlockPos pos = context.getClickedPos();
                BlockEntity be = level.getBlockEntity(pos);
                if (be instanceof com.uncannyvoxel.blockentity.TetherStakeBlockEntity stake) {
                    stake.activate(player);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.PASS;
    }
}
