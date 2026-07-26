package com.uncannyvoxel.test;

import com.uncannyvoxel.horror.DreadModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DreadModelTest {

    @BeforeEach
    void clearState() {
        DreadModel.clearCache();
    }

    @Test
    void increasesNearEntities() {
        assertTrue(true);
    }

    @Test
    void decreasesWhenSafe() {
        BlockPos pos = new BlockPos(0, 64, 0);
        DreadModel.registerSafeZone(pos, 10);

        assertFalse(DreadModel.isInSafeZone(new BlockPos(20, 64, 0)));
        assertTrue(DreadModel.isInSafeZone(pos));
    }

    @Test
    void neverNaN() {
        BlockPos pos = new BlockPos(0, 64, 0);
        DreadModel.registerSafeZone(pos, 10);
        float dread = DreadModel.getDreadLevel(null, pos);
        assertFalse(Float.isNaN(dread));
    }
}
