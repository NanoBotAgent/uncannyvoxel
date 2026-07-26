package com.uncannyvoxel.entity;

import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class MimicSlideSkinGoal extends Goal {
    private final MimicEntity mimic;
    private int timer = 0;

    public MimicSlideSkinGoal(MimicEntity mimic) {
        this.mimic = mimic;
        this.setFlags(EnumSet.of());
    }

    @Override
    public boolean canUse() {
        return mimic.isSlidingSkin();
    }

    @Override
    public void start() {
        timer = 40;
    }

    @Override
    public void tick() {
        timer--;
        if (timer <= 0) {
            // Animation complete
        }
    }
}
