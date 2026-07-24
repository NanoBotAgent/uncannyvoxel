package com.uncannyvoxel.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.MathHelper;

import java.util.EnumSet;

public class MimicHeadRotationGoal extends Goal {

    private final PathAwareEntity mimic;
    private int rotationTicks = 0;

    public MimicHeadRotationGoal(PathAwareEntity mimic) {
        this.mimic = mimic;
        this.setControls(EnumSet.of(Control.LOOK));
    }

    @Override
    public boolean canStart() {
        return rotationTicks > 0;
    }

    @Override
    public void start() {
        // Already rotating
    }

    @Override
    public void tick() {
        if (rotationTicks > 0) {
            rotationTicks--;
            float targetYaw = mimic.getYaw() + 180.0f;
            float currentYaw = mimic.getYaw();

            // Smooth rotation towards target
            float newYaw = MathHelper.lerpAngleDegrees(0.05f, currentYaw, targetYaw);
            mimic.setYaw(newYaw);
            mimic.headYaw = newYaw;
            mimic.bodyYaw = newYaw;
        }
    }

    public void triggerRotation(int ticks) {
        this.rotationTicks = ticks;
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }
}