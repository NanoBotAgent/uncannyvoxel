package com.uncannyvoxel.entity;


import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.PathfinderMob;

import java.util.EnumSet;
import java.util.Random;

public class MimicStutterStepGoal extends Goal {

    private final PathfinderMob mimic;
    private final Random random = new Random();
    private int cooldown = 0;

    public MimicStutterStepGoal(PathfinderMob mimic) {
        this.mimic = mimic;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (cooldown > 0) {
            cooldown--;
            return false;
        }

        net.minecraft.world.entity.LivingEntity target = mimic.getTarget();
        if (target == null) return false;

        if (mimic.distanceToSqr(target) < 256.0 && random.nextFloat() < 0.05f) {
            return true;
        }

        return false;
    }

    @Override
    public void start() {
        if (mimic instanceof MimicEntity mimicEntity) {
            mimicEntity.triggerStutterStep();
        }
        cooldown = 100 + random.nextInt(200);
    }
}
