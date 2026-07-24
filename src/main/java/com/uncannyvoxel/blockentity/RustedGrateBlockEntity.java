package com.uncannyvoxel.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class RustedGrateBlockEntity extends BlockEntity {

    private int humLevel = 0;

    public RustedGrateBlockEntity(BlockPos pos, BlockState state) {
        super(com.uncannyvoxel.registry.ModBlockEntities.RUSTED_GRATE, pos, state);
    }

    public static void tick(net.minecraft.server.world.ServerWorld world, BlockPos pos, BlockState state, RustedGrateBlockEntity entity) {
        if (entity.humLevel < 100) {
            entity.humLevel++;
            entity.markDirty();
        }
    }

    public int getHumLevel() {
        return humLevel;
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("humLevel", humLevel);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        humLevel = nbt.getInt("humLevel");
    }
}