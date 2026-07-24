package com.uncannyvoxel.audio;

import com.uncannyvoxel.config.HorrorConfig;
import com.uncannyvoxel.registry.ModSoundEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.player.LocalPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class InfrasonicDroneSound {
    private static final Random RANDOM = Random.create();
    private static SoundInstance activeDrone = null;
    private static int droneTimer = 0;
    private static int droneLayer = 0;

    public static void tick(LocalPlayerEntity player) {
        if (!HorrorConfig.isAudioEnabled() || !HorrorConfig.isHorrorEnabled()) {
            stop();
            return;
        }

        World world = player.getWorld();
        float dread = calculateDread(world, player.getBlockPos());

        if (activeDrone != null) {
            droneTimer--;
            if (droneTimer <= 0 || RANDOM.nextFloat() < 0.02f) {
                stop();
            }
        } else if (dread > 0.2f && RANDOM.nextFloat() < 0.001f * dread) {
            startDrone(player, volume, pitch);
        }
    }

    private static float calculateDread(World world, BlockPos pos) {
        float dread = 0f;
        if (world.getLightLevel(pos) < 7) dread += 0.3f;
        if (world.getRegistryKey() == com.uncannyvoxel.registry.ModDimensions.SUBSTRATE) dread += 0.5f;
        if (world.getBlockState(pos).isIn(com.uncannyvoxel.registry.ModTags.SULFUR_GEYSERS)) dread += 0.3f;
        return Math.min(dread, 1f);
    }

    private static void startDrone(LocalPlayerEntity player, float volume, float pitch) {
        float masterVolume = HorrorConfig.getMasterVolume();
        volume = AudioSafety.clampInfrasoundVolume(volume * masterVolume, masterVolume);
        if (volume <= 0) return;

        activeDrone = new SoundInstance(
            ModSoundEvents.INFRASONIC_DRONE,
            SoundCategory.AMBIENT,
            volume,
            pitch,
            player.getBlockPos(),
            SoundInstance.Attenuation.NONE,
            0,
            Identifier.of("uncannyvoxel", "drone_" + droneLayer)
        );
        droneLayer = (droneLayer + 1) % 3;
        droneTimer = 600 + RANDOM.nextInt(1200);

        MinecraftClient.getInstance().getSoundManager().play(activeDrone);
    }

    public static void stop() {
        if (activeDrone != null) {
            MinecraftClient.getInstance().getSoundManager().stop(activeDrone);
            activeDrone = null;
        }
    }
}