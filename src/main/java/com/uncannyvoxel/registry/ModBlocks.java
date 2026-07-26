package com.uncannyvoxel.registry;

import com.uncannyvoxel.block.SulfurGlassMirrorBlock;
import com.uncannyvoxel.block.RustedGrateBlock;
import com.uncannyvoxel.block.CompactedHairBlock;
import com.uncannyvoxel.block.ChestMimicBlock;
import com.uncannyvoxel.block.TetherStakeBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public final class ModBlocks {

    public static final Block SULFUR_GLASS_MIRROR = register(
            "sulfur_glass_mirror",
            new SulfurGlassMirrorBlock(BlockBehaviour.Properties.copy(Blocks.TINTED_GLASS).noOcclusion())
    );

    public static final Block RUSTED_GRATE = register(
            "rusted_grate",
            new RustedGrateBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BARS))
    );

    public static final Block COMPACTED_HAIR = register(
            "compacted_hair",
            new CompactedHairBlock(BlockBehaviour.Properties.copy(Blocks.SOUL_SAND))
    );

    public static final Block CHEST_MIMIC = register(
            "chest_mimic",
            new ChestMimicBlock(BlockBehaviour.Properties.copy(Blocks.CHEST).noOcclusion())
    );

    public static final Block TETHER_STAKE = register(
            "tether_stake",
            new TetherStakeBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE))
    );

    private static Block register(String name, Block block) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath("uncannyvoxel", name);
        Registry.register(BuiltInRegistries.BLOCK, id, block);
        ModItems.registerBlockItem(name, block);
        return block;
    }

    public static void init() {}
}
