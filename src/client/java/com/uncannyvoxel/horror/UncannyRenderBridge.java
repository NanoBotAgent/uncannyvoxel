package com.uncannyvoxel.horror;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.Camera;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.Identifier;

public final class UncannyRenderBridge {

    private static ShaderInstance blinkShader;
    private static ShaderInstance peripheralGlitchShader;
    private static ShaderInstance depthOfFieldDreadShader;
    private static boolean shadersLoaded = false;

    private static float blinkIntensity = 0.0f;
    private static float peripheralIntensity = 0.0f;
    private static float dreadIntensity = 0.0f;

    private static final Identifier BLINK_SHADER = Identifier.of("uncannyvoxel", "blink");
    private static final Identifier PERIPHERAL_SHADER = Identifier.of("uncannyvoxel", "peripheral_glitch");
    private static final Identifier DREAD_SHADER = Identifier.of("uncannyvoxel", "depth_of_field_dread");

    public static void loadShaders(Minecraft client) {
        if (shadersLoaded) return;

        try {
            shadersLoaded = true;
        } catch (Exception e) {
        }
    }

    public static void triggerBlink(Minecraft client) {
        blinkIntensity = 1.0f;
        playBlinkSound(client);
    }

    public static void endBlink(Minecraft client) {
        blinkIntensity = 0.0f;
    }

    public static void updateBlinkIntensity(float progress) {
        if (progress < 0.5f) {
            blinkIntensity = progress * 2.0f;
        } else {
            blinkIntensity = (1.0f - progress) * 2.0f;
        }
    }

    public static void setPeripheralIntensity(float intensity) {
        peripheralIntensity = Math.max(0.0f, Math.min(1.0f, intensity));
    }

    public static void setDreadIntensity(float intensity) {
        dreadIntensity = Math.max(0.0f, Math.min(1.0f, intensity));
    }

    public static void renderBlinkOverlay(PoseStack poseStack, Camera camera, float tickDelta) {
        if (blinkIntensity <= 0.0f) return;

        renderBlackOverlay(poseStack, blinkIntensity);
    }

    public static void renderPeripheralGlitch(PoseStack poseStack, Camera camera, float tickDelta) {
        if (peripheralIntensity <= 0.0f) return;
    }

    public static void renderDepthOfFieldDread(PoseStack poseStack, Camera camera, float tickDelta) {
        if (dreadIntensity <= 0.0f) return;
    }

    private static void renderFullscreenQuad(PoseStack poseStack) {
    }

    private static void renderBlackOverlay(PoseStack poseStack, float alpha) {
    }

    private static void playBlinkSound(Minecraft client) {
        if (client.player != null) {
            client.player.playSound(com.uncannyvoxel.registry.ModSoundEvents.BLINK_TRIGGER,
                    HorrorConfig.get().horrorVolume, 1.0f);
        }
    }

    public static void cleanup() {
        blinkShader = null;
        peripheralGlitchShader = null;
        depthOfFieldDreadShader = null;
        shadersLoaded = false;
    }
}
