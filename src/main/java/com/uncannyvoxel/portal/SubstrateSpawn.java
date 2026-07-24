package com.uncannyvoxel.portal;

import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;

public final class SubstrateSpawn {

    private SubstrateSpawn() {}

    public static BlockPos findSafeSpawn(ServerWorld world, BlockPos origin) {
        BlockPos.Mutable mutable = origin.mutableCopy();
        int radius = 0;
        int maxRadius = 32;

        while (radius <= maxRadius) {
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    mutable.set(origin.getX() + dx, origin.getY(), origin.getZ() + dz);

                    if (isSafeSpawn(world, mutable)) {
                        int y = findSurfaceY(world, mutable);
                        return new BlockPos(mutable.getX(), y + 1, mutable.getZ());
                    }
                }
            }
            radius++;
        }

        // Fallback: create platform at origin
        createPlatform(world, origin);
        return origin.up(2);
    }

    private static boolean isSafeSpawn(ServerWorld world, BlockPos pos) {
        BlockPos below = pos.down();
        if (!world.getBlockState(below).isSolidBlock(world, below)) {
            return false;
        }
        if (!world.getBlockState(pos).isAir()) {
            return false;
        }
        if (!world.getBlockState(pos.up()).isAir()) {
            return false;
        }
        return true;
    }

    private static int findSurfaceY(ServerWorld world, BlockPos pos) {
        int y = world.getTopY() - 1;
        while (y > world.getBottomY()) {
            BlockPos testPos = pos.withY(y);
            if (world.getBlockState(testPos).isSolidBlock(world, testPos)) {
                return y;
            }
            y--;
        }
        return world.getBottomY() + 10;
    }

    private static void createPlatform(ServerWorld world, BlockPos origin) {
        for (int x = -5; x <= 5; x++) {
            for (int z = -5; z <= 5; z++) {
                world.setBlockState(origin.add(x, 0, z), Blocks.BEDROCK.getDefaultState());
                world.setBlockState(origin.add(x, 1, z), Blocks.AIR.getDefaultState());
            }
        }
    }
}