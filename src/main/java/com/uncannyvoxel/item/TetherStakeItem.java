package com.uncannyvoxel.item;

import com.uncannyvoxel.horror.VantablackChunkManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

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
                BlockEntity be = world.getBlockEntity(pos);
                if (be instanceof com.uncannyvoxel.blockentity.TetherStakeBlockEntity stake) {
                    stake.activate(player);
                    return ActionResult.SUCCESS;
                }
            }
        }
        return ActionResult.PASS;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.uncannyvoxel.tether_stake.tooltip1"));
        tooltip.add(Text.translatable("item.uncannyvoxel.tether_stake.tooltip2"));
        super.appendTooltip(stack, context, tooltip, type);
    }
}