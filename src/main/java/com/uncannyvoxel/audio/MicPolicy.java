package com.uncannyvoxel.audio;

import com.uncannyvoxel.config.HorrorConfig;

public final class MicPolicy {

    private static int acceptedConsentVersion = 0;
    private static boolean consentGiven = false;

    private MicPolicy() {}

    public static boolean isMicCaptureAllowed() {
        return HorrorConfig.get().horrorEnabled
                && HorrorConfig.get().mimicVoiceEnabled
                && consentGiven
                && acceptedConsentVersion >= HorrorConfig.get().micConsentVersion;
    }

    public static void grantConsent(int version) {
        if (version >= HorrorConfig.get().micConsentVersion) {
            consentGiven = true;
            acceptedConsentVersion = version;
        }
    }

    public static void revokeConsent() {
        consentGiven = false;
        acceptedConsentVersion = 0;
    }

    public static boolean hasConsent() {
        return consentGiven;
    }

    public static int getRequiredConsentVersion() {
        return HorrorConfig.get().micConsentVersion;
    }

    public static String getConsentStatus() {
        if (!HorrorConfig.get().horrorEnabled) return "Horror disabled";
        if (!HorrorConfig.get().mimicVoiceEnabled) return "Mimic voice disabled";
        if (!consentGiven) return "Consent not given";
        if (acceptedConsentVersion < HorrorConfig.get().micConsentVersion) return "Consent version outdated";
        return "Active";
    }
}