package com.uncannyvoxel.mixin.client;

import com.uncannyvoxel.horror.VantablackChunkManager;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Target: VulkanLightmapTextureManager (1.21.11 placeholder)
 * Note: Vulkan rendering may use different class names in 1.21.11
 */
@Mixin(targets = "net.minecraft.client.render.vulkan.VulkanLightmapTextureManager", require = 0)
public class VulkanLightmapDropMixin {

    @Inject(
        method = "bindLightmap",
        at = @At("HEAD"),
        cancellable = true,
        require = 0
    )
    private void uncanny$dropLightmapForVantablackChunks(Camera camera, CallbackInfo ci) {
        if (VantablackChunkManager.shouldDropLightmap(camera.getBlockPos())) {
            // In real 1.21.11 Vulkan: bind black lightmap descriptor set or cancel
            ci.cancel();
        }
    }
}