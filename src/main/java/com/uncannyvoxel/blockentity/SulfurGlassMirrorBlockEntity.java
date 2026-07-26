package com.uncannyvoxel.blockentity;

import com.uncannyvoxel.horror.VantablackChunkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;

public class SulfurGlassMirrorBlockEntity extends BlockEntity {

    private boolean portalActive = false;
    private int activationCooldown = 0;

    public SulfurGlassMirrorBlockEntity(BlockPos pos, BlockState state) {
        super(com.uncannyvoxel.registry.ModBlockEntities.SULFUR_GLASS_MIRROR, pos, state);
    }

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
        view.putBoolean("portalActive", portalActive);
        view.putInt("activationCooldown", activationCooldown);
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        portalActive = view.getBoolean("portalActive", false);
        activationCooldown = view.getInt("activationCooldown", 0);
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