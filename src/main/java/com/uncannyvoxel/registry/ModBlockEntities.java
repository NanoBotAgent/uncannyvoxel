package com.uncannyvoxel.registry;

import com.uncannyvoxel.blockentity.SulfurGlassMirrorBlockEntity;
import com.uncannyvoxel.blockentity.ChestMimicBlockEntity;
import com.uncannyvoxel.blockentity.TetherStakeBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class ModBlockEntities {

    public static final BlockEntityType<SulfurGlassMirrorBlockEntity> SULFUR_GLASS_MIRROR = register(
            "sulfur_glass_mirror",
            BlockEntityType.Builder.create(SulfurGlassMirrorBlockEntity::new, ModBlocks.SULFUR_GLASS_MIRROR).build(null)
    );

    public static final BlockEntityType<ChestMimicBlockEntity> CHEST_MIMIC = register(
            "chest_mimic",
            BlockEntityType.Builder.create(ChestMimicBlockEntity::new, ModBlocks.CHEST_MIMIC).build(null)
    );

    public static final BlockEntityType<TetherStakeBlockEntity> TETHER_STAKE = register(
            "tether_stake",
            BlockEntityType.Builder.create(TetherStakeBlockEntity::new, ModBlocks.TETHER_STAKE).build(null)
    );

    private static <T extends net.minecraft.block.entity.BlockEntity> BlockEntityType<T> register(String name, BlockEntityType<T> type) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of("uncannyvoxel", name), type);
    }

    public static void init() {}
}