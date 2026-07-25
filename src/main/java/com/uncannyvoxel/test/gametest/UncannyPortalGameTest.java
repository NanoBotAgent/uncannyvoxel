package com.uncannyvoxel.test.gametest;

import com.uncannyvoxel.registry.ModDimensions;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class UncannyPortalGameTest {

    @GameTest(templateName = "empty")
    public void portalFrameValidates(TestContext context) {
        BlockPos center = new BlockPos(1, 2, 1);

        // Build frame: tinted glass ring + sulfur glass mirror center
        context.setBlockState(center, com.uncannyvoxel.registry.ModBlocks.SULFUR_GLASS_MIRROR.getDefaultState());
        for (BlockPos offset : com.uncannyvoxel.portal.PortalFrameValidator.RING) {
            context.setBlockState(center.add(offset), net.minecraft.block.Blocks.TINTED_GLASS.getDefaultState());
        }

        // Validate
        boolean valid = com.uncannyvoxel.portal.PortalFrameValidator.isValid(
                context.getWorld(),
                center,
                state -> state.isIn(com.uncannyvoxel.registry.ModTags.SUBSTRATE_FRAME),
                state -> state.isOf(com.uncannyvoxel.registry.ModBlocks.SULFUR_GLASS_MIRROR)
        );

        context.assertTrue(valid, "Portal frame should be valid with sulfur glass mirror center and tinted glass ring");
        context.complete();
    }

    @GameTest(templateName = "empty")
    public void portalInvalidFrameFails(TestContext context) {
        BlockPos center = new BlockPos(1, 2, 1);

        // Build incomplete frame
        context.setBlockState(center, com.uncannyvoxel.registry.ModBlocks.SULFUR_GLASS_MIRROR.getDefaultState());
        context.setBlockState(center.add(com.uncannyvoxel.portal.PortalFrameValidator.RING[0]),
                net.minecraft.block.Blocks.AIR.getDefaultState());

        boolean valid = com.uncannyvoxel.portal.PortalFrameValidator.isValid(
                context.getWorld(),
                center,
                state -> state.isIn(com.uncannyvoxel.registry.ModTags.SUBSTRATE_FRAME),
                state -> state.isOf(com.uncannyvoxel.registry.ModBlocks.SULFUR_GLASS_MIRROR)
        );

        context.assertFalse(valid, "Portal frame should be invalid with missing ring block");
        context.complete();
    }
}