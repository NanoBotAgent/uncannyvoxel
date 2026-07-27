package com.uncannyvoxel.audio;

import com.uncannyvoxel.config.HorrorConfig;
import com.uncannyvoxel.horror.BlinkScheduler;
import com.uncannyvoxel.registry.ModSoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.sounds.SoundSource;

public final class ClientAudioDirector {

    private static long infrasonicStartTick = 0;
    private static boolean infrasonicActive = false;
    private static float currentInfrasonicVolume = 0.0f;

    private ClientAudioDirector() {}

    public static void init() {}

    public static void tick(Minecraft client) {
        if (!HorrorConfig.get().horrorEnabled) {
            stopAll();
            return;
        }

        ClientLevel level = client.level;
        if (level == null) return;

        tickInfrasonicDrone(client);
        tickBinauralWhispers(client);
        tickCorruptedFamiliarity(client);
    }

    private static void tickInfrasonicDrone(Minecraft client) {
        if (!HorrorConfig.get().infrasonicDroneEnabled) {
            stopInfrasonic();
            return;
        }

        if (!infrasonicActive && client.level.getRandom().nextFloat() < 0.0001f) {
            startInfrasonic(client);
        }

        if (infrasonicActive) {
            long elapsed = client.level.getGameTime() - infrasonicStartTick;
            if (elapsed > 24000) {
                stopInfrasonic();
            } else {
                float targetVolume = AudioSafety.clampInfrasonicVolume(HorrorConfig.get().infrasonicDroneVolume);
                float fadeSpeed = 0.005f;
                currentInfrasonicVolume = Math.min(currentInfrasonicVolume + fadeSpeed, targetVolume);
            }
        }
    }

    private static void startInfrasonic(Minecraft client) {
        infrasonicActive = true;
        infrasonicStartTick = client.level.getGameTime();
        currentInfrasonicVolume = 0.0f;
    }

    private static void stopInfrasonic() {
        infrasonicActive = false;
        currentInfrasonicVolume = 0.0f;
    }

    private static void tickBinauralWhispers(Minecraft client) {
        if (!HorrorConfig.get().binauralWhispersEnabled) return;
        if (client.player == null) return;
    }

    private static void tickCorruptedFamiliarity(Minecraft client) {
        if (!HorrorConfig.get().corruptedFamiliarityEnabled) return;
    }

    public static void playBlinkSound(Minecraft client) {
        if (client.player != null) {
            client.player.playSound(ModSoundEvents.BLINK_TRIGGER,
                    HorrorConfig.get().horrorVolume, 1.0f);
        }
    }

    public static void playWetTearingSound(Minecraft client) {
        if (client.player != null) {
            client.player.playSound(ModSoundEvents.WET_TEARING,
                    HorrorConfig.get().horrorVolume, 1.0f);
        }
    }

    public static void playChestSnapSound(Minecraft client) {
        if (client.player != null) {
            client.player.playSound(ModSoundEvents.CHEST_SNAP,
                    HorrorConfig.get().horrorVolume, 1.0f);
        }
    }

    public static void playMirrorWhisper(Minecraft client) {
        if (client.player != null) {
            client.player.playSound(ModSoundEvents.MIRROR_WHISPER,
                    HorrorConfig.get().horrorVolume, 0.8f);
        }
    }

    public static void playSlidingSkinSound(Minecraft client) {
        if (client.player != null) {
            client.player.playSound(ModSoundEvents.SLIDING_SKIN,
                    HorrorConfig.get().horrorVolume, 1.0f);
        }
    }

    private static void stopAll() {
        stopInfrasonic();
    }
}
