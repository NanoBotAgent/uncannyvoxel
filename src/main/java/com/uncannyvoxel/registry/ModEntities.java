package com.uncannyvoxel.registry;

import com.uncannyvoxel.entity.MimicEntity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class ModEntities {

    public static final EntityType<MimicEntity> MIMIC = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of("uncannyvoxel", "mimic"),
            EntityType.Builder.create(MimicEntity::new, SpawnGroup.MONSTER)
                    .dimensions(EntityDimensions.fixed(0.6f, 1.95f))
                    .build()
    );

    public static void init() {}
}