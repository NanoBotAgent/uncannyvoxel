package com.uncannyvoxel.horror;

import com.uncannyvoxel.config.HorrorConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

public class PeripheralGlitchController {

    private static final Random RANDOM = new Random();
    private static float currentIntensity = 0.0f;
    private static float targetIntensity = 0.0f;
    private static long lastTriggerTick = 0;

    public static void tick(MinecraftClient client) {
        if (!HorrorConfig.get().horrorEnabled || !HorrorConfig.get().peripheralGlitchEnabled) {
            currentIntensity = 0.0f;
            targetIntensity = 0.0f;
            return;
        }

        if (client.player == null || client.world == null) return;

        // Check for nearby entities not being looked at
        Entity target = findPeripheralEntity(client);
        if (target != null) {
            float distance = (float) client.player.distanceTo(target);
            float maxDistance = 16.0f;
            float proximityFactor = Math.max(0, 1.0f - distance / maxDistance);

            if (RANDOM.nextFloat() < HorrorConfig.get().peripheralGlitchChance * proximityFactor) {
                triggerGlitch(proximityFactor);
            }
        }

        // Smooth intensity
        if (currentIntensity < targetIntensity) {
            currentIntensity = Math.min(currentIntensity + 0.02f, targetIntensity);
        } else if (currentIntensity > targetIntensity) {
            currentIntensity = Math.max(currentIntensity - 0.01f, targetIntensity);
        }
    }

    private static Entity findPeripheralEntity(MinecraftClient client) {
        Vec3d lookDir = client.player.getRotationVec(1.0f);
        Entity bestTarget = null;
        float bestDot = 0.0f;

        for (Entity entity : client.world.getEntities()) {
            if (entity == client.player) continue;
            if (!(entity instanceof net.minecraft.entity.LivingEntity)) continue;

            Vec3d toEntity = entity.getPos().subtract(client.player.getPos()).normalize();
            float dot = (float) lookDir.dotProduct(toEntity);

            // Entity is in peripheral vision (not directly looked at)
            if (dot < 0.5f && dot > -0.5f) {
                if (dot > bestDot) {
                    bestDot = dot;
                    bestTarget = entity;
                }
            }
        }

        return bestTarget;
    }

    private static void triggerGlitch(float proximityFactor) {
        targetIntensity = Math.min(HorrorConfig.get().peripheralGlitchIntensity * proximityFactor, 1.0f);
        lastTriggerTick = MinecraftClient.getInstance().world.getTime();
    }

    public static float getCurrentIntensity() {
        return currentIntensity;
    }

    public static void reset() {
        currentIntensity = 0.0f;
        targetIntensity = 0.0f;
    }
}