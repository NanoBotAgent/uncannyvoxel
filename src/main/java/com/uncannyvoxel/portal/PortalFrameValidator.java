package com.uncannyvoxel.portal;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import java.util.function.Predicate;

public final class PortalFrameValidator {

    public static final BlockPos[] RING = {
            new BlockPos(0, 0, -1),
            new BlockPos(1, 0, -1),
            new BlockPos(1, 0, 0),
            new BlockPos(1, 0, 1),
            new BlockPos(0, 0, 1),
            new BlockPos(-1, 0, 1),
            new BlockPos(-1, 0, 0),
            new BlockPos(-1, 0, -1)
    };

    private PortalFrameValidator() {}

    public static boolean isValid(
            BlockView world,
            BlockPos center,
            Predicate<BlockState> ringPredicate,
            Predicate<BlockState> centerPredicate
    ) {
        if (!centerPredicate.test(world.getBlockState(center))) {
            return false;
        }

        for (BlockPos offset : RING) {
            BlockPos pos = center.add(offset);
            BlockState state = world.getBlockState(pos);

            if (!ringPredicate.test(state)) {
                return false;
            }
        }

        return true;
    }
}