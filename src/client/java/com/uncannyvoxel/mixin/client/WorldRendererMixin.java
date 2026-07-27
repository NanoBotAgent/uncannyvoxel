package com.uncannyvoxel.mixin.client;

import com.uncannyvoxel.horror.UncannyRenderBridge;
import com.uncannyvoxel.horror.BlinkScheduler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.Camera;
import com.mojang.blaze3d.vertex.PoseStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class WorldRendererMixin {

    @Inject(
        method = "renderLevel",
        at = @At("TAIL")
    )
    private void uncanny$renderHorrorEffects(CallbackInfo ci) {
        Minecraft client = Minecraft.getInstance();
        UncannyRenderBridge.loadShaders(client);

        if (BlinkScheduler.isBlinkActive()) {
            float progress = BlinkScheduler.getBlinkProgress(client);
            UncannyRenderBridge.updateBlinkIntensity(progress);
        }
    }
}
