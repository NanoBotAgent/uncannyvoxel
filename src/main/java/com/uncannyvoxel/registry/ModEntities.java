package com.uncannyvoxel.registry;

import com.uncannyvoxel.entity.MimicEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public final class ModEntities {

    public static final EntityType<MimicEntity> MIMIC = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath("uncannyvoxel", "mimic")),
            EntityType.Builder.of(MimicEntity::new, MobCategory.MONSTER)
                    .dimensions(0.6f, 1.95f)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath("uncannyvoxel", "mimic")))
    );

    public static void init() {}
}
