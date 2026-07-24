package com.uncannyvoxel.audio;

import com.uncannyvoxel.config.HorrorConfig;
import com.uncannyvoxel.horror.BlinkScheduler;
import com.uncannyvoxel.registry.ModSoundEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.sound.SoundCategory;

public final class ClientAudioDirector {

    private static long infrasonicStartTick = 0;
    private static boolean infrasonicActive = false;
    private static float currentInfrasonicVolume = 0.0f;

    private ClientAudioDirector() {}

    public static void init() {}

    public static void tick(MinecraftClient client) {
        if (!HorrorConfig.get().horrorEnabled) {
            stopAll();
            return;
        }

        ClientWorld world = client.world;
        if (world == null) return;

        tickInfrasonicDrone(client);
        tickBinauralWhispers(client);
        tickCorruptedFamiliarity(client);
    }

    private static void tickInfrasonicDrone(MinecraftClient client) {
        if (!HorrorConfig.get().infrasonicDroneEnabled) {
            stopInfrasonic();
            return;
        }

        if (!infrasonicActive && client.world.random.nextFloat() < 0.0001f) {
            startInfrasonic(client);
        }

        if (infrasonicActive) {
            long elapsed = client.world.getTime() - infrasonicStartTick;
            if (elapsed > 24000) { // 20 minutes max
                stopInfrasonic();
            } else {
                // Fade in/out
                float targetVolume = AudioSafety.clampInfrasonicVolume(HorrorConfig.get().infrasonicDroneVolume);
                float fadeSpeed = 0.005f;
                currentInfrasonicVolume = Math.min(currentInfrasonicVolume + fadeSpeed, targetVolume);

                // Play as continuous sound
                // In real implementation: use SoundInstance with looping
            }
        }
    }

    private static void startInfrasonic(MinecraftClient client) {
        infrasonicActive = true;
        infrasonicStartTick = client.world.getTime();
        currentInfrasonicVolume = 0.0f;
    }

    private static void stopInfrasonic() {
        infrasonicActive = false;
        currentInfrasonicVolume = 0.0f;
    }

    private static void tickBinauralWhispers(MinecraftClient client) {
        if (!HorrorConfig.get().binauralWhispersEnabled) return;

        if (client.player == null) return;

        // Check for nearby entities or darkness
        // In real implementation: spatial audio with panning
    }

    private static void tickCorruptedFamiliarity(MinecraftClient client) {
        if (!HorrorConfig.get().corruptedFamiliarityEnabled) return;

        // Layer uncomfortable sounds over vanilla sounds
        // e.g., chest open + teeth grinding
    }

    public static void playBlinkSound(MinecraftClient client) {
        if (client.player != null) {
            client.player.playSound(ModSoundEvents.BLINK_TRIGGER,
                    HorrorConfig.get().horrorVolume, 1.0f);
        }
    }

    public static void playWetTearingSound(MinecraftClient client) {
        if (client.player != null) {
            client.player.playSound(ModSoundEvents.WET_TEARING,
                    HorrorConfig.get().horrorVolume, 1.0f);
        }
    }

    public static void playChestSnapSound(MinecraftClient client) {
        if (client.player != null) {
            client.player.playSound(ModSoundEvents.CHEST_SNAP,
                    HorrorConfig.get().horrorVolume, 1.0f);
        }
    }

    public static void playMirrorWhisper(MinecraftClient client) {
        if (client.player != null) {
            client.player.playSound(ModSoundEvents.MIRROR_WHISPER,
                    HorrorConfig.get().horrorVolume, 0.8f);
        }
    }

    public static void playSlidingSkinSound(MinecraftClient client) {
        if (client.player != null) {
            client.player.playSound(ModSoundEvents.SLIDING_SKIN,
                    HorrorConfig.get().horrorVolume, 1.0f);
        }
    }

    private static void stopAll() {
        stopInfrasonic();
    }
}