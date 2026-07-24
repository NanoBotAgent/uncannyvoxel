package com.uncannyvoxel.registry;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public final class ModStructures {

    public static final RegistryKey<net.minecraft.structure.Structure> FLESH_FARM =
            RegistryKey.of(RegistryKeys.STRUCTURE, Identifier.of("uncannyvoxel", "flesh_farm"));

    public static final RegistryKey<net.minecraft.structure.Structure> WEEPING_MONOLITH =
            RegistryKey.of(RegistryKeys.STRUCTURE, Identifier.of("uncannyvoxel", "weeping_monolith"));

    public static void init() {}
}