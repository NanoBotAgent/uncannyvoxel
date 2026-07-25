package com.uncannyvoxel.item;

import com.uncannyvoxel.horror.VantablackChunkManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.entity.player.PlayerEntity;

public class TetherStakeItem extends Item {

    public TetherStakeItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (!world.isClient) {
            PlayerEntity player = context.getPlayer();
            if (player != null) {
                BlockPos pos = context.getBlockPos();
                net.minecraft.block.entity.BlockEntity be = world.getBlockEntity(pos);
                if (be instanceof com.uncannyvoxel.blockentity.TetherStakeBlockEntity stake) {
                    stake.activate(player);
                    return ActionResult.SUCCESS;
                }
            }
        }
        return ActionResult.PASS;
    }
}