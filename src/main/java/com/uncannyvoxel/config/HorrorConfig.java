package com.uncannyvoxel.config;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import java.nio.file.Path;

public final class HorrorConfig {

    public boolean masterHorrorEnabled = true;
    public boolean photosensitivitySafeMode = true;
    public boolean blinkEnabled = true;
    public int blinkMinCooldownTicks = 1200;
    public int blinkMaxCooldownTicks = 3600;
    public int blinkDurationTicks = 10;
    public boolean peripheralGlitchEnabled = true;
    public float peripheralGlitchIntensity = 0.3f;
    public boolean depthOfFieldDreadEnabled = true;
    public boolean infrasonicDroneEnabled = true;
    public float infrasonicDroneVolume = 0.15f;
    public int infrasonicDroneFrequencyHz = 32;
    public boolean binauralWhispersEnabled = true;
    public float binauralWhispersVolume = 0.2f;
    public boolean corruptedFamiliarityEnabled = true;
    public boolean mimicVoiceEnabled = false;
    public boolean micCaptureEnabled = false;
    public int micConsentVersion = 1;
    public boolean friendListCorruptionEnabled = true;
    public boolean useFictionalFriendNames = false;
    public boolean inventoryRotEnabled = true;
    public boolean mirrorTrapEnabled = true;
    public boolean chestMimicEnabled = true;
    public boolean bedParalysisEnabled = true;
    public int bedParalysisMaxDurationMinutes = 10;
    public String bedParalysisCancelKey = "key.escape";
    public boolean vantablackChunksEnabled = true;
    public int vantablackRangeChunks = 3;
    public float lumenScalpelMaxHealthDrain = 0.5f;
    public int tetherStakeRadius = 5;

    public boolean horrorEnabled = true;
    public boolean audioEnabled = true;
    public float horrorVolume = 1.0f;
    public boolean slidingSkin = true;
    public boolean blinkEffect = true;
    public float maxCorruptionIntensity = 1.0f;
    public float peripheralGlitchChance = 0.3f;
    public boolean bedParalysis = true;

    private static HorrorConfig INSTANCE;

    public static HorrorConfig get() {
        if (INSTANCE == null) {
            INSTANCE = load();
        }
        return INSTANCE;
    }

    public static boolean isHorrorEnabled() {
        return get().masterHorrorEnabled;
    }

    public static boolean isAudioEnabled() {
        return get().audioEnabled;
    }

    public static boolean isMicEnabled() {
        return get().micCaptureEnabled;
    }

    public static int getBlinkCooldown() {
        return get().blinkMinCooldownTicks;
    }

    public static int getMaxBlinkDuration() {
        return get().blinkDurationTicks;
    }

    public static float getMasterVolume() {
        return get().horrorVolume;
    }

    public static float getGlitchVolume() {
        return get().peripheralGlitchIntensity;
    }

    public static HorrorConfig load() {
        Path configPath = FabricLoader.getInstance().getConfigDir().resolve("uncannyvoxel.json");
        return new HorrorConfig();
    }

    public void save() {
    }

    public Component getConsentText() {
        return Component.literal("Microphone-based horror features require explicit consent. " +
                "Audio is processed locally, never uploaded, and only used for short-term playback distortion. " +
                "Enable in config after reading the full privacy policy.");
    }
}
