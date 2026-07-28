package com.uncannyvoxel.blockentity;

import com.uncannyvoxel.horror.VantablackChunkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
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
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putBoolean("portalActive", portalActive);
        output.putInt("activationCooldown", activationCooldown);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        portalActive = input.getBooleanOr("portalActive", false);
        activationCooldown = input.getIntOr("activationCooldown", 0);
    }

    @Override
    public void setRemoved() {
        onPortalDeactivated();
        super.setRemoved();
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
