package com.uncannyvoxel;

import com.uncannyvoxel.audio.ClientAudioDirector;
import com.uncannyvoxel.horror.BlinkScheduler;
import com.uncannyvoxel.horror.NameCorruptor;
import com.uncannyvoxel.horror.VantablackChunkManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(EnvType.CLIENT)
public class UncannyVoxelClient implements ClientModInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger("uncannyvoxel-client");

    @Override
    public void onInitializeClient() {
        ClientAudioDirector.init();
        BlinkScheduler.init();
        VantablackChunkManager.init();
        NameCorruptor.init();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null && client.world != null) {
                ClientAudioDirector.tick(client);
                BlinkScheduler.tick(client);
            }
        });

        WorldRenderEvents.AFTER_ENTITIES.register(context -> {
            // Update entity proximity for peripheral glitch / depth of field dread
        });

        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            // Inventory rot eye tracking
        });

        LOGGER.info("Uncanny Voxel client initialized");
    }
}