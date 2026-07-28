package com.uncannyvoxel.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class RustedGrateBlockEntity extends BlockEntity {

    private int humLevel = 0;

    public RustedGrateBlockEntity(BlockPos pos, BlockState state) {
        super(com.uncannyvoxel.registry.ModBlockEntities.RUSTED_GRATE, pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, RustedGrateBlockEntity entity) {
        if (entity.humLevel < 100) {
            entity.humLevel++;
            entity.setChanged();
        }
    }

    public int getHumLevel() {
        return humLevel;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("humLevel", humLevel);
    }

    @Override
    protected void loadAdditional(CompoundTag tag) {
        super.loadAdditional(tag);
        humLevel = tag.getInt("humLevel");
    }
}