package com.uncannyvoxel.test;

import com.uncannyvoxel.portal.ActivationDecision;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActivationDecisionTest {

    @Test
    void validFrameAndEyeActivates() {
        ActivationDecision decision = ActivationDecision.evaluate(true, 1, 0);
        assertTrue(decision.activate());
        assertEquals(1, decision.consumeEye());
    }

    @Test
    void invalidFrameDoesNotActivate() {
        ActivationDecision decision = ActivationDecision.evaluate(false, 1, 0);
        assertFalse(decision.activate());
        assertEquals(0, decision.consumeEye());
    }

    @Test
    void noEyeDoesNotActivate() {
        ActivationDecision decision = ActivationDecision.evaluate(true, 0, 0);
        assertFalse(decision.activate());
        assertEquals(0, decision.consumeEye());
    }

    @Test
    void cooldownPreventsActivation() {
        ActivationDecision decision = ActivationDecision.evaluate(true, 1, 10);
        assertFalse(decision.activate());
        assertEquals(0, decision.consumeEye());
    }
}