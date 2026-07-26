package com.uncannyvoxel.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public final class ModBlocks {

    public static final Block SULFUR_GLASS_MIRROR = register(
            "sulfur_glass_mirror",
            new SulfurGlassMirrorBlock(BlockBehaviour.Properties.of().noOcclusion().strength(0.3f).sound(net.minecraft.world.level.block.SoundType.GLASS))
    );

    public static final Block RUSTED_GRATE = register(
            "rusted_grate",
            new RustedGrateBlock(BlockBehaviour.Properties.of().strength(3.0f).sound(net.minecraft.world.level.block.SoundType.METAL))
    );

    public static final Block COMPACTED_HAIR = register(
            "compacted_hair",
            new CompactedHairBlock(BlockBehaviour.Properties.of().strength(0.5f).sound(net.minecraft.world.level.block.SoundType.SOUL_SAND))
    );

    public static final Block CHEST_MIMIC = register(
            "chest_mimic",
            new ChestMimicBlock(BlockBehaviour.Properties.of().strength(2.5f).sound(net.minecraft.world.level.block.SoundType.WOOD).noOcclusion())
    );

    public static final Block TETHER_STAKE = register(
            "tether_stake",
            new TetherStakeBlock(BlockBehaviour.Properties.of().strength(2.0f).sound(net.minecraft.world.level.block.SoundType.WOOD))
    );

    private static Block register(String name, Block block) {
        ResourceLocation id = ResourceLocation.of("uncannyvoxel", name);
        BuiltInRegistries.BLOCK.register(id, block);
        ModItems.registerBlockItem(name, block);
        return block;
    }

    public static void init() {}
}
