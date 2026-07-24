package com.uncannyvoxel.mixin.common;

import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {

    @Inject(
        method = "onCollision",
        at = @At("HEAD")
    )
    private void uncanny$desaturatedEyePickup(ItemEntity other, CallbackInfo ci) {
        ItemEntity entity = (ItemEntity) (Object) this;
        
        // Detect Desaturated Eye landing on mirror
        if (entity.getStack().isOf(com.uncannyvoxel.registry.ModItems.DESATURATED_EYE)) {
            // Portal activation handled in SulfurGlassMirrorBlock.onUse
        }
    }
}