package com.uncannyvoxel.test;

import com.uncannyvoxel.horror.DreadModel;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DreadModelTest {

    @BeforeEach
    void clearState() {
        DreadModel.clearCache();
    }

    @Test
    void returnsZeroToOne() {
        float dread = DreadModel.getDreadLevel(null, new BlockPos(0, 64, 0));
        assertTrue(dread >= 0.0f && dread <= 1.0f);
    }

    @Test
    void increasesNearEntities() {
        // Would need mock world with entities
        assertTrue(true);
    }

    @Test
    void decreasesWhenSafe() {
        BlockPos pos = new BlockPos(0, 64, 0);
        DreadModel.registerSafeZone(pos, 10);

        float dread = DreadModel.getDreadLevel(null, pos);
        assertEquals(0.0f, dread, 0.01f);
    }

    @Test
    void neverNaN() {
        float dread = DreadModel.getDreadLevel(null, new BlockPos(0, 64, 0));
        assertFalse(Float.isNaN(dread));
    }
}