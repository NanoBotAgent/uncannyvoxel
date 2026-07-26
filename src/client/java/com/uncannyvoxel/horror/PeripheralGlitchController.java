package com.uncannyvoxel.horror;

import com.uncannyvoxel.config.HorrorConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class PeripheralGlitchController {

    private static final Random RANDOM = new Random();
    private static float currentIntensity = 0.0f;
    private static float targetIntensity = 0.0f;
    private static long lastTriggerTick = 0;

    public static void tick(Minecraft client) {
        if (!HorrorConfig.get().horrorEnabled || !HorrorConfig.get().peripheralGlitchEnabled) {
            currentIntensity = 0.0f;
            targetIntensity = 0.0f;
            return;
        }

        if (client.player == null || client.level == null) return;

        Entity target = findPeripheralEntity(client);
        if (target != null) {
            float distance = (float) client.player.distanceTo(target);
            float maxDistance = 16.0f;
            float proximityFactor = Math.max(0, 1.0f - distance / maxDistance);

            if (RANDOM.nextFloat() < HorrorConfig.get().peripheralGlitchChance * proximityFactor) {
                triggerGlitch(proximityFactor);
            }
        }

        if (currentIntensity < targetIntensity) {
            currentIntensity = Math.min(currentIntensity + 0.02f, targetIntensity);
        } else if (currentIntensity > targetIntensity) {
            currentIntensity = Math.max(currentIntensity - 0.01f, targetIntensity);
        }
    }

    private static Entity findPeripheralEntity(Minecraft client) {
        Vec3 lookDir = client.player.getLookAngle();
        Entity bestTarget = null;
        float bestDot = 0.0f;

        for (Entity entity : client.level.entitiesForRendering()) {
            if (entity == client.player) continue;
            if (!(entity instanceof net.minecraft.world.entity.LivingEntity)) continue;

            Vec3 toEntity = entity.position().subtract(client.player.position()).normalize();
            float dot = (float) lookDir.dot(toEntity);

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
        lastTriggerTick = Minecraft.getInstance().level.getGameTime();
    }

    public static float getCurrentIntensity() {
        return currentIntensity;
    }

    public static void reset() {
        currentIntensity = 0.0f;
        targetIntensity = 0.0f;
    }
}
