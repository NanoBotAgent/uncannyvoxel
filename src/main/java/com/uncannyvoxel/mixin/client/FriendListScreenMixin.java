package com.uncannyvoxel.mixin.client;

import com.uncannyvoxel.config.HorrorConfig;
import com.uncannyvoxel.horror.NameCorruptor;
import net.minecraft.client.gui.screen.social.FriendListScreen;
import net.minecraft.client.gui.widget.EntryListWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Target: FriendListScreen (26.2 - may be SocialInteractionsScreen)
 * Confirmed class name needed from 26.2 mappings.
 */
@Mixin(targets = "net.minecraft.client.gui.screen.social.FriendListScreen", require = 0)
public class FriendListScreenMixin {

    @Inject(
        method = "render",
        at = @At("HEAD"),
        require = 0
    )
    private void uncanny$corruptFriendNamesOnRender(CallbackInfo ci) {
        if (!HorrorConfig.get().horrorEnabled || !HorrorConfig.get().friendListCorruptionEnabled) {
            return;
        }
        // Widget-level corruption happens in FriendEntryMixin
    }
}