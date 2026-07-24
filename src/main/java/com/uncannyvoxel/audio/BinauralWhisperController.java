package com.uncannyvoxel.audio;

import com.uncannyvoxel.config.HorrorConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

public class BinauralWhisperController {
    private static final Random RANDOM = Random.create();

    private boolean active = false;
    private long nextWhisperTime = 0;
    private int whisperStage = 0;
    private Vec3d whisperPosition = Vec3d.ZERO;

    public void tick(PlayerEntity player) {
        if (!HorrorConfig.isHorrorEnabled() || !HorrorConfig.get().audioEnabled) return;

        long now = System.currentTimeMillis();

        if (active) {
            if (now >= nextWhisperTime) {
                playWhisper(player);
                nextWhisperTime = now + 3000 + RANDOM.nextInt(7000);
            }
        } else {
            boolean shouldActivate = RANDOM.nextFloat() < 0.0002f ||
                    player.getWorld().getLightLevel(player.getBlockPos()) < 3;

            if (shouldActivate) {
                active = true;
                whisperPosition = new Vec3d(
                    player.getX() + (RANDOM.nextDouble() - 0.5) * 4,
                    player.getY() + RANDOM.nextDouble() * 2,
                    player.getZ() + (RANDOM.nextDouble() - 0.5) * 4
                );
                nextWhisperTime = now + 1000;
            }
        }
    }

    private void playWhisper(PlayerEntity player) {
        float volume = HorrorConfig.getGlitchVolume() * 0.3f;
        if (volume <= 0) return;

        int soundIndex = RANDOM.nextInt(3);
        // Use different whisper sounds
        SoundEvent whisperSound = switch (soundIndex) {
            case 0 -> com.uncannyvoxel.registry.ModSoundEvents.BINAURAL_WHISPER;
            case 1 -> com.uncannyvoxel.registry.ModSoundEvents.BINAURAL_WHISPER;
            default -> com.uncannyvoxel.registry.ModSoundEvents.BINAURAL_WHISPER;
        };

        SoundInstance sound = PositionedSoundInstance.master(
            whisperSound,
            volume,
            0.8f + RANDOM.nextFloat() * 0.4f,
            whisperPosition.x, whisperPosition.y, whisperPosition.z
        );
        MinecraftClient.getInstance().getSoundManager().play(sound);

        // Move whisper position slightly
        whisperPosition = new Vec3d(
            whisperPosition.x + (RANDOM.nextDouble() - 0.5) * 2,
            whisperPosition.y + (RANDOM.nextDouble() - 0.5) * 1,
            whisperPosition.z + (RANDOM.nextDouble() - 0.5) * 2
        );
    }

    public void activate(Vec3d position) {
        active = true;
        whisperPosition = position;
        nextWhisperTime = System.currentTimeMillis() + 500;
    }

    public void deactivate() {
        active = false;
    }

    public void stop() {
        active = false;
    }

    public boolean isActive() {
        return active;
    }
}