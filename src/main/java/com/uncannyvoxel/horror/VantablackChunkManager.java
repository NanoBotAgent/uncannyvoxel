package com.uncannyvoxel.horror;

import com.uncannyvoxel.config.HorrorConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;

import java.util.HashMap;
import java.util.Map;

public final class VantablackChunkManager {

    private static final Map<Long, Boolean> VANTABLACK_CHUNKS = new HashMap<>();
    private static final int DEFAULT_RANGE = 3;

    private VantablackChunkManager() {}

    public static void init() {}

    public static void markChunkVantablack(ChunkPos chunkPos, boolean vantablack) {
        if (!HorrorConfig.get().horrorEnabled || !HorrorConfig.get().vantablackChunksEnabled) {
            VANTABLACK_CHUNKS.remove(chunkPos.toLong());
            return;
        }

        if (vantablack) {
            VANTABLACK_CHUNKS.put(chunkPos.toLong(), true);
        } else {
            VANTABLACK_CHUNKS.remove(chunkPos.toLong());
        }
    }

    public static boolean isVantablackChunk(ChunkPos chunkPos) {
        if (!HorrorConfig.get().horrorEnabled || !HorrorConfig.get().vantablackChunksEnabled) {
            return false;
        }
        return VANTABLACK_CHUNKS.getOrDefault(chunkPos.toLong(), false);
    }

    public static boolean isVantablackChunk(BlockPos blockPos) {
        return isVantablackChunk(new ChunkPos(blockPos));
    }

    public static boolean shouldDropLightmap(BlockPos cameraPos) {
        if (!HorrorConfig.get().horrorEnabled || !HorrorConfig.get().vantablackChunksEnabled) {
            return false;
        }

        ChunkPos chunkPos = new ChunkPos(cameraPos);
        return VANTABLACK_CHUNKS.getOrDefault(chunkPos.toLong(), false);
    }

    public static void markSulfurGeyserArea(BlockPos geyserPos) {
        int range = HorrorConfig.get().vantablackRangeChunks;
        ChunkPos center = new ChunkPos(geyserPos);

        for (int dx = -range; dx <= range; dx++) {
            for (int dz = -range; dz <= range; dz++) {
                ChunkPos pos = new ChunkPos(center.getX() + dx, center.getZ() + dz);
                markChunkVantablack(pos, true);
            }
        }
    }

    public static void clear() {
        VANTABLACK_CHUNKS.clear();
    }
}