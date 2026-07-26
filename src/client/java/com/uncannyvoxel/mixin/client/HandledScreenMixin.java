package com.uncannyvoxel.mixin.client;

import com.uncannyvoxel.config.HorrorConfig;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerScreen.class)
public class HandledScreenMixin {

    @Inject(
        method = "renderBackground",
        at = @At("HEAD"),
        cancellable = true
    )
    private void uncanny$renderInventoryRot(CallbackInfo ci) {
        if (!HorrorConfig.get().horrorEnabled || !HorrorConfig.get().inventoryRotEnabled) {
            return;
        }
    }
}
