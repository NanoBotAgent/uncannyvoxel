package com.uncannyvoxel.audio;

import com.uncannyvoxel.config.HorrorConfig;
import com.uncannyvoxel.registry.ModSoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.core.BlockPos;
import java.util.Random;

public class InfrasonicDroneSound {
    private static final Random RANDOM = new Random();
    private static SoundInstance activeDrone = null;
    private static int droneTimer = 0;
    private static int droneLayer = 0;

    public static void tick(LocalPlayer player) {
        if (!HorrorConfig.isAudioEnabled() || !HorrorConfig.isHorrorEnabled()) {
            stop();
            return;
        }

        float dread = calculateDread(player);

        if (activeDrone != null) {
            droneTimer--;
            if (droneTimer <= 0 || RANDOM.nextFloat() < 0.02f) {
                stop();
            }
        } else if (dread > 0.2f && RANDOM.nextFloat() < 0.001f * dread) {
            float volume = 0.5f + RANDOM.nextFloat() * 0.3f;
            float pitch = 0.8f + RANDOM.nextFloat() * 0.4f;
            startDrone(player, volume, pitch);
        }
    }

    private static float calculateDread(LocalPlayer player) {
        float dread = 0f;
        BlockPos pos = player.blockPosition();
        if (player.level().getMaxLocalRawBrightness(pos) < 7) dread += 0.3f;
        if (player.level().dimension() == com.uncannyvoxel.registry.ModDimensions.SUBSTRATE_LEVEL) dread += 0.5f;
        var state = player.level().getBlockState(pos);
        if (state.is(com.uncannyvoxel.registry.ModTags.SULFUR_GEYSERS)) dread += 0.3f;
        return Math.min(dread, 1f);
    }

    private static void startDrone(LocalPlayer player, float volume, float pitch) {
        float masterVolume = HorrorConfig.getMasterVolume();
        volume = AudioSafety.clampInfrasonicVolume(volume * masterVolume);
        if (volume <= 0) return;

        activeDrone = SimpleSoundInstance.forUI(
            ModSoundEvents.INFRASONIC_DRONE,
            pitch,
            volume
        );
        droneLayer = (droneLayer + 1) % 3;
        droneTimer = 600 + RANDOM.nextInt(1200);

        Minecraft.getInstance().getSoundManager().play(activeDrone);
    }

    public static void stop() {
        if (activeDrone != null) {
            Minecraft.getInstance().getSoundManager().stop(activeDrone);
            activeDrone = null;
        }
    }
}
