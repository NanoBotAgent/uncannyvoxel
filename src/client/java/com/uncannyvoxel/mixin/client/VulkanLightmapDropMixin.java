package com.uncannyvoxel.mixin.client;

import com.uncannyvoxel.horror.VantablackChunkManager;
import net.minecraft.client.renderer.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// @Mixin(targets = "net.minecraft.client.renderer.VulkanLightmapTextureManager", require = 0) // REMOVED: Does not exist in MC 26.2
public class VulkanLightmapDropMixin {

    @Inject(
        method = "bindLightmap",
        at = @At("HEAD"),
        cancellable = true,
        require = 0
    )
    private void uncanny$dropLightmapForVantablackChunks(Camera camera, CallbackInfo ci) {
        if (VantablackChunkManager.shouldDropLightmap(camera.getBlockPos())) {
            ci.cancel();
        }
    }
}
