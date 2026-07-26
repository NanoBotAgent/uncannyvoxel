package com.uncannyvoxel.blockentity;

import com.uncannyvoxel.horror.VantablackChunkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SulfurGlassMirrorBlockEntity extends BlockEntity {

    private boolean portalActive = false;
    private int activationCooldown = 0;

    public SulfurGlassMirrorBlockEntity(BlockPos pos, BlockState state) {
        super(com.uncannyvoxel.registry.ModBlockEntities.SULFUR_GLASS_MIRROR, pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putBoolean("portalActive", portalActive);
        tag.putInt("activationCooldown", activationCooldown);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        portalActive = tag.getBoolean("portalActive");
        activationCooldown = tag.getInt("activationCooldown");
    }

    public static void tick(Level level, BlockPos pos, BlockState state, SulfurGlassMirrorBlockEntity entity) {
        if (entity.activationCooldown > 0) {
            entity.activationCooldown--;
        }

        VantablackChunkManager.markSulfurGeyserArea(pos);
    }

    public void onPortalActivated() {
        portalActive = true;
        activationCooldown = 100;
        setChanged();
    }

    public void onPortalDeactivated() {
        portalActive = false;
        setChanged();
    }
}
