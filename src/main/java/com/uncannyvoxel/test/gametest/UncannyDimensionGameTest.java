package com.uncannyvoxel.test.gametest;

import com.uncannyvoxel.registry.ModDimensions;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;

public class UncannyDimensionGameTest {

    @GameTest(structure = "fabric-gametest-api-v1:empty")
    public void substrateDimensionExists(GameTestHelper helper) {
        ServerLevel overworld = helper.getLevel();
        ServerLevel substrate = overworld.getServer().getLevel(ModDimensions.SUBSTRATE);

        helper.assertTrue(substrate != null, "Substrate dimension should be registered and loadable");
        helper.succeed();
    }

    @GameTest(structure = "fabric-gametest-api-v1:empty")
    public void substrateChunkGenerationDoesNotCrash(GameTestHelper helper) {
        ServerLevel overworld = helper.getLevel();
        ServerLevel substrate = overworld.getServer().getLevel(ModDimensions.SUBSTRATE);

        if (substrate == null) {
            helper.fail("Substrate dimension not loaded");
            return;
        }

        BlockPos pos = new BlockPos(100, 64, 100);
        substrate.getChunk(pos);

        helper.succeed();
    }
}
