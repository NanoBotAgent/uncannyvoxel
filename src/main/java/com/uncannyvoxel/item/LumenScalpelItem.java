package com.uncannyvoxel.item;

import com.uncannyvoxel.config.HorrorConfig;
import com.uncannyvoxel.registry.ModSoundEvents;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

public class LumenScalpelItem extends Item {

    private static final Identifier MAX_HEALTH_MODIFIER_ID = Identifier.of("uncannyvoxel", "lumen_scalpel_drain");
    private static final float MAX_HEALTH_DRAIN = 0.5f; // 50% max health drain
    private static final float SAFE_FLOOR = 0.5f; // Minimum 50% of max health

    public LumenScalpelItem(Settings settings) {
        super(settings.maxDamage(100));
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerWorld world, net.minecraft.entity.Entity entity, EquipmentSlot slot) {
        if (entity instanceof PlayerEntity player) {
            if (slot == EquipmentSlot.MAINHAND && stack.getDamage() < stack.getMaxDamage()) {
                applyEffect(player);
            } else {
                removeEffect(player);
            }
        }
        super.inventoryTick(stack, world, entity, slot);
    }

    private void applyEffect(PlayerEntity player) {
        float currentMaxHealth = player.getAttributeValue(EntityAttributes.GENERIC_MAX_HEALTH);
        float drainAmount = currentMaxHealth * MAX_HEALTH_DRAIN;

        // Don't drain below safe floor
        float newMaxHealth = Math.max(currentMaxHealth - drainAmount, currentMaxHealth * SAFE_FLOOR);

        // Apply modifier if not already applied
        if (!player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).hasModifier(MAX_HEALTH_MODIFIER_ID)) {
            EntityAttributeModifier modifier = new EntityAttributeModifier(
                    MAX_HEALTH_MODIFIER_ID,
                    -(currentMaxHealth - newMaxHealth),
                    Operation.ADD_VALUE
            );
            player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).addPersistentModifier(modifier);
        }
    }

    private void removeEffect(PlayerEntity player) {
        player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).removeModifier(MAX_HEALTH_MODIFIER_ID);
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, net.minecraft.entity.LivingEntity entity) {
        if (entity instanceof PlayerEntity player && !player.getWorld().isClient) {
            // Reveal invisible entities in radius
            revealInvisibleEntities(player);
        }
        return super.onEntitySwing(stack, entity);
    }

    private void revealInvisibleEntities(PlayerEntity player) {
        World world = player.getWorld();
        double radius = 20.0;

        world.getEntitiesByClass(net.minecraft.entity.LivingEntity.class,
                player.getBoundingBox().expand(radius),
                e -> e != player && e.hasStatusEffect(StatusEffects.INVISIBILITY))
                .forEach(e -> {
                    e.removeStatusEffect(StatusEffects.INVISIBILITY);
                    world.playSound(null, e.getBlockPos(), ModSoundEvents.MIRROR_WHISPER,
                            net.minecraft.sound.SoundCategory.PLAYERS, 0.5f, 1.2f);
                });
    }
}