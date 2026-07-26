package com.uncannyvoxel.audio;

import com.uncannyvoxel.config.HorrorConfig;
import java.util.Random;

public class BlinkScheduler {
    private static final Random RANDOM = new Random();

    private long lastBlink = 0;
    private boolean blinking = false;
    private int blinkDuration = 0;
    private int blinkCooldown = 0;

    public void tick() {
        if (!HorrorConfig.isHorrorEnabled() || !HorrorConfig.get().blinkEffect) {
            blinking = false;
            return;
        }

        long now = System.currentTimeMillis();

        if (blinking) {
            blinkDuration--;
            if (blinkDuration <= 0) {
                blinking = false;
                scheduleNextBlink();
            }
        } else {
            blinkCooldown--;
            if (blinkCooldown <= 0 && now >= lastBlink) {
                triggerBlink();
            }
        }
    }

    private void triggerBlink() {
        blinking = true;
        int maxDuration = HorrorConfig.getMaxBlinkDuration();
        blinkDuration = RANDOM.nextInt(maxDuration) + 1;
        blinkCooldown = HorrorConfig.getBlinkCooldown() + RANDOM.nextInt(HorrorConfig.getBlinkCooldown() / 2);
        lastBlink = System.currentTimeMillis();
    }

    private void scheduleNextBlink() {
        int cooldown = HorrorConfig.getBlinkCooldown();
        lastBlink = System.currentTimeMillis() + cooldown * 50L;
        blinkCooldown = cooldown + RANDOM.nextInt(cooldown / 2);
    }

    public boolean isBlinking() {
        return blinking;
    }

    public float getBlinkProgress() {
        if (!blinking) return 0f;
        int maxDuration = HorrorConfig.getMaxBlinkDuration();
        return 1f - (float) blinkDuration / maxDuration;
    }

    public void forceBlink() {
        if (!blinking) triggerBlink();
    }

    public void reset() {
        blinking = false;
        blinkDuration = 0;
        blinkCooldown = 0;
        scheduleNextBlink();
    }
}
