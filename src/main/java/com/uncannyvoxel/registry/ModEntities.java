package com.uncannyvoxel.registry;

import com.uncannyvoxel.entity.MimicEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public final class ModEntities {

    public static final EntityType<MimicEntity> MIMIC = BuiltInRegistries.ENTITY_TYPE.register(
            ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("uncannyvoxel", "mimic")),
            EntityType.Builder.of(MimicEntity::new, MobCategory.MONSTER)
                    .sized(0.6f, 1.95f)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("uncannyvoxel", "mimic")))
    );

    public static void init() {}
}
