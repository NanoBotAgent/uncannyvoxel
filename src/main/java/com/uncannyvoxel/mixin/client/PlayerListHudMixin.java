package com.uncannyvoxel.mixin.client;

import com.uncannyvoxel.config.HorrorConfig;
import com.uncannyvoxel.horror.NameCorruptor;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerListHud.class)
public class PlayerListHudMixin {

    @Inject(
        method = "getPlayerName",
        at = @At("RETURN"),
        cancellable = true
    )
    private void uncanny$corruptTabListName(PlayerListEntry entry, CallbackInfoReturnable<Text> cir) {
        if (!HorrorConfig.get().horrorEnabled || !HorrorConfig.get().friendListCorruptionEnabled) {
            return;
        }

        Text original = cir.getReturnValue();
        if (original == null) return;

        String corrupted = NameCorruptor.corrupt(original.getString());
        cir.setReturnValue(Text.literal(corrupted).setStyle(original.getStyle()));
    }
}