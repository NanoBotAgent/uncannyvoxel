package com.uncannyvoxel.registry;

import com.uncannyvoxel.blockentity.SulfurGlassMirrorBlockEntity;
import com.uncannyvoxel.blockentity.ChestMimicBlockEntity;
import com.uncannyvoxel.blockentity.TetherStakeBlockEntity;
import com.uncannyvoxel.blockentity.CompactedHairBlockEntity;
import com.uncannyvoxel.blockentity.RustedGrateBlockEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.entity.BlockEntityType;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;

public final class ModBlockEntities {

    public static final BlockEntityType<SulfurGlassMirrorBlockEntity> SULFUR_GLASS_MIRROR = register(
            "sulfur_glass_mirror",
            FabricBlockEntityTypeBuilder.create(SulfurGlassMirrorBlockEntity::new, ModBlocks.SULFUR_GLASS_MIRROR).build()
    );

    public static final BlockEntityType<ChestMimicBlockEntity> CHEST_MIMIC = register(
            "chest_mimic",
            FabricBlockEntityTypeBuilder.create(ChestMimicBlockEntity::new, ModBlocks.CHEST_MIMIC).build()
    );

    public static final BlockEntityType<TetherStakeBlockEntity> TETHER_STAKE = register(
            "tether_stake",
            FabricBlockEntityTypeBuilder.create(TetherStakeBlockEntity::new, ModBlocks.TETHER_STAKE).build()
    );

    public static final BlockEntityType<CompactedHairBlockEntity> COMPACTED_HAIR = register(
            "compacted_hair",
            FabricBlockEntityTypeBuilder.create(CompactedHairBlockEntity::new, ModBlocks.COMPACTED_HAIR).build()
    );

    public static final BlockEntityType<RustedGrateBlockEntity> RUSTED_GRATE = register(
            "rusted_grate",
            FabricBlockEntityTypeBuilder.create(RustedGrateBlockEntity::new, ModBlocks.RUSTED_GRATE).build()
    );

    private static <T extends net.minecraft.world.level.block.entity.BlockEntity> BlockEntityType<T> register(String name, BlockEntityType<T> type) {
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath("uncannyvoxel", name), type);
    }

    public static void init() {}
}
