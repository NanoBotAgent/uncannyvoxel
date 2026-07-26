package com.uncannyvoxel.audio;

import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Identifier;

public interface MicCapture {
    void start();
    void stop();
    float getAmplitude();
    boolean isActive();
    void release();

    static MicCapture create() {
        if (com.uncannyvoxel.config.HorrorConfig.isMicEnabled()) {
            return new OpenALMicCapture();
        }
        return new DisabledMicCapture();
    }
}
