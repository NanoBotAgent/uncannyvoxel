package com.uncannyvoxel.item;

import com.uncannyvoxel.registry.ModSoundEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class DesaturatedEyeItem extends Item {

    public DesaturatedEyeItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            BlockPos pos = player.blockPosition();
            com.uncannyvoxel.portal.PortalController.tryActivate(serverLevel, pos, (ServerPlayer) player);
        }
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            ServerPlayer player = (ServerPlayer) context.getPlayer();
            if (player != null) {
                com.uncannyvoxel.portal.PortalController.tryActivate(serverLevel, context.getClickedPos(), player);
                return InteractionResult.sidedSuccess(level.isClientSide());
            }
        }
        return InteractionResult.PASS;
    }
}