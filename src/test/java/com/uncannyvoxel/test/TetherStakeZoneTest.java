package com.uncannyvoxel.test;

import com.uncannyvoxel.horror.DreadModel;
import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TetherStakeZoneTest {

    @BeforeEach
    void clearZones() {
        DreadModel.clearCache();
    }

    @Test
    void insideRadiusReturnsTrue() {
        BlockPos center = new BlockPos(0, 64, 0);
        DreadModel.registerSafeZone(center, 5);

        assertTrue(DreadModel.isInSafeZone(new BlockPos(0, 64, 0)));
        assertTrue(DreadModel.isInSafeZone(new BlockPos(3, 64, 4)));
        assertTrue(DreadModel.isInSafeZone(new BlockPos(-5, 64, 0)));
    }

    @Test
    void outsideRadiusReturnsFalse() {
        BlockPos center = new BlockPos(0, 64, 0);
        DreadModel.registerSafeZone(center, 5);

        assertFalse(DreadModel.isInSafeZone(new BlockPos(10, 64, 0)));
        assertFalse(DreadModel.isInSafeZone(new BlockPos(0, 64, 10)));
    }

    @Test
    void nullTetherReturnsFalse() {
        assertFalse(DreadModel.isInSafeZone(new BlockPos(0, 64, 0)));
    }
}
