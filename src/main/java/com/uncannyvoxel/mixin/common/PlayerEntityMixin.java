package com.uncannyvoxel.mixin.common;

import com.uncannyvoxel.config.HorrorConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerEntityMixin {

    @Inject(method = "canSleep", at = @At("HEAD"), cancellable = true, require = 0)
    private void uncanny$bedParalysis(BlockPos pos, CallbackInfoReturnable<?> cir) {
        if (!HorrorConfig.isHorrorEnabled() || !HorrorConfig.get().bedParalysis) return;
        if (HorrorConfig.get().photosensitivitySafeMode) return;
    }

    @Inject(method = "sleep", at = @At("HEAD"), require = 0)
    private void uncanny$onSleep(BlockPos pos, CallbackInfoReturnable<?> cir) {
        if (!HorrorConfig.isHorrorEnabled() || !HorrorConfig.get().bedParalysis) return;
    }

    @Inject(method = "wakeUp", at = @At("HEAD"), require = 0)
    private void uncanny$onWake(CallbackInfo ci) {
    }
}
