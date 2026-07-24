package com.uncannyvoxel.mixin.client;

import com.uncannyvoxel.horror.UncannyRenderBridge;
import com.uncannyvoxel.horror.BlinkScheduler;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @Inject(
        method = "render",
        at = @At("TAIL")
    )
    private void uncanny$renderHorrorEffects(Camera camera, CallbackInfo ci) {
        UncannyRenderBridge.loadShaders(net.minecraft.client.MinecraftClient.getInstance());
        
        // Update blink shader
        BlinkScheduler scheduler = BlinkScheduler.getInstance();
        if (scheduler.isBlinkActive()) {
            float progress = scheduler.getBlinkProgress(net.minecraft.client.MinecraftClient.getInstance());
            UncannyRenderBridge.updateBlinkIntensity(progress);
            UncannyRenderBridge.renderBlinkOverlay(new MatrixStack(), camera, 0);
        }
        
        // Peripheral glitch
        // Update based on entity proximity
    }
}