package com.uncannyvoxel.mixin.client;

import com.uncannyvoxel.config.HorrorConfig;
import com.uncannyvoxel.horror.NameCorruptor;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HandledScreen.class)
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

        // Render eye tracking mouse cursor instead of default background
        // Implementation would draw the bloodshot eye following mouse position
    }
}