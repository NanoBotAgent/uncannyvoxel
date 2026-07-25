package com.uncannyvoxel.mixin.common;

import com.uncannyvoxel.audio.ClientAudioDirector;
import com.uncannyvoxel.registry.ModSoundEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(
        method = "damage",
        at = @At("HEAD")
    )
    private void uncanny$slidingSkinOnDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        
        // Trigger sliding skin animation for Mimic
        if (entity instanceof com.uncannyvoxel.entity.MimicEntity mimic) {
            mimic.setSlidingSkin(true);
            ClientAudioDirector.playSlidingSkinSound(net.minecraft.client.MinecraftClient.getInstance());
        }
    }
}