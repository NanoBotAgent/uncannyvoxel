package com.uncannyvoxel.config;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.Text;
import java.nio.file.Path;

public final class HorrorConfig {

    public boolean masterHorrorEnabled = true;
    public boolean photosensitivitySafeMode = true;
    public boolean blinkEnabled = true;
    public int blinkMinCooldownTicks = 1200; // 60s safe mode default
    public int blinkMaxCooldownTicks = 3600;
    public int blinkDurationTicks = 10; // 0.5s max
    public boolean peripheralGlitchEnabled = true;
    public float peripheralGlitchIntensity = 0.3f;
    public boolean depthOfFieldDreadEnabled = true;
    public boolean infrasonicDroneEnabled = true;
    public float infrasonicDroneVolume = 0.15f;
    public int infrasonicDroneFrequencyHz = 32; // min 32Hz
    public boolean binauralWhispersEnabled = true;
    public float binauralWhispersVolume = 0.2f;
    public boolean corruptedFamiliarityEnabled = true;
    public boolean mimicVoiceEnabled = false; // disabled by default
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
    public float lumenScalpelMaxHealthDrain = 0.5f; // max 50% health drain
    public int tetherStakeRadius = 5;

    public static HorrorConfig load() {
        Path configPath = FabricLoader.getInstance().getConfigDir().resolve("uncannyvoxel.json");
        // In real implementation: use Gson to load from file
        return new HorrorConfig();
    }

    public void save() {
        // In real implementation: use Gson to save to file
    }

    public Text getConsentText() {
        return Text.literal("Microphone-based horror features require explicit consent. " +
                "Audio is processed locally, never uploaded, and only used for short-term playback distortion. " +
                "Enable in config after reading the full privacy policy.");
    }
}