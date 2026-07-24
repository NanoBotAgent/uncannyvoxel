package com.uncannyvoxel.horror;

import com.uncannyvoxel.config.HorrorConfig;
import net.minecraft.client.MinecraftClient;

public final class BlinkScheduler {

    private long nextBlinkTick = -1;
    private long lastBlinkTick = -1;
    private boolean blinkActive = false;
    private int blinkDurationTicks = 0;

    public void tick(MinecraftClient client) {
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

    private void scheduleNextBlink(MinecraftClient client) {
        HorrorConfig config = HorrorConfig.get();
        int minCooldown = config.photosensitivitySafeMode ? 2400 : config.blinkMinCooldownTicks;
        int maxCooldown = config.blinkMaxCooldownTicks;

        int cooldown = minCooldown + client.player.getRandom().nextInt(maxCooldown - minCooldown + 1);
        nextBlinkTick = (client.world != null ? client.world.getTime() : 0) + cooldown;
    }

    private void triggerBlink(MinecraftClient client) {
        HorrorConfig config = HorrorConfig.get();
        blinkDurationTicks = Math.min(config.blinkDurationTicks, config.photosensitivitySafeMode ? 10 : config.blinkDurationTicks);
        lastBlinkTick = client.world != null ? client.world.getTime() : 0;
        blinkActive = true;
        nextBlinkTick = -1;

        // Trigger shader and sound via render bridge
        UncannyRenderBridge.triggerBlink(client);
    }

    private void endBlink(MinecraftClient client) {
        blinkActive = false;
        UncannyRenderBridge.endBlink(client);
        scheduleNextBlink(client);
    }

    public boolean isBlinkActive() {
        return blinkActive;
    }

    public float getBlinkProgress(MinecraftClient client) {
        if (!blinkActive) return 0.0f;
        long currentTick = client.world != null ? client.world.getTime() : 0;
        return Math.min(1.0f, (float)(currentTick - lastBlinkTick) / blinkDurationTicks);
    }

    public void reset() {
        nextBlinkTick = -1;
        lastBlinkTick = -1;
        blinkActive = false;
        blinkDurationTicks = 0;
    }
}