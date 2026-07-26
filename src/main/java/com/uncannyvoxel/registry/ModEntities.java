package com.uncannyvoxel.registry;

import com.uncannyvoxel.entity.MimicEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public final class ModEntities {

    @SuppressWarnings("unchecked")
    public static final EntityType<MimicEntity> MIMIC = BuiltInRegistries.ENTITY_TYPE.register(
            ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.of("uncannyvoxel", "mimic")),
            EntityType.Builder.of(MimicEntity::new, MobCategory.MONSTER)
                    .sized(0.6f, 1.95f)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.of("uncannyvoxel", "mimic")))
    );

    public static void init() {}
}
