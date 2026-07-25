package com.uncannyvoxel.mixin.client;

import com.uncannyvoxel.horror.UncannyRenderBridge;
import com.uncannyvoxel.horror.BlinkScheduler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
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
        MinecraftClient client = MinecraftClient.getInstance();
        UncannyRenderBridge.loadShaders(client);

        if (BlinkScheduler.isBlinkActive()) {
            float progress = BlinkScheduler.getBlinkProgress(client);
            UncannyRenderBridge.updateBlinkIntensity(progress);
            UncannyRenderBridge.renderBlinkOverlay(new MatrixStack(), camera, 0);
        }
    }
}
