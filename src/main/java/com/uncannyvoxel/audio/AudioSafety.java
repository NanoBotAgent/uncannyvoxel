package com.uncannyvoxel.audio;

public final class AudioSafety {

    private static final float MAX_HORROR_VOLUME = 0.5f;
    private static final float MAX_INFRASONIC_VOLUME = 0.2f;
    private static final float MIN_INFRASONIC_FREQ = 32.0f;
    private static final float MAX_INFRASONIC_FREQ = 80.0f;

    private AudioSafety() {}

    public static float clampHorrorVolume(float volume) {
        return Math.min(Math.max(volume, 0.0f), MAX_HORROR_VOLUME);
    }

    public static float clampInfrasonicVolume(float volume) {
        return Math.min(Math.max(volume, 0.0f), MAX_INFRASONIC_VOLUME);
    }

    public static float clampInfrasonicFrequency(float frequency) {
        return Math.min(Math.max(frequency, MIN_INFRASONIC_FREQ), MAX_INFRASONIC_FREQ);
    }

    public static float normalizeDread(float dread) {
        return Math.min(Math.max(dread, 0.0f), 1.0f);
    }
}