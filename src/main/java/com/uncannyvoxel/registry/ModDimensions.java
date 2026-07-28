package com.uncannyvoxel.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public final class ModDimensions {

    public static final String MOD_ID = "uncannyvoxel";

    public static final ResourceKey<Level> SUBSTRATE_LEVEL =
            ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(MOD_ID, "substrate"));

    private ModDimensions() {}

    public static void init() {}
}
