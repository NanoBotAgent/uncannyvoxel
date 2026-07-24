package com.uncannyvoxel.blockentity;

import com.uncannyvoxel.horror.VantablackChunkManager;
import com.uncannyvoxel.portal.PortalController;
import com.uncannyvoxel.portal.SubstrateSpawn;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

public class SulfurGlassMirrorBlockEntity extends BlockEntity {

    private boolean portalActive = false;
    private int activationCooldown = 0;

    public SulfurGlassMirrorBlockEntity(BlockPos pos, BlockState state) {
        super(com.uncannyvoxel.registry.ModBlockEntities.SULFUR_GLASS_MIRROR, pos, state);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putBoolean("portalActive", portalActive);
        nbt.putInt("activationCooldown", activationCooldown);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        portalActive = nbt.getBoolean("portalActive");
        activationCooldown = nbt.getInt("activationCooldown");
    }

    public static void tick(ServerWorld world, BlockPos pos, BlockState state, SulfurGlassMirrorBlockEntity entity) {
        if (entity.activationCooldown > 0) {
            entity.activationCooldown--;
        }

        // Update vantablack chunks near sulfur geysers
        VantablackChunkManager.markSulfurGeyserArea(pos);
    }

    public void onPortalActivated() {
        portalActive = true;
        activationCooldown = 100;
        markDirty();
    }

    public void onPortalDeactivated() {
        portalActive = false;
        markDirty();
    }
}