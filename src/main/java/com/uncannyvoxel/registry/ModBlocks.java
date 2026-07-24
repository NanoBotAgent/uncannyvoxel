package com.uncannyvoxel.registry;

import com.uncannyvoxel.block.SulfurGlassMirrorBlock;
import com.uncannyvoxel.block.RustedGrateBlock;
import com.uncannyvoxel.block.CompactedHairBlock;
import com.uncannyvoxel.block.ChestMimicBlock;
import com.uncannyvoxel.block.TetherStakeBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class ModBlocks {

    public static final Block SULFUR_GLASS_MIRROR = register(
            "sulfur_glass_mirror",
            new SulfurGlassMirrorBlock(AbstractBlock.Settings.copy(Blocks.TINTED_GLASS).nonOpaque())
    );

    public static final Block RUSTED_GRATE = register(
            "rusted_grate",
            new RustedGrateBlock(AbstractBlock.Settings.copy(Blocks.IRON_BARS))
    );

    public static final Block COMPACTED_HAIR = register(
            "compacted_hair",
            new CompactedHairBlock(AbstractBlock.Settings.copy(Blocks.SOUL_SAND))
    );

    public static final Block CHEST_MIMIC = register(
            "chest_mimic",
            new ChestMimicBlock(AbstractBlock.Settings.copy(Blocks.CHEST).nonOpaque())
    );

    public static final Block TETHER_STAKE = register(
            "tether_stake",
            new TetherStakeBlock(AbstractBlock.Settings.copy(Blocks.OAK_FENCE))
    );

    private static Block register(String name, Block block) {
        Registry.register(Registries.BLOCK, Identifier.of("uncannyvoxel", name), block);
        ModItems.registerBlockItem(name, block);
        return block;
    }

    public static void init() {}
}