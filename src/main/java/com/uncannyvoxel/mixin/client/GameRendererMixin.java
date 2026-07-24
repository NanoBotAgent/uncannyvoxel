package com.uncannyvoxel.mixin.client;

import com.uncannyvoxel.horror.BlinkScheduler;
import com.uncannyvoxel.horror.UncannyRenderBridge;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Inject(
        method = "render",
        at = @At("TAIL")
    )
    private void uncanny$renderBlinkOverlay(CallbackInfo ci) {
        UncannyRenderBridge.loadShaders(net.minecraft.client.MinecraftClient.getInstance());
        
        BlinkScheduler scheduler = BlinkScheduler.getInstance();
        if (scheduler.isBlinkActive()) {
            float progress = scheduler.getBlinkProgress(net.minecraft.client.MinecraftClient.getInstance());
            UncannyRenderBridge.updateBlinkIntensity(progress);
        }
    }
}