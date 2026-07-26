package com.uncannyvoxel.horror;

import com.uncannyvoxel.config.HorrorConfig;

import java.util.Random;

public final class NameCorruptor {

    private static final Random RANDOM = new Random();
    private static final char[] REPLACEMENT_CHARS = {
        '̶', '̡', 'ͅ', '̛', '̷', '̸', '͖', '͙'
    };

    private NameCorruptor() {}

    public static String corrupt(String original) {
        if (original == null || original.isEmpty()) return original;
        if (!HorrorConfig.get().horrorEnabled) return original;

        int corruptionLevel = RANDOM.nextInt(3);
        if (corruptionLevel == 0) return original;

        StringBuilder sb = new StringBuilder(original);
        int numReplacements = RANDOM.nextInt(Math.max(1, original.length() / 3)) + 1;

        for (int i = 0; i < numReplacements; i++) {
            int pos = RANDOM.nextInt(original.length());
            if (pos < sb.length()) {
                char replacement = REPLACEMENT_CHARS[RANDOM.nextInt(REPLACEMENT_CHARS.length)];
                sb.insert(pos, replacement);
            }
        }

        if (RANDOM.nextFloat() < 0.2f && original.length() > 2) {
            int pos1 = RANDOM.nextInt(original.length());
            int pos2 = RANDOM.nextInt(original.length());
            if (pos1 < sb.length() && pos2 < sb.length() && pos1 != pos2) {
                char c1 = sb.charAt(pos1);
                char c2 = sb.charAt(pos2);
                sb.setCharAt(pos1, c2);
                sb.setCharAt(pos2, c1);
            }
        }

        return sb.toString();
    }
}
