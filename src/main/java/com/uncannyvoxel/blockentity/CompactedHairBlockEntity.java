package com.uncannyvoxel.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;

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
    protected void writeData(WriteView view) {
        super.writeData(view);
        view.putInt("pulsePhase", pulsePhase);
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        pulsePhase = view.getInt("pulsePhase", 0);
    }
}