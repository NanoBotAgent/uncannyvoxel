package com.uncannyvoxel.mixin.common;

import com.uncannyvoxel.entity.MimicEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(
        method = "hurt",
        at = @At("HEAD")
    )
    private void uncanny$slidingSkinOnDamage(DamageSource source, float amount, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (entity instanceof MimicEntity mimic) {
            mimic.setSlidingSkin(true);
        }
    }
}
