package com.uncannyvoxel.mixin.client;

import com.uncannyvoxel.config.HorrorConfig;
import com.uncannyvoxel.horror.NameCorruptor;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerTabOverlay.class)
public class PlayerListHudMixin {

    @Inject(
        method = "getNameForDisplay",
        at = @At("RETURN"),
        cancellable = true
    )
    private void uncanny$corruptTabListName(PlayerInfo entry, CallbackInfoReturnable<Component> cir) {
        if (!HorrorConfig.get().horrorEnabled || !HorrorConfig.get().friendListCorruptionEnabled) {
            return;
        }

        Component original = cir.getReturnValue();
        if (original == null) return;

        String corrupted = NameCorruptor.corrupt(original.getString());
        cir.setReturnValue(Component.literal(corrupted).setStyle(original.getStyle()));
    }
}
