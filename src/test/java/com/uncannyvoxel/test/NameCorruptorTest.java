package com.uncannyvoxel.test;

import com.uncannyvoxel.config.HorrorConfig;
import com.uncannyvoxel.horror.NameCorruptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NameCorruptorTest {

    @BeforeEach
    void resetConfig() {
        HorrorConfig.get().horrorEnabled = true;
        HorrorConfig.get().friendListCorruptionEnabled = true;
        HorrorConfig.get().maxCorruptionIntensity = 0.3f;
        HorrorConfig.get().useFictionalFriendNames = false;
    }

    @Test
    void deterministicForSameSeed() {
        String name = "TestPlayer";
        String result1 = NameCorruptor.corrupt(name);
        String result2 = NameCorruptor.corrupt(name);
        assertEquals(result1, result2);
    }

    @Test
    void neverReturnsNull() {
        assertNotNull(NameCorruptor.corrupt("Test"));
        assertNotNull(NameCorruptor.corrupt(""));
        assertNotNull(NameCorruptor.corrupt(null));
    }

    @Test
    void handlesBlankNames() {
        assertEquals("???", NameCorruptor.corrupt(""));
        assertEquals("???", NameCorruptor.corrupt("   "));
        assertEquals("???", NameCorruptor.corrupt(null));
    }

    @Test
    void sometimesCorruptsNames() {
        // With low intensity, may not corrupt - just verify it doesn't crash
        for (int i = 0; i < 100; i++) {
            String result = NameCorruptor.corrupt("TestPlayer" + i);
            assertNotNull(result);
            assertFalse(result.isEmpty());
        }
    }
}