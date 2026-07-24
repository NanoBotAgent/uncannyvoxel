package com.uncannyvoxel.test;

import com.uncannyvoxel.portal.PortalFrameValidator;
import com.uncannyvoxel.portal.ActivationDecision;
import net.minecraft.block.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class PortalFrameValidatorTest {

    @Test
    void validFramePasses() {
        BlockView world = Mockito.mock(BlockView.class);
        BlockPos center = new BlockPos(0, 0, 0);

        Mockito.when(world.getBlockState(center)).thenReturn(Blocks.TINTED_GLASS.getDefaultState());
        for (BlockPos offset : PortalFrameValidator.RING) {
            Mockito.when(world.getBlockState(center.add(offset)))
                    .thenReturn(Blocks.TINTED_GLASS.getDefaultState());
        }

        Predicate<BlockState> framePredicate = state -> state.isOf(Blocks.TINTED_GLASS);
        Predicate<BlockState> centerPredicate = state -> state.isOf(Blocks.TINTED_GLASS);

        boolean result = PortalFrameValidator.isValid(world, center, framePredicate, centerPredicate);

        assertTrue(result);
    }

    @Test
    void missingRingBlockFails() {
        BlockView world = Mockito.mock(BlockView.class);
        BlockPos center = new BlockPos(0, 0, 0);

        Mockito.when(world.getBlockState(center)).thenReturn(Blocks.TINTED_GLASS.getDefaultState());
        for (BlockPos offset : PortalFrameValidator.RING) {
            Mockito.when(world.getBlockState(center.add(offset)))
                    .thenReturn(Blocks.TINTED_GLASS.getDefaultState());
        }
        // Make one block air
        Mockito.when(world.getBlockState(center.add(PortalFrameValidator.RING[0])))
                .thenReturn(Blocks.AIR.getDefaultState());

        Predicate<BlockState> framePredicate = state -> state.isOf(Blocks.TINTED_GLASS);
        Predicate<BlockState> centerPredicate = state -> state.isOf(Blocks.TINTED_GLASS);

        boolean result = PortalFrameValidator.isValid(world, center, framePredicate, centerPredicate);

        assertFalse(result);
    }

    @Test
    void invalidCenterFails() {
        BlockView world = Mockito.mock(BlockView.class);
        BlockPos center = new BlockPos(0, 0, 0);

        Mockito.when(world.getBlockState(center)).thenReturn(Blocks.AIR.getDefaultState());

        Predicate<BlockState> framePredicate = state -> state.isOf(Blocks.TINTED_GLASS);
        Predicate<BlockState> centerPredicate = state -> state.isOf(Blocks.TINTED_GLASS);

        boolean result = PortalFrameValidator.isValid(world, center, framePredicate, centerPredicate);

        assertFalse(result);
    }
}