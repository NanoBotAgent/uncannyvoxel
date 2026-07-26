package com.uncannyvoxel.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public final class ModDimensions {

    public static final String MOD_ID = "uncannyvoxel";

    public static final ResourceKey<Level> SUBSTRATE =
            ResourceKey.create(Registries.DIMENSION, ResourceLocation.of(MOD_ID, "substrate"));

    private ModDimensions() {}

    public static void init() {
    }
}
