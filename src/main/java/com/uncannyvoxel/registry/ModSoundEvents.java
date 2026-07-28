package com.uncannyvoxel.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public final class ModSoundEvents {

    public static final String MOD_ID = "uncannyvoxel";

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
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
    }

    public static void init() {}
}
