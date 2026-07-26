package com.uncannyvoxel.audio;

public class DisabledMicCapture implements MicCapture {
    @Override
    public void start() {}

    @Override
    public void stop() {}

    @Override
    public float getAmplitude() { return 0.0f; }

    @Override
    public boolean isActive() { return false; }

    @Override
    public void release() {}
}