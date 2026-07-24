package com.uncannyvoxel.horror;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class DreadModel {

    private static final Map<BlockPos, Integer> SAFE_ZONES = new ConcurrentHashMap<>();
    private static final Set<BlockPos> ENTITY_PROXIMITY_CACHE = ConcurrentHashMap.newKeySet();

    private DreadModel() {}

    public static void registerSafeZone(BlockPos center, int radius) {
        SAFE_ZONES.put(center.toImmutable(), radius);
    }

    public static void unregisterSafeZone(BlockPos center) {
        SAFE_ZONES.remove(center.toImmutable());
    }

    public static boolean isInSafeZone(BlockPos pos) {
        for (Map.Entry<BlockPos, Integer> entry : SAFE_ZONES.entrySet()) {
            BlockPos center = entry.getKey();
            int radius = entry.getValue();
            if (center.isWithinDistance(pos, radius)) {
                return true;
            }
        }
        return false;
    }

    public static float getDreadLevel(World world, BlockPos playerPos) {
        if (world.isClient) return 0.0f;

        // Check safe zones
        if (isInSafeZone(playerPos)) {
            return 0.0f;
        }

        // Count nearby hostile entities
        Box searchBox = new Box(playerPos).expand(32);
        int nearbyHostiles = world.getEntitiesByClass(
                net.minecraft.entity.mob.HostileEntity.class,
                searchBox,
                e -> true
        ).size();

        // Base dread from entity count
        float entityDread = Math.min(nearbyHostiles * 0.05f, 0.5f);

        // Additional dread from darkness
        float lightLevel = world.getLightLevel(playerPos);
        float darknessDread = Math.max(0, (7 - lightLevel) / 7.0f) * 0.3f;

        // Vantablack proximity
        float vantablackDread = com.uncannyvoxel.horror.VantablackChunkManager.isVantablackChunk(playerPos) ? 0.4f : 0.0f;

        return Math.min(entityDread + darknessDread + vantablackDread, 1.0f);
    }

    public static void cacheNearbyEntity(BlockPos entityPos) {
        ENTITY_PROXIMITY_CACHE.add(entityPos.toImmutable());
    }

    public static void clearCache() {
        ENTITY_PROXIMITY_CACHE.clear();
    }
}