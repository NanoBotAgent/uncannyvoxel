package com.uncannyvoxel.horror;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.Mob;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class DreadModel {

    private static final Map<BlockPos, Integer> SAFE_ZONES = new ConcurrentHashMap<>();
    private static final Set<BlockPos> ENTITY_PROXIMITY_CACHE = ConcurrentHashMap.newKeySet();

    private DreadModel() {}

    public static void registerSafeZone(BlockPos center, int radius) {
        SAFE_ZONES.put(center.immutable(), radius);
    }

    public static void unregisterSafeZone(BlockPos center) {
        SAFE_ZONES.remove(center.immutable());
    }

    public static boolean isInSafeZone(BlockPos pos) {
        for (Map.Entry<BlockPos, Integer> entry : SAFE_ZONES.entrySet()) {
            BlockPos center = entry.getKey();
            int radius = entry.getValue();
            if (center.closerThan(pos, radius)) {
                return true;
            }
        }
        return false;
    }

    public static float getDreadLevel(Level level, BlockPos playerPos) {
        if (level.isClientSide()) return 0.0f;

        if (isInSafeZone(playerPos)) {
            return 0.0f;
        }

        AABB searchBox = AABB.ofSize(Vec3.atCenterOf(playerPos), 64, 64, 64);
        int nearbyHostiles = level.getEntitiesOfClass(
                Mob.class,
                searchBox,
                e -> true
        ).size();

        float entityDread = Math.min(nearbyHostiles * 0.05f, 0.5f);

        int lightLevel = level.getMaxLocalRawBrightness(playerPos);
        float darknessDread = Math.max(0, (7 - lightLevel) / 7.0f) * 0.3f;

        float vantablackDread = VantablackChunkManager.isVantablackChunk(playerPos) ? 0.4f : 0.0f;

        return Math.min(entityDread + darknessDread + vantablackDread, 1.0f);
    }

    public static void cacheNearbyEntity(BlockPos entityPos) {
        ENTITY_PROXIMITY_CACHE.add(entityPos.immutable());
    }

    public static void clearCache() {
        ENTITY_PROXIMITY_CACHE.clear();
    }
}
