package com.uncannyvoxel.portal;

import com.uncannyvoxel.registry.ModBlocks;
import com.uncannyvoxel.registry.ModDimensions;
import com.uncannyvoxel.registry.ModItems;
import com.uncannyvoxel.registry.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class PortalController {

    private static final Map<BlockPos, PortalState> ACTIVE_PORTALS = new HashMap<>();
    private static final int COOLDOWN_TICKS = 100;

    private static class PortalState {
        int cooldown = 0;
        boolean active = false;
    }

    public static void init() {}

    public static void onWorldLoad(ServerLevel level) {
        ACTIVE_PORTALS.clear();
    }

    public static void onWorldTick(ServerLevel level) {
        ACTIVE_PORTALS.entrySet().removeIf(entry -> {
            PortalState state = entry.getValue();
            if (state.cooldown > 0) {
                state.cooldown--;
            }
            if (!state.active) {
                return state.cooldown <= 0;
            }
            BlockState centerState = level.getBlockState(entry.getKey());
            if (!centerState.is(ModBlocks.SULFUR_GLASS_MIRROR)) {
                state.active = false;
                state.cooldown = COOLDOWN_TICKS;
                return false;
            }
            return false;
        });
    }

    public static void tryActivate(ServerLevel level, BlockPos center, ServerPlayer player) {
        if (!level.getBlockState(center).is(ModBlocks.SULFUR_GLASS_MIRROR)) {
            return;
        }

        PortalState state = ACTIVE_PORTALS.computeIfAbsent(center.immutable(), k -> new PortalState());
        if (state.cooldown > 0 || state.active) {
            return;
        }

        Predicate<BlockState> framePredicate = s -> s.is(ModTags.SUBSTRATE_FRAME);
        Predicate<BlockState> centerPredicate = s -> s.is(ModBlocks.SULFUR_GLASS_MIRROR);

        if (!PortalFrameValidator.isValid(level, center, framePredicate, centerPredicate)) {
            return;
        }

        ItemStack eyeStack = player.getMainHandItem();
        if (!isDesaturatedEye(eyeStack)) {
            return;
        }

        ActivationDecision decision = ActivationDecision.evaluate(true, eyeStack.getCount(), state.cooldown);
        if (!decision.activate()) {
            return;
        }

        if (decision.consumeEye() > 0) {
            eyeStack.shrink(decision.consumeEye());
        }

        activatePortal(level, center, state);
    }

    private static void activatePortal(ServerLevel level, BlockPos center, PortalState state) {
        state.active = true;
        state.cooldown = COOLDOWN_TICKS;

        level.playSound(null, center, SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 1.0f, 0.8f);
        level.playSound(null, center, SoundEvents.ENDERMAN_TELEPORT, SoundSource.BLOCKS, 0.7f, 1.2f);

        for (ServerPlayer player : level.getPlayers(p -> p.blockPosition().closerThan(center, 5))) {
            teleportToSubstrate(player, level);
        }
    }

    private static void teleportToSubstrate(ServerPlayer player, ServerLevel level) {
        ServerLevel substrateLevel = level.getServer().getLevel(ModDimensions.SUBSTRATE_LEVEL);
        if (substrateLevel == null) {
            return;
        }

        BlockPos spawnPos = SubstrateSpawn.findSafeSpawn(substrateLevel, BlockPos.ZERO);
        player.teleportTo(substrateLevel, spawnPos.getX() + 0.5, spawnPos.getY() + 1, spawnPos.getZ() + 0.5, java.util.Set.of(),
                player.getYRot(), player.getXRot(), true);
    }

    private static boolean isDesaturatedEye(ItemStack stack) {
        return stack.is(ModItems.DESATURATED_EYE);
    }

    public static void deactivate(BlockPos center) {
        PortalState state = ACTIVE_PORTALS.get(center.immutable());
        if (state != null) {
            state.active = false;
            state.cooldown = COOLDOWN_TICKS;
        }
    }
}
