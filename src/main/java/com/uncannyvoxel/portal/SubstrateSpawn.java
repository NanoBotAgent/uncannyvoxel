package com.uncannyvoxel.portal;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public final class SubstrateSpawn {

    private SubstrateSpawn() {}

    public static BlockPos findSafeSpawn(ServerLevel level, BlockPos origin) {
        BlockPos.MutableBlockPos mutable = origin.mutable();
        int radius = 0;
        int maxRadius = 32;

        while (radius <= maxRadius) {
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    mutable.set(origin.getX() + dx, origin.getY(), origin.getZ() + dz);

                    if (isSafeSpawn(level, mutable)) {
                        int y = findSurfaceY(level, mutable);
                        return new BlockPos(mutable.getX(), y + 1, mutable.getZ());
                    }
                }
            }
            radius++;
        }

        createPlatform(level, origin);
        return origin.above(2);
    }

    private static boolean isSafeSpawn(ServerLevel level, BlockPos pos) {
        BlockPos below = pos.below();
        if (!level.getBlockState(below).isSolidRender(level, below)) {
            return false;
        }
        if (!level.getBlockState(pos).isAir()) {
            return false;
        }
        if (!level.getBlockState(pos.above()).isAir()) {
            return false;
        }
        return true;
    }

    private static int findSurfaceY(ServerLevel level, BlockPos pos) {
        int y = level.getMaxBuildHeight() - 1;
        while (y > level.getMinBuildHeight()) {
            BlockPos testPos = new BlockPos(pos.getX(), y, pos.getZ());
            if (level.getBlockState(testPos).isSolidRender(level, testPos)) {
                return y;
            }
            y--;
        }
        return level.getMinBuildHeight() + 10;
    }

    private static void createPlatform(ServerLevel level, BlockPos origin) {
        BlockState bedrock = Blocks.BEDROCK.defaultBlockState();
        BlockState air = Blocks.AIR.defaultBlockState();
        for (int x = -5; x <= 5; x++) {
            for (int z = -5; z <= 5; z++) {
                level.setBlock(origin.offset(x, 0, z), bedrock, 3);
                level.setBlock(origin.offset(x, 1, z), air, 3);
            }
        }
    }
}
