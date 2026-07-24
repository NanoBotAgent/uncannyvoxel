package com.uncannyvoxel.mixin.common;

import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.ai.pathing.Path;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
public class MobEntityMixin {

    @Inject(
        method = "getNavigation",
        at = @At("RETURN"),
        cancellable = true
    )
    private void uncanny$stutterStepPath(CallbackInfoReturnable<net.minecraft.entity.ai.pathing.EntityNavigation> cir) {
        // Inject stutter-step pathfinding for Mimic and other horror entities
        // Override path to include random micro-teleports and pauses
    }
}