package com.uncannyvoxel.registry;

import com.uncannyvoxel.entity.MimicEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public final class ModEntities {

    @SuppressWarnings("unchecked")
    public static final EntityType<MimicEntity> MIMIC = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath("uncannyvoxel", "mimic"),
            EntityType.Builder.<MimicEntity>of(MimicEntity::new, MobCategory.MONSTER)
                    .dimensions(EntityDimensions.fixed(0.6f, 1.95f))
                    .build()
    );

    public static void init() {}
}
