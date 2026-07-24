package com.uncannyvoxel.registry;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public final class ModSoundEvents {

    public static final SoundEvent WET_TEARING = register("wet_tearing");
    public static final SoundEvent BLINK_TRIGGER = register("blink_trigger");
    public static final SoundEvent INFRASONIC_DRONE = register("infrasonic_drone");
    public static final SoundEvent BINAURAL_WHISPER = register("binaural_whisper");
    public static final SoundEvent CHEST_SNAP = register("chest_snap");
    public static final SoundEvent MIRROR_WHISPER = register("mirror_whisper");
    public static final SoundEvent SLIDING_SKIN = register("sliding_skin");
    public static final SoundEvent STUTTER_STEP = register("stutter_step");
    public static final SoundEvent SUBSTRATE_AMBIENT = register("substrate_ambient");
    public static final SoundEvent MIMIC_VOICE = register("mimic_voice");

    private static SoundEvent register(String name) {
        Identifier id = Identifier.of("uncannyvoxel", name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void init() {}
}