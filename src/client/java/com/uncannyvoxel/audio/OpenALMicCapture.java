package com.uncannyvoxel.audio;

import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class OpenALMicCapture implements MicCapture {
    private long device;
    private long context;
    private int captureBufferSize = 4096;
    private int sampleRate = 44100;
    private int format = AL10.AL_FORMAT_MONO16;
    private Queue<ShortBuffer> captureQueue = new ConcurrentLinkedQueue<>();
    private Thread captureThread;
    private volatile boolean running = false;
    private volatile float currentAmplitude = 0.0f;

    @Override
    public void start() {
        if (running) return;

        String defaultDevice = ALC10.alcGetString(0, ALC10.ALC_CAPTURE_DEVICE_SPECIFIER);
        device = ALC10.alcCaptureOpenDevice(defaultDevice, sampleRate, format, captureBufferSize);
        if (device == 0) {
            running = false;
            return;
        }

        running = true;
        ALC10.alcCaptureStart(device);

        captureThread = new Thread(this::captureLoop, "UncannyVoxel-MicCapture");
        captureThread.setDaemon(true);
        captureThread.start();
    }

    private void captureLoop() {
        while (running && device != 0) {
            IntBuffer samplesAvailable = IntBuffer.allocate(1);
            ALC10.alcGetIntegerv(device, ALC10.ALC_CAPTURE_SAMPLES, samplesAvailable);
            int available = samplesAvailable.get(0);

            if (available > 0) {
                ShortBuffer buffer = ShortBuffer.allocate(available);
                ALC10.alcCaptureSamples(device, buffer, available);
                buffer.flip();

                float amplitude = calculateAmplitude(buffer);
                currentAmplitude = amplitude;

                if (captureQueue.size() < 10) {
                    captureQueue.offer(buffer);
                }
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private float calculateAmplitude(ShortBuffer buffer) {
        float sum = 0;
        int count = buffer.remaining();
        for (int i = 0; i < count; i++) {
            sum += Math.abs(buffer.get(i));
        }
        return count > 0 ? (sum / count) / 32768.0f : 0.0f;
    }

    @Override
    public void stop() {
        running = false;
        if (device != 0) {
            ALC10.alcCaptureStop(device);
            ALC10.alcCaptureCloseDevice(device);
            device = 0;
        }
        if (captureThread != null) {
            captureThread.interrupt();
            captureThread = null;
        }
        captureQueue.clear();
        currentAmplitude = 0.0f;
    }

    @Override
    public float getAmplitude() {
        return currentAmplitude;
    }

    @Override
    public boolean isActive() {
        return running && device != 0;
    }

    @Override
    public void release() {
        stop();
    }
}