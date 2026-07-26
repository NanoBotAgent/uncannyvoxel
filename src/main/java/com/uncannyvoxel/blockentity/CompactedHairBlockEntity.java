package com.uncannyvoxel.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CompactedHairBlockEntity extends BlockEntity {

    private int pulsePhase = 0;

    public CompactedHairBlockEntity(BlockPos pos, BlockState state) {
        super(com.uncannyvoxel.registry.ModBlockEntities.COMPACTED_HAIR, pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, CompactedHairBlockEntity entity) {
        entity.pulsePhase = (entity.pulsePhase + 1) % 200;
        if (entity.pulsePhase % 20 == 0) {
            entity.setChanged();
        }
    }

    public int getPulsePhase() {
        return pulsePhase;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("pulsePhase", pulsePhase);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        pulsePhase = tag.getInt("pulsePhase").orElse(0);
    }
}