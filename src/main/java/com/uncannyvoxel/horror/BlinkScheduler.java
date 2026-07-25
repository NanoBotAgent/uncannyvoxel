package com.uncannyvoxel.horror;

import com.uncannyvoxel.config.HorrorConfig;
import net.minecraft.client.MinecraftClient;

public final class BlinkScheduler {

    private static long nextBlinkTick = -1;
    private static long lastBlinkTick = -1;
    private static boolean blinkActive = false;
    private static int blinkDurationTicks = 0;

    private BlinkScheduler() {}

    public static void init() {
        nextBlinkTick = -1;
        lastBlinkTick = -1;
        blinkActive = false;
        blinkDurationTicks = 0;
    }

    public static void tick(MinecraftClient client) {
        if (!HorrorConfig.get().horrorEnabled || !HorrorConfig.get().blinkEnabled) {
            return;
        }

        long currentTick = client.world != null ? client.world.getTime() : 0;

        if (blinkActive) {
            if (currentTick >= lastBlinkTick + blinkDurationTicks) {
                endBlink(client);
            }
            return;
        }

        if (nextBlinkTick == -1) {
            scheduleNextBlink(client);
            return;
        }

        if (currentTick >= nextBlinkTick) {
            triggerBlink(client);
        }
    }

    private static void scheduleNextBlink(MinecraftClient client) {
        HorrorConfig config = HorrorConfig.get();
        int minCooldown = config.photosensitivitySafeMode ? 2400 : config.blinkMinCooldownTicks;
        int maxCooldown = config.blinkMaxCooldownTicks;

        int cooldown = minCooldown + client.player.getRandom().nextInt(maxCooldown - minCooldown + 1);
        nextBlinkTick = (client.world != null ? client.world.getTime() : 0) + cooldown;
    }

    private static void triggerBlink(MinecraftClient client) {
        HorrorConfig config = HorrorConfig.get();
        blinkDurationTicks = Math.min(config.blinkDurationTicks, config.photosensitivitySafeMode ? 10 : config.blinkDurationTicks);
        lastBlinkTick = client.world != null ? client.world.getTime() : 0;
        blinkActive = true;
        nextBlinkTick = -1;

        UncannyRenderBridge.triggerBlink(client);
    }

    private static void endBlink(MinecraftClient client) {
        blinkActive = false;
        UncannyRenderBridge.endBlink(client);
        scheduleNextBlink(client);
    }

    public static boolean isBlinkActive() {
        return blinkActive;
    }

    public static float getBlinkProgress(MinecraftClient client) {
        if (!blinkActive) return 0.0f;
        long currentTick = client.world != null ? client.world.getTime() : 0;
        return Math.min(1.0f, (float)(currentTick - lastBlinkTick) / blinkDurationTicks);
    }

    public static void reset() {
        nextBlinkTick = -1;
        lastBlinkTick = -1;
        blinkActive = false;
        blinkDurationTicks = 0;
    }
}
