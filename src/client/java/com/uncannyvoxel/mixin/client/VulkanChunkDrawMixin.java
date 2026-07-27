package com.uncannyvoxel.mixin.client;

import com.uncannyvoxel.horror.VantablackChunkManager;
import net.minecraft.client.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// @Mixin("net.minecraft.client.renderer.VulkanChunkRenderBackend") // REMOVED: Does not exist in MC 26.2
// public class VulkanChunkDrawMixin {

    @Inject(
        method = "drawChunk",
        at = @At("HEAD"),
        cancellable = true,
        require = 0
    )
    private void uncanny$skipVantablackChunk(Camera camera, CallbackInfo ci) {
        if (VantablackChunkManager.shouldDropLightmap(camera.getBlockPos())) {
            ci.cancel();
        }
    }
}
