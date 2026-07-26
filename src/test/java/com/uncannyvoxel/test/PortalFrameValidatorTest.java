package com.uncannyvoxel.test;

import com.uncannyvoxel.portal.PortalFrameValidator;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.BlockGetter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class PortalFrameValidatorTest {

    @Test
    void validFramePasses() {
        BlockGetter world = Mockito.mock(BlockGetter.class);
        BlockPos center = new BlockPos(0, 0, 0);

        Mockito.when(world.getBlockState(center)).thenReturn(Blocks.TINTED_GLASS.defaultBlockState());
        for (BlockPos offset : PortalFrameValidator.RING) {
            Mockito.when(world.getBlockState(center.offset(offset.getX(), offset.getY(), offset.getZ())))
                    .thenReturn(Blocks.TINTED_GLASS.defaultBlockState());
        }

        Predicate<BlockState> framePredicate = state -> state.is(Blocks.TINTED_GLASS);
        Predicate<BlockState> centerPredicate = state -> state.is(Blocks.TINTED_GLASS);

        boolean result = PortalFrameValidator.isValid(world, center, framePredicate, centerPredicate);

        assertTrue(result);
    }

    @Test
    void missingRingBlockFails() {
        BlockGetter world = Mockito.mock(BlockGetter.class);
        BlockPos center = new BlockPos(0, 0, 0);

        Mockito.when(world.getBlockState(center)).thenReturn(Blocks.TINTED_GLASS.defaultBlockState());
        for (BlockPos offset : PortalFrameValidator.RING) {
            Mockito.when(world.getBlockState(center.offset(offset.getX(), offset.getY(), offset.getZ())))
                    .thenReturn(Blocks.TINTED_GLASS.defaultBlockState());
        }
        Mockito.when(world.getBlockState(center.offset(PortalFrameValidator.RING[0].getX(), PortalFrameValidator.RING[0].getY(), PortalFrameValidator.RING[0].getZ())))
                .thenReturn(Blocks.AIR.defaultBlockState());

        Predicate<BlockState> framePredicate = state -> state.is(Blocks.TINTED_GLASS);
        Predicate<BlockState> centerPredicate = state -> state.is(Blocks.TINTED_GLASS);

        boolean result = PortalFrameValidator.isValid(world, center, framePredicate, centerPredicate);

        assertFalse(result);
    }

    @Test
    void invalidCenterFails() {
        BlockGetter world = Mockito.mock(BlockGetter.class);
        BlockPos center = new BlockPos(0, 0, 0);

        Mockito.when(world.getBlockState(center)).thenReturn(Blocks.AIR.defaultBlockState());

        Predicate<BlockState> framePredicate = state -> state.is(Blocks.TINTED_GLASS);
        Predicate<BlockState> centerPredicate = state -> state.is(Blocks.TINTED_GLASS);

        boolean result = PortalFrameValidator.isValid(world, center, framePredicate, centerPredicate);

        assertFalse(result);
    }
}
