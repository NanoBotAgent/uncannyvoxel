package com.uncannyvoxel.registry;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public final class ModDimensions {

    public static final String MOD_ID = "uncannyvoxel";

    public static final RegistryKey<World> SUBSTRATE =
            RegistryKey.of(RegistryKeys.WORLD, Identifier.of(MOD_ID, "substrate"));

    private ModDimensions() {}

    public static void init() {
    }
}