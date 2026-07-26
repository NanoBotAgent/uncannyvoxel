package com.uncannyvoxel.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public final class ModTags {

    public static final TagKey<Block> SUBSTRATE_FRAME =
            TagKey.create(Registries.BLOCK, Identifier.of("uncannyvoxel", "substrate_frame"));

    public static final TagKey<Block> SULFUR_BLOCKS =
            TagKey.create(Registries.BLOCK, Identifier.of("uncannyvoxel", "sulfur_blocks"));

    public static final TagKey<Block> SULFUR_GEYSERS =
            TagKey.create(Registries.BLOCK, Identifier.of("uncannyvoxel", "sulfur_geysers"));

    private ModTags() {}

    public static void init() {
    }
}
