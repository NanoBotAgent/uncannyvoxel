package com.uncannyvoxel.registry;

import com.uncannyvoxel.blockentity.SulfurGlassMirrorBlockEntity;
import com.uncannyvoxel.blockentity.ChestMimicBlockEntity;
import com.uncannyvoxel.blockentity.TetherStakeBlockEntity;
import com.uncannyvoxel.blockentity.CompactedHairBlockEntity;
import com.uncannyvoxel.blockentity.RustedGrateBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;

public final class ModBlockEntities {

    public static final BlockEntityType<SulfurGlassMirrorBlockEntity> SULFUR_GLASS_MIRROR = register(
            "sulfur_glass_mirror",
            Builder.of(SulfurGlassMirrorBlockEntity::new, ModBlocks.SULFUR_GLASS_MIRROR).build(null)
    );

    public static final BlockEntityType<ChestMimicBlockEntity> CHEST_MIMIC = register(
            "chest_mimic",
            Builder.of(ChestMimicBlockEntity::new, ModBlocks.CHEST_MIMIC).build(null)
    );

    public static final BlockEntityType<TetherStakeBlockEntity> TETHER_STAKE = register(
            "tether_stake",
            Builder.of(TetherStakeBlockEntity::new, ModBlocks.TETHER_STAKE).build(null)
    );

    public static final BlockEntityType<CompactedHairBlockEntity> COMPACTED_HAIR = register(
            "compacted_hair",
            Builder.of(CompactedHairBlockEntity::new, ModBlocks.COMPACTED_HAIR).build(null)
    );

    public static final BlockEntityType<RustedGrateBlockEntity> RUSTED_GRATE = register(
            "rusted_grate",
            Builder.of(RustedGrateBlockEntity::new, ModBlocks.RUSTED_GRATE).build(null)
    );

    private static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType<T> type) {
        return BuiltInRegistries.BLOCK_ENTITY_TYPE.register(ResourceKey.create(Registries.BLOCK_ENTITY_TYPE, Identifier.of("uncannyvoxel", name)), type);
    }

    public static void init() {}
}
