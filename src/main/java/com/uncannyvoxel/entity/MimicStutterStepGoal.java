package com.uncannyvoxel.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;
import java.util.Random;

public class MimicStutterStepGoal extends Goal {

    private final PathAwareEntity mimic;
    private final Random random = new Random();
    private int cooldown = 0;

    public MimicStutterStepGoal(PathAwareEntity mimic) {
        this.mimic = mimic;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    @Override
    public boolean canStart() {
        if (cooldown > 0) {
            cooldown--;
            return false;
        }

        LivingEntity target = mimic.getTarget();
        if (target == null) return false;

        // 5% chance per tick when target is in range
        if (mimic.distanceTo(target) < 16.0 && random.nextFloat() < 0.05f) {
            return true;
        }

        return false;
    }

    @Override
    public void start() {
        if (mimic instanceof MimicEntity mimicEntity) {
            mimicEntity.triggerStutterStep();
        }
        cooldown = 100 + random.nextInt(200); // 5-15 seconds
    }
}