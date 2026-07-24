package com.uncannyvoxel.blockentity;

import com.uncannyvoxel.horror.DreadModel;
import com.uncannyvoxel.portal.SubstrateSpawn;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

import java.util.UUID;

public class TetherStakeBlockEntity extends BlockEntity {

    private UUID ownerUuid;
    private int radius = 5;
    private boolean active = false;
    private int cooldown = 0;

    public TetherStakeBlockEntity(BlockPos pos, BlockState state) {
        super(com.uncannyvoxel.registry.ModBlockEntities.TETHER_STAKE, pos, state);
    }

    public void activate(PlayerEntity player) {
        if (active) return;

        ownerUuid = player.getUuid();
        active = true;
        cooldown = 0;
        markDirty();

        // Set safe zone - geometry cannot shift
        DreadModel.registerSafeZone(pos, radius);
    }

    public void onOwnerLeft(PlayerEntity player) {
        if (!active || ownerUuid == null || !ownerUuid.equals(player.getUuid())) {
            return;
        }

        // Violent uproot
        active = false;
        DreadModel.unregisterSafeZone(pos);
        ownerUuid = null;
        cooldown = 200; // 10 seconds before reactivation
        markDirty();

        // Alert nearby entities
        alertNearbyEntities(player);
    }

    private void alertNearbyEntities(PlayerEntity player) {
        if (world instanceof ServerWorld serverWorld) {
            Box box = new Box(pos).expand(50);
            serverWorld.getEntitiesByClass(net.minecraft.entity.LivingEntity.class, box, e -> e != player)
                    .forEach(e -> {
                        if (e instanceof net.minecraft.entity.mob.PathAwareEntity pathEntity) {
                            pathEntity.setTarget(player);
                        }
                    });
        }
    }

    public static void tick(ServerWorld world, BlockPos pos, BlockState state, TetherStakeBlockEntity entity) {
        if (entity.cooldown > 0) {
            entity.cooldown--;
        }

        if (entity.active && entity.ownerUuid != null) {
            PlayerEntity owner = world.getPlayerByUuid(entity.ownerUuid);
            if (owner == null || !isInRadius(owner, pos, entity.radius)) {
                if (owner != null) {
                    entity.onOwnerLeft(owner);
                } else {
                    entity.active = false;
                    com.uncannyvoxel.horror.DreadModel.unregisterSafeZone(pos);
                    entity.ownerUuid = null;
                    entity.markDirty();
                }
            }
        }
    }

    private static boolean isInRadius(PlayerEntity player, BlockPos stakePos, int radius) {
        return player.getBlockPos().isWithinDistance(stakePos, radius);
    }

    public boolean isSafeZone(BlockPos pos) {
        return active && this.pos.isWithinDistance(pos, radius);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (ownerUuid != null) {
            nbt.putUuid("owner", ownerUuid);
        }
        nbt.putInt("radius", radius);
        nbt.putBoolean("active", active);
        nbt.putInt("cooldown", cooldown);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        if (nbt.containsUuid("owner")) {
            ownerUuid = nbt.getUuid("owner");
        }
        radius = nbt.getInt("radius");
        active = nbt.getBoolean("active");
        cooldown = nbt.getInt("cooldown");
    }
}