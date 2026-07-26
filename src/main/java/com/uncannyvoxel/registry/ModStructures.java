package com.uncannyvoxel.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.resources.ResourceKey;

public final class ModStructures {

    public static final String MOD_ID = "uncannyvoxel";

    public static final ResourceKey<Structure> FLESH_FARM =
            ResourceKey.create(Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath(MOD_ID, "flesh_farm"));

    public static final ResourceKey<Structure> WEEPING_MONOLITH =
            ResourceKey.create(Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath(MOD_ID, "weeping_monolith"));

    public static void init() {}
}
