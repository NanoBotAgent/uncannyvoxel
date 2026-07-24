package com.uncannyvoxel.portal;

import com.uncannyvoxel.registry.ModBlocks;
import com.uncannyvoxel.registry.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PortalController {

    private static final Map<BlockPos, PortalState> ACTIVE_PORTALS = new HashMap<>();
    private static final int COOLDOWN_TICKS = 100;

    private static class PortalState {
        int cooldown = 0;
        boolean active = false;
    }

    public static void init() {}

    public static void onWorldLoad(ServerWorld world) {
        ACTIVE_PORTALS.clear();
    }

    public static void onWorldTick(ServerWorld world) {
        ACTIVE_PORTALS.entrySet().removeIf(entry -> {
            PortalState state = entry.getValue();
            if (state.cooldown > 0) {
                state.cooldown--;
            }
            if (!state.active) {
                return state.cooldown <= 0;
            }
            BlockState centerState = world.getBlockState(entry.getKey());
            if (!centerState.isOf(ModBlocks.SULFUR_GLASS_MIRROR)) {
                state.active = false;
                state.cooldown = COOLDOWN_TICKS;
                return false;
            }
            return false;
        });
    }

    public static void tryActivate(ServerWorld world, BlockPos center, ServerPlayerEntity player) {
        if (!world.getBlockState(center).isOf(ModBlocks.SULFUR_GLASS_MIRROR)) {
            return;
        }

        PortalState state = ACTIVE_PORTALS.computeIfAbsent(center.toImmutable(), k -> new PortalState());
        if (state.cooldown > 0 || state.active) {
            return;
        }

        Predicate<BlockState> framePredicate = s -> s.isIn(ModTags.SUBSTRATE_FRAME);
        Predicate<BlockState> centerPredicate = s -> s.isOf(ModBlocks.SULFUR_GLASS_MIRROR);

        if (!PortalFrameValidator.isValid(world, center, framePredicate, centerPredicate)) {
            return;
        }

        ItemStack eyeStack = player.getMainHandStack();
        if (!isDesaturatedEye(eyeStack)) {
            return;
        }

        ActivationDecision decision = ActivationDecision.evaluate(true, eyeStack.getCount(), state.cooldown);
        if (!decision.activate()) {
            return;
        }

        if (decision.consumeEye() > 0) {
            eyeStack.decrement(decision.consumeEye());
        }

        activatePortal(world, center, state);
    }

    private static void activatePortal(ServerWorld world, BlockPos center, PortalState state) {
        state.active = true;
        state.cooldown = COOLDOWN_TICKS;

        world.playSound(null, center, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS, 1.0f, 0.8f);
        world.playSound(null, center, SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.BLOCKS, 0.7f, 1.2f);

        for (ServerPlayerEntity player : world.getPlayers(p -> p.getBlockPos().isWithinDistance(center, 5))) {
            teleportToSubstrate(player, world);
        }
    }

    private static void teleportToSubstrate(ServerPlayerEntity player, ServerWorld world) {
        ServerWorld substrateWorld = world.getServer().getWorld(ModDimensions.SUBSTRATE);
        if (substrateWorld == null) {
            return;
        }

        BlockPos spawnPos = SubstrateSpawn.findSafeSpawn(substrateWorld, BlockPos.ORIGIN);
        player.teleport(substrateWorld, spawnPos.getX() + 0.5, spawnPos.getY() + 1, spawnPos.getZ() + 0.5,
                player.getYaw(), player.getPitch());
    }

    private static boolean isDesaturatedEye(ItemStack stack) {
        return stack.isOf(ModItems.DESATURATED_EYE);
    }

    public static void deactivate(BlockPos center) {
        PortalState state = ACTIVE_PORTALS.get(center.toImmutable());
        if (state != null) {
            state.active = false;
            state.cooldown = COOLDOWN_TICKS;
        }
    }
}