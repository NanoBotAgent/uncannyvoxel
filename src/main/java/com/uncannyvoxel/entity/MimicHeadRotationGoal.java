package com.uncannyvoxel.entity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.util.Mth;

import java.util.EnumSet;

public class MimicHeadRotationGoal extends Goal {

    private final PathfinderMob mimic;
    private int rotationTicks = 0;

    public MimicHeadRotationGoal(PathfinderMob mimic) {
        this.mimic = mimic;
        this.setFlags(EnumSet.of(Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return rotationTicks > 0;
    }

    @Override
    public void start() {
    }

    @Override
    public void tick() {
        if (rotationTicks > 0) {
            rotationTicks--;
            float targetYaw = mimic.getYRot() + 180.0f;
            float currentYaw = mimic.getYRot();

            float newYaw = Mth.lerpAngleDegrees(0.05f, currentYaw, targetYaw);
            mimic.setYRot(newYaw);
            mimic.yHeadRot = newYaw;
            mimic.yBodyRot = newYaw;
        }
    }

    public void triggerRotation(int ticks) {
        this.rotationTicks = ticks;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
}
