package com.uncannyvoxel.registry;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public final class ModTags {

    public static final TagKey<Block> SUBSTRATE_FRAME =
            TagKey.of(RegistryKeys.BLOCK, Identifier.of("uncannyvoxel", "substrate_frame"));

    public static final TagKey<Block> SULFUR_BLOCKS =
            TagKey.of(RegistryKeys.BLOCK, Identifier.of("uncannyvoxel", "sulfur_blocks"));

    public static final TagKey<Block> SULFUR_GEYSERS =
            TagKey.of(RegistryKeys.BLOCK, Identifier.of("uncannyvoxel", "sulfur_geysers"));

    private ModTags() {}
}