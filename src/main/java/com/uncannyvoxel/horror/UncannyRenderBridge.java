package com.uncannyvoxel.horror;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public final class UncannyRenderBridge {

    private static ShaderProgram blinkShader;
    private static ShaderProgram peripheralGlitchShader;
    private static ShaderProgram depthOfFieldDreadShader;
    private static boolean shadersLoaded = false;

    private static float blinkIntensity = 0.0f;
    private static float peripheralIntensity = 0.0f;
    private static float dreadIntensity = 0.0f;

    private static final Identifier BLINK_SHADER = Identifier.of("uncannyvoxel", "blink");
    private static final Identifier PERIPHERAL_SHADER = Identifier.of("uncannyvoxel", "peripheral_glitch");
    private static final Identifier DREAD_SHADER = Identifier.of("uncannyvoxel", "depth_of_field_dread");

    public static void loadShaders(MinecraftClient client) {
        if (shadersLoaded) return;

        try {
            blinkShader = client.getShaderLoader().getOrCreateProgram(BLINK_SHADER);
            peripheralGlitchShader = client.getShaderLoader().getOrCreateProgram(PERIPHERAL_SHADER);
            depthOfFieldDreadShader = client.getShaderLoader().getOrCreateProgram(DREAD_SHADER);
            shadersLoaded = true;
        } catch (Exception e) {
            // Shaders not available, will use fallback
        }
    }

    public static void triggerBlink(MinecraftClient client) {
        blinkIntensity = 1.0f;
        playBlinkSound(client);
    }

    public static void endBlink(MinecraftClient client) {
        blinkIntensity = 0.0f;
    }

    public static void updateBlinkIntensity(float progress) {
        // Ease in/out for smooth blink
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

    public static void renderBlinkOverlay(MatrixStack matrices, Camera camera, float tickDelta) {
        if (blinkIntensity <= 0.0f) return;

        if (blinkShader != null) {
            RenderSystem.setShader(blinkShader);
            blinkShader.setUniform("u_intensity", blinkIntensity);
            // Render full-screen quad
            renderFullscreenQuad(matrices);
            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        } else {
            // Fallback: render black overlay
            renderBlackOverlay(matrices, blinkIntensity);
        }
    }

    public static void renderPeripheralGlitch(MatrixStack matrices, Camera camera, float tickDelta) {
        if (peripheralIntensity <= 0.0f || peripheralGlitchShader == null) return;

        RenderSystem.setShader(peripheralGlitchShader);
        peripheralGlitchShader.setUniform("u_intensity", peripheralIntensity);
        peripheralGlitchShader.setUniform("u_time", camera.getTickDelta());
        renderFullscreenQuad(matrices);
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
    }

    public static void renderDepthOfFieldDread(MatrixStack matrices, Camera camera, float tickDelta) {
        if (dreadIntensity <= 0.0f || depthOfFieldDreadShader == null) return;

        RenderSystem.setShader(depthOfFieldDreadShader);
        depthOfFieldDreadShader.setUniform("u_intensity", dreadIntensity);
        depthOfFieldDreadShader.setUniform("u_camera_pos", camera.getPos());
        renderFullscreenQuad(matrices);
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
    }

    private static void renderFullscreenQuad(MatrixStack matrices) {
        // Implementation uses Tessellator to draw full-screen quad
        // Placeholder for actual implementation
    }

    private static void renderBlackOverlay(MatrixStack matrices, float alpha) {
        // Fallback: draw black rect over screen
    }

    private static void playBlinkSound(MinecraftClient client) {
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