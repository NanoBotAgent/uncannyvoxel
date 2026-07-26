package com.uncannyvoxel.horror;

import com.uncannyvoxel.config.HorrorConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class DepthOfFieldDreadController {

    private static float currentDreadIntensity = 0.0f;

    public static void tick(Minecraft client) {
        if (!HorrorConfig.get().horrorEnabled || !HorrorConfig.get().depthOfFieldDreadEnabled) {
            currentDreadIntensity = 0.0f;
            return;
        }

        if (client.player == null || client.level == null) return;

        Entity behindEntity = findEntityBehind(client);
        if (behindEntity != null) {
            float distance = (float) client.player.distanceTo(behindEntity);
            float maxDistance = 8.0f;

            if (distance < maxDistance) {
                float proximityFactor = 1.0f - distance / maxDistance;
                currentDreadIntensity = Math.min(proximityFactor, 1.0f);
            } else {
                currentDreadIntensity = Math.max(currentDreadIntensity - 0.02f, 0.0f);
            }
        } else {
            currentDreadIntensity = Math.max(currentDreadIntensity - 0.02f, 0.0f);
        }
    }

    private static Entity findEntityBehind(Minecraft client) {
        Vec3 lookDir = client.player.getLookAngle();
        Vec3 backDir = lookDir.scale(-1);
        Entity bestTarget = null;
        float bestDot = 0.0f;

        for (Entity entity : client.level.entitiesForRendering()) {
            if (entity == client.player) continue;
            if (!(entity instanceof net.minecraft.world.entity.LivingEntity)) continue;

            Vec3 toEntity = entity.position().subtract(client.player.position()).normalize();
            float dot = (float) backDir.dot(toEntity);

            if (dot > 0.7f && dot > bestDot) {
                bestDot = dot;
                bestTarget = entity;
            }
        }

        return bestTarget;
    }

    public static float getDreadIntensity() {
        return currentDreadIntensity;
    }

    public static void reset() {
        currentDreadIntensity = 0.0f;
    }
}
