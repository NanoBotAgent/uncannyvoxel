package com.uncannyvoxel.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class CompactedHairBlockEntity extends BlockEntity {

    private int pulsePhase = 0;

    public CompactedHairBlockEntity(BlockPos pos, BlockState state) {
        super(com.uncannyvoxel.registry.ModBlockEntities.COMPACTED_HAIR, pos, state);
    }

    public static void tick(net.minecraft.server.world.ServerWorld world, BlockPos pos, BlockState state, CompactedHairBlockEntity entity) {
        entity.pulsePhase = (entity.pulsePhase + 1) % 200;
        if (entity.pulsePhase % 20 == 0) {
            entity.markDirty();
        }
    }

    public int getPulsePhase() {
        return pulsePhase;
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("pulsePhase", pulsePhase);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        pulsePhase = nbt.getInt("pulsePhase");
    }
}