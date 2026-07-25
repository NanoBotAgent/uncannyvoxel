package com.uncannyvoxel.test.gametest;

import com.uncannyvoxel.registry.ModDimensions;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class UncannyDimensionGameTest {

    @GameTest(templateName = "empty")
    public void substrateDimensionExists(TestContext context) {
        var substrateKey = ModDimensions.SUBSTRATE;

        World substrate = context.getWorld().getServer().getWorld(substrateKey);

        context.assertNotNull(substrate, "Substrate dimension should be registered and loadable");
        context.complete();
    }

    @GameTest(templateName = "empty")
    public void substrateChunkGenerationDoesNotCrash(TestContext context) {
        World substrate = context.getWorld().getServer().getWorld(ModDimensions.SUBSTRATE);

        // Generate a few chunks
        BlockPos pos = new BlockPos(100, 64, 100);
        substrate.getChunk(pos);

        context.assertTrue(substrate.getChunk(pos).isReady(), "Substrate chunk should generate without crashing");
        context.complete();
    }
}