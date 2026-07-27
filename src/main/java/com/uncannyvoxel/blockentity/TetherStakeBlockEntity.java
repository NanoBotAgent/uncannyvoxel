package com.uncannyvoxel.blockentity;

import com.uncannyvoxel.horror.DreadModel;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.UUID;

public class TetherStakeBlockEntity extends BlockEntity {

    private UUID ownerUuid;
    private int radius = 5;
    private boolean active = false;
    private int cooldown = 0;

    public TetherStakeBlockEntity(BlockPos pos, BlockState state) {
        super(com.uncannyvoxel.registry.ModBlockEntities.TETHER_STAKE, pos, state);
    }

    public void activate(Player player) {
        if (active) return;

        ownerUuid = player.getUUID();
        active = true;
        cooldown = 0;
        setChanged();

        DreadModel.registerSafeZone(getBlockPos(), radius);
    }

    public void onOwnerLeft(Player player) {
        if (!active || ownerUuid == null || !ownerUuid.equals(player.getUUID())) {
            return;
        }

        active = false;
        DreadModel.unregisterSafeZone(getBlockPos());
        ownerUuid = null;
        cooldown = 200;
        setChanged();

        alertNearbyEntities(player);
    }

    private void alertNearbyEntities(Player player) {
        if (level instanceof ServerLevel serverLevel) {
            AABB box = new AABB(getBlockPos()).inflate(50);
            serverLevel.getEntitiesOfClass(LivingEntity.class, box, e -> e != player)
                    .forEach(e -> {
                        if (e instanceof Mob mob) {
                            mob.setTarget(player);
                        }
                    });
        }
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TetherStakeBlockEntity entity) {
        if (entity.cooldown > 0) {
            entity.cooldown--;
        }

        if (entity.active && entity.ownerUuid != null && level instanceof ServerLevel serverLevel) {
            Player owner = serverLevel.getServer().getPlayerList().getPlayer(entity.ownerUuid);
            if (owner == null || !isInRadius(owner, pos, entity.radius)) {
                if (owner != null) {
                    entity.onOwnerLeft(owner);
                } else {
                    entity.active = false;
                    DreadModel.unregisterSafeZone(pos);
                    entity.ownerUuid = null;
                    entity.setChanged();
                }
            }
        }
    }

    private static boolean isInRadius(Player player, BlockPos stakePos, int radius) {
        return player.blockPosition().closerThan(stakePos, radius);
    }

    public boolean isSafeZone(BlockPos checkPos) {
        return active && getBlockPos().closerThan(checkPos, radius);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (ownerUuid != null) {
            tag.putLong("ownerMost", ownerUuid.getMostSignificantBits());
            tag.putLong("ownerLeast", ownerUuid.getLeastSignificantBits());
        }
        tag.putInt("radius", radius);
        tag.putBoolean("active", active);
        tag.putInt("cooldown", cooldown);
    }

    @Override
    protected void loadAdditional(CompoundTag tag) {
        super.loadAdditional(tag);
        long most = tag.getLong("ownerMost");
        long least = tag.getLong("ownerLeast");
        if (most != 0 && least != 0) ownerUuid = new java.util.UUID(most, least);
        radius = tag.getInt("radius");
        active = tag.getBoolean("active");
        cooldown = tag.getInt("cooldown");
    }
}
