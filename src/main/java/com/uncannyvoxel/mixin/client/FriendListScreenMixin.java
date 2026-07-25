package com.uncannyvoxel.mixin.client;

import com.uncannyvoxel.config.HorrorConfig;
import com.uncannyvoxel.horror.NameCorruptor;
import net.minecraft.client.gui.screen.social.SocialInteractionsScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Target: SocialInteractionsScreen (1.21.11 Yarn)
 */
@Mixin(targets = "net.minecraft.client.gui.screen.social.SocialInteractionsScreen", require = 0)
public class FriendListScreenMixin {

    @Inject(
        method = "getTitle",
        at = @At("RETURN"),
        cancellable = true,
        require = 0
    )
    private void uncanny$corruptScreenTitle(CallbackInfoReturnable<Text> cir) {
        if (!HorrorConfig.get().horrorEnabled || !HorrorConfig.get().friendListCorruptionEnabled) {
            return;
        }

        Text original = cir.getReturnValue();
        if (original == null) return;

        String corrupted = NameCorruptor.corrupt(original.getString());rupt(original.getString());
        cir.setReturnValue(Text.literal(corrupted).setStyle(original.getStyle()));
    }
}