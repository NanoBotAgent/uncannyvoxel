package com.uncannyvoxel.mixin.common;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Mob.class)
public class MobEntityMixin {

    @Inject(
        method = "getNavigation",
        at = @At("RETURN"),
        cancellable = true
    )
    private void uncanny$stutterStepPath(CallbackInfoReturnable<PathNavigation> cir) {
    }
}
