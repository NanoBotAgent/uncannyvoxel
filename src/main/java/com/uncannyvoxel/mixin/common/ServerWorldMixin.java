package com.uncannyvoxel.mixin.common;

import com.uncannyvoxel.horror.VantablackChunkManager;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLevel.class)
public class ServerWorldMixin {

    @Inject(
        method = "tickChunk",
        at = @At("TAIL")
    )
    private void uncanny$updateVantablackChunks(CallbackInfo ci) {
    }

    @Inject(
        method = "onBlockAdded",
        at = @At("TAIL")
    )
    private void uncanny$onSulfurGeyserPlaced(BlockPos pos, BlockState state, CallbackInfo ci) {
        if (state.is(Blocks.BEDROCK)) {
            VantablackChunkManager.markSulfurGeyserArea(pos);
        }
    }
}
