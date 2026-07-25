package com.uncannyvoxel.test.gametest;

import com.uncannyvoxel.registry.ModDimensions;
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class UncannyDimensionGameTest implements FabricGameTest {

    @GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE)
    public void substrateDimensionExists(TestContext context) {
        RegistryKey<World> substrateKey = ModDimensions.SUBSTRATE;

        World substrate = context.getWorld().getServer().getWorld(substrateKey);

        context.assertNotNull(substrate, "Substrate dimension should be registered and loadable");
        context.complete();
    }

    @GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE)
    public void substrateChunkGenerationDoesNotCrash(TestContext context) {
        World substrate = context.getWorld().getServer().getWorld(ModDimensions.SUBSTRATE);

        // Generate a few chunks
        BlockPos pos = new BlockPos(100, 64, 100);
        substrate.getChunk(pos);

        context.assertTrue(substrate.getChunk(pos).isReady(), "Substrate chunk should generate without crashing");
        context.complete();
    }
}