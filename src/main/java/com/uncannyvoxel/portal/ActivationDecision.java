package com.uncannyvoxel.portal;

public record ActivationDecision(boolean activate, int consumeEye) {

    public static ActivationDecision evaluate(
            boolean frameValid,
            int eyeCount,
            int cooldownTicks
    ) {
        if (!frameValid) {
            return new ActivationDecision(false, 0);
        }

        if (eyeCount <= 0) {
            return new ActivationDecision(false, 0);
        }

        if (cooldownTicks > 0) {
            return new ActivationDecision(false, 0);
        }

        return new ActivationDecision(true, 1);
    }
}