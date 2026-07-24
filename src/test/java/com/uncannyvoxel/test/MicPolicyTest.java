package com.uncannyvoxel.test;

import com.uncannyvoxel.config.HorrorConfig;
import com.uncannyvoxel.audio.MicPolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MicPolicyTest {

    @BeforeEach
    void resetConfig() {
        HorrorConfig.get().horrorEnabled = true;
        HorrorConfig.get().mimicVoiceEnabled = false;
        HorrorConfig.get().requireMicConsent = true;
        HorrorConfig.get().micConsentVersion = 1;
    }

    @Test
    void disabledByDefault() {
        assertFalse(MicPolicy.isMicCaptureAllowed());
    }

    @Test
    void requiresConsent() {
        HorrorConfig.get().mimicVoiceEnabled = true;
        assertFalse(MicPolicy.isMicCaptureAllowed());
    }

    @Test
    void requiresConsentVersion() {
        HorrorConfig.get().mimicVoiceEnabled = true;
        MicPolicy.grantConsent(1);
        assertTrue(MicPolicy.isMicCaptureAllowed());

        MicPolicy.revokeConsent();
        assertFalse(MicPolicy.isMicCaptureAllowed());
    }

    @Test
    void requiresMasterHorrorToggle() {
        HorrorConfig.get().horrorEnabled = false;
        HorrorConfig.get().mimicVoiceEnabled = true;
        MicPolicy.grantConsent(1);
        assertFalse(MicPolicy.isMicCaptureAllowed());
    }
}