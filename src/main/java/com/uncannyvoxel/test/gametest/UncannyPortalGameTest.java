package com.uncannyvoxel.test.gametest;

import com.uncannyvoxel.registry.ModBlocks;
import com.uncannyvoxel.registry.ModTags;
import com.uncannyvoxel.portal.PortalFrameValidator;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class UncannyPortalGameTest {

    @GameTest(structure = "fabric-gametest-api-v1:empty")
    public void portalFrameValidates(GameTestHelper helper) {
        BlockPos center = new BlockPos(1, 2, 1);

        helper.setBlock(center, ModBlocks.SULFUR_GLASS_MIRROR.defaultBlockState());
        for (BlockPos offset : PortalFrameValidator.RING) {
            helper.setBlock(center.offset(offset), Blocks.TINTED_GLASS.defaultBlockState());
        }

        boolean valid = PortalFrameValidator.isValid(
                helper.getLevel(),
                center,
                state -> state.is(ModTags.SUBSTRATE_FRAME),
                state -> state.is(ModBlocks.SULFUR_GLASS_MIRROR)
        );

        helper.assertTrue(valid, "Portal frame should be valid with sulfur glass mirror center and tinted glass ring");
        helper.succeed();
    }

    @GameTest(structure = "fabric-gametest-api-v1:empty")
    public void portalInvalidFrameFails(GameTestHelper helper) {
        BlockPos center = new BlockPos(1, 2, 1);

        helper.setBlock(center, ModBlocks.SULFUR_GLASS_MIRROR.defaultBlockState());
        helper.setBlock(center.offset(PortalFrameValidator.RING[0]), Blocks.AIR.defaultBlockState());

        boolean valid = PortalFrameValidator.isValid(
                helper.getLevel(),
                center,
                state -> state.is(ModTags.SUBSTRATE_FRAME),
                state -> state.is(ModBlocks.SULFUR_GLASS_MIRROR)
        );

        helper.assertFalse(valid, "Portal frame should be invalid with missing ring block");
        helper.succeed();
    }
}
