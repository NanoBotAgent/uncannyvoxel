package com.uncannyvoxel.mixin.common;

import com.uncannyvoxel.config.HorrorConfig;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject(method = "canSleep", at = @At("HEAD"), cancellable = true)
    private void uncanny$bedParalysis(BlockPos pos, CallbackInfoReturnable<net.minecraft.entity.player.SleepFailureReason> cir) {
        if (!HorrorConfig.isHorrorEnabled() || !HorrorConfig.get().bedParalysis) return;
        if (HorrorConfig.get().photosensitivitySafeMode) return;
        // Add bed paralysis logic
    }

    @Inject(method = "sleep", at = @At("HEAD"))
    private void uncanny$onSleep(BlockPos pos, CallbackInfoReturnable<net.minecraft.entity.player.SleepFailureReason> cir) {
        if (!HorrorConfig.isHorrorEnabled() || !HorrorConfig.get().bedParalysis) return;
        // Start bed paralysis scenario
    }

    @Inject(method = "wakeUp", at = @At("HEAD"))
    private void uncanny$onWake(CallbackInfo ci) {
        // End bed paralysis
    }
}