package com.uncannyvoxel.mixin.client;

import com.uncannyvoxel.horror.VantablackChunkManager;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Target: VulkanChunkRenderBackend (26.2 placeholder)
 * Confirmed class name needed from 26.2 mappings.
 */
@Mixin(targets = "net.minecraft.client.render.vulkan.VulkanChunkRenderBackend", require = 0)
public class VulkanChunkDrawMixin {

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