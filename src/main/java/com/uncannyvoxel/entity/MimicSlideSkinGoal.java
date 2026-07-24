package com.uncannyvoxel.entity;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.random.Random;

import java.util.EnumSet;

public class MimicSlideSkinGoal extends Goal {
    private final MimicEntity mimic;
    private int timer = 0;

    public MimicSlideSkinGoal(MimicEntity mimic) {
        this.mimic = mimic;
        this.setControls(EnumSet.of());
    }

    @Override
    public boolean canStart() {
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