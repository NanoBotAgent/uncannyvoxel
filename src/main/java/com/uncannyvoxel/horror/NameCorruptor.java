package com.uncannyvoxel.horror;

import com.uncannyvoxel.config.HorrorConfig;

import java.util.Random;

public final class NameCorruptor {

    private static final String[] EYE_STRINGS = {
            "◉◉◉", "👁👁", "◉_◉", "⊙⊙⊙", "☉☉", "𓂀𓂀", "◉◡◉"
    };

    private static final char[] CORRUPTION_CHARS = {'1', '0', 'x', '_', '̸', '̷'};
    private static final float[] CORRUPTION_WEIGHTS = {0.08f, 0.06f, 0.04f, 0.04f, 0.02f, 0.02f};
    private static final Random RANDOM = new Random();

    private static long sessionSeed;
    private static boolean initialized = false;

    private NameCorruptor() {}

    public static void init() {
        sessionSeed = System.nanoTime();
        initialized = true;
    }

    public static String corrupt(String name) {
        if (!HorrorConfig.get().horrorEnabled || !HorrorConfig.get().friendListCorruptionEnabled) {
            return name;
        }

        if (name == null || name.isBlank()) {
            return "???";
        }

        if (HorrorConfig.get().useFictionalFriendNames) {
            return generateFictionalName();
        }

        Random nameRandom = new Random(sessionSeed ^ name.hashCode());

        if (nameRandom.nextFloat() < 0.15f * HorrorConfig.get().maxCorruptionIntensity) {
            return EYE_STRINGS[nameRandom.nextInt(EYE_STRINGS.length)];
        }

        StringBuilder builder = new StringBuilder(name.length());

        for (char c : name.toCharArray()) {
            float roll = nameRandom.nextFloat();

            if (roll < HorrorConfig.get().maxCorruptionIntensity * 0.26f) {
                float weightedRoll = nameRandom.nextFloat();
                float cumulative = 0f;
                for (int i = 0; i < CORRUPTION_CHARS.length; i++) {
                    cumulative += CORRUPTION_WEIGHTS[i];
                    if (weightedRoll <= cumulative) {
                        builder.append(CORRUPTION_CHARS[i]);
                        break;
                    }
                }
            } else {
                builder.append(c);
            }
        }

        return builder.toString();
    }

    private static String generateFictionalName() {
        String[] prefixes = {"Void", "Null", "Empty", "Lost", "Hollow", "Faded", "Silent", "Dark"};
        String[] suffixes = {"Walker", "Watcher", "Seeker", "Drifter", "Echo", "Shadow", "Wraith", "Phantom"};

        Random r = new Random(sessionSeed);
        return prefixes[r.nextInt(prefixes.length)] + "_" + suffixes[r.nextInt(suffixes.length)];
    }

    public static void resetSession() {
        sessionSeed = System.nanoTime();
    }
}