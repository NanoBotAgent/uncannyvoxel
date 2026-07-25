package com.uncannyvoxel.item;

import com.uncannyvoxel.registry.ModSoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.entity.player.PlayerEntity;

import java.util.List;

public class DesaturatedEyeItem extends Item {

    public DesaturatedEyeItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (!world.isClient && world instanceof ServerWorld serverWorld) {
            BlockPos pos = player.getBlockPos();
            com.uncannyvoxel.portal.PortalController.tryActivate(serverWorld, pos, player);
        }
        return ActionResult.success(world.isClient());
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (!world.isClient && world instanceof ServerWorld serverWorld) {
            ServerPlayerEntity player = (ServerPlayerEntity) context.getPlayer();
            if (player != null) {
                com.uncannyvoxel.portal.PortalController.tryActivate(serverWorld, context.getBlockPos(), player);
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.uncannyvoxel.desaturated_eye.tooltip"));
        super.appendTooltip(stack, context, tooltip, type);
    }
}