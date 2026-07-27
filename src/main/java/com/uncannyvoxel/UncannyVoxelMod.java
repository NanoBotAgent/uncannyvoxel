package com.uncannyvoxel;

import com.uncannyvoxel.config.HorrorConfig;
import com.uncannyvoxel.registry.*;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UncannyVoxelMod implements ModInitializer {

    public static final String MOD_ID = "uncannyvoxel";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static HorrorConfig CONFIG;

    @Override
    public void onInitialize() {
        CONFIG = HorrorConfig.load();

        ModBlocks.init();
        ModItems.init();
        ModBlockEntities.init();
        ModSoundEvents.init();
        ModDimensions.init();
        ModTags.init();
        ModEntities.init();
        ModStructures.init();

        LOGGER.info("The Uncanny Voxel initialized. Substrate dimension: {}", ModDimensions.SUBSTRATE_LEVEL.identifier());
    }
}
