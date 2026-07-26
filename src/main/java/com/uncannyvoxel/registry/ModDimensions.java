package com.uncannyvoxel.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.level.Level;

public final class ModDimensions {

    public static final String MOD_ID = "uncannyvoxel";

    public static final ResourceKey<Level> SUBSTRATE =
            ResourceKey.create(Registries.DIMENSION, Identifier.of(MOD_ID, "substrate"));

    private ModDimensions() {}

    public static void init() {
    }
}
