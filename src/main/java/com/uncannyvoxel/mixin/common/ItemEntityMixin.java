package com.uncannyvoxel.mixin.common;

import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {

    @Inject(
        method = "onPlayerCollision",
        at = @At("HEAD")
    )
    private void uncanny$desaturatedEyePickup(CallbackInfo ci) {
    }
}
