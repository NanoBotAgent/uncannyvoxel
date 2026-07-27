package com.uncannyvoxel.mixin.client;

import com.uncannyvoxel.horror.UncannyRenderBridge;
import net.minecraft.client.renderer.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public class CameraMixin {

    @Inject(
        method = "update",
        at = @At("TAIL")
    )
    private void uncanny$updateDepthOfFieldDread(CallbackInfo ci) {
    }
}
