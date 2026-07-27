package com.uncannyvoxel.audio;

import com.uncannyvoxel.config.HorrorConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class BinauralWhisperController {
    private static final Random RANDOM = new Random();

    private boolean active = false;
    private long nextWhisperTime = 0;
    private int whisperStage = 0;
    private Vec3 whisperPosition = Vec3.ZERO;

    public void tick(LocalPlayer player) {
        if (!HorrorConfig.isHorrorEnabled() || !HorrorConfig.get().audioEnabled) return;

        long now = System.currentTimeMillis();

        if (active) {
            if (now >= nextWhisperTime) {
                playWhisper(player);
                nextWhisperTime = now + 3000 + RANDOM.nextInt(7000);
            }
        } else {
            boolean shouldActivate = RANDOM.nextFloat() < 0.0002f ||
                    player.level().getMaxLocalRawBrightness(player.blockPosition()) < 3;

            if (shouldActivate) {
                active = true;
                whisperPosition = new Vec3(
                    player.getX() + (RANDOM.nextDouble() - 0.5) * 4,
                    player.getY() + RANDOM.nextDouble() * 2,
                    player.getZ() + (RANDOM.nextDouble() - 0.5) * 4
                );
                nextWhisperTime = now + 1000;
            }
        }
    }

    private void playWhisper(LocalPlayer player) {
        float volume = HorrorConfig.getGlitchVolume() * 0.3f;
        if (volume <= 0) return;

        Minecraft.getInstance().getSoundManager().play(
            net.minecraft.client.resources.sounds.SimpleSoundInstance.forUI(
                com.uncannyvoxel.registry.ModSoundEvents.BINAURAL_WHISPER,
                0.8f + RANDOM.nextFloat() * 0.4f,
                volume
            )
        );

        whisperPosition = new Vec3(
            whisperPosition.x + (RANDOM.nextDouble() - 0.5) * 2,
            whisperPosition.y + (RANDOM.nextDouble() - 0.5) * 1,
            whisperPosition.z + (RANDOM.nextDouble() - 0.5) * 2
        );

        whisperStage++;
    }

    public void activate(Vec3 position) {
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
