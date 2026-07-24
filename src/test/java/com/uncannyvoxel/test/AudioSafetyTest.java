package com.uncannyvoxel.test;

import com.uncannyvoxel.audio.AudioSafety;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AudioSafetyTest {

    @Test
    void clampHorrorVolumeRespectsMax() {
        assertEquals(0.5f, AudioSafety.clampHorrorVolume(1.0f));
        assertEquals(0.3f, AudioSafety.clampHorrorVolume(0.3f));
        assertEquals(0.0f, AudioSafety.clampHorrorVolume(-0.1f));
    }

    @Test
    void clampInfrasonicVolumeRespectsMax() {
        assertEquals(0.2f, AudioSafety.clampInfrasonicVolume(1.0f));
        assertEquals(0.1f, AudioSafety.clampInfrasonicVolume(0.1f));
        assertEquals(0.0f, AudioSafety.clampInfrasonicVolume(-0.1f));
    }

    @Test
    void clampInfrasonicFrequencyRespectsRange() {
        assertEquals(32.0f, AudioSafety.clampInfrasonicFrequency(20.0f));
        assertEquals(80.0f, AudioSafety.clampInfrasonicFrequency(120.0f));
        assertEquals(50.0f, AudioSafety.clampInfrasonicFrequency(50.0f));
    }

    @Test
    void normalizeDreadClamps0to1() {
        assertEquals(0.0f, AudioSafety.normalizeDread(-1.0f));
        assertEquals(1.0f, AudioSafety.normalizeDread(2.0f));
        assertEquals(0.5f, AudioSafety.normalizeDread(0.5f));
    }
}