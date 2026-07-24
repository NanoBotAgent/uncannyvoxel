package com.uncannyvoxel.horror;

import com.uncannyvoxel.config.HorrorConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class DepthOfFieldDreadController {

    private static float currentDreadIntensity = 0.0f;

    public static void tick(MinecraftClient client) {
        if (!HorrorConfig.get().horrorEnabled || !HorrorConfig.get().depthOfFieldDreadEnabled) {
            currentDreadIntensity = 0.0f;
            return;
        }

        if (client.player == null || client.world == null) return;

        // Check for entity directly behind player
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

    private static Entity findEntityBehind(MinecraftClient client) {
        Vec3d lookDir = client.player.getRotationVec(1.0f);
        Vec3d backDir = lookDir.multiply(-1);
        Entity bestTarget = null;
        float bestDot = 0.0f;

        for (Entity entity : client.world.getEntities()) {
            if (entity == client.player) continue;
            if (!(entity instanceof net.minecraft.entity.LivingEntity)) continue;

            Vec3d toEntity = entity.getPos().subtract(client.player.getPos()).normalize();
            float dot = (float) backDir.dotProduct(toEntity);

            if (dot > 0.7f && dot > bestDot) { // Within ~45 degrees behind
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