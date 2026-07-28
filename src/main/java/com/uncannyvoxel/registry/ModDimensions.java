package com.uncannyvoxel.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.LevelStem;

public final class ModDimensions {

    public static final String MOD_ID = "uncannyvoxel";

    public static final ResourceKey<LevelStem> SUBSTRATE =
            ResourceKey.create(Registries.LEVEL_STEM, Identifier.fromNamespaceAndPath(MOD_ID, "substrate"));

    public static final ResourceKey<Level> SUBSTRATE_LEVEL =
            ResourceKey.create(Registries.DIMENSION, Identifier.fromNamespaceAndPath(MOD_ID, "substrate"));

    private ModDimensions() {}

    public static void init() {
    }
}
