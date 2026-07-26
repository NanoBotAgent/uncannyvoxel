package com.uncannyvoxel.item;

import com.uncannyvoxel.config.HorrorConfig;
import com.uncannyvoxel.registry.ModSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.UUID;

public class LumenScalpelItem extends Item {

    private static final Identifier MAX_HEALTH_MODIFIER_ID = Identifier.of("uncannyvoxel", "lumen_scalpel_drain");
    private static final float MAX_HEALTH_DRAIN = 0.5f;
    private static final float SAFE_FLOOR = 0.5f;

    public LumenScalpelItem(Properties properties) {
        super(properties.durability(100));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, EquipmentSlot slot) {
        @SuppressWarnings("unused") Level unused = level;
        if (entity instanceof Player player) {
            if (slot == EquipmentSlot.MAINHAND && stack.getDamageValue() < stack.getMaxDamage()) {
                applyEffect(player);
            } else {
                removeEffect(player);
            }
        }
    }

    private void applyEffect(Player player) {
        double currentMaxHealth = player.getAttributeValue(Attributes.MAX_HEALTH);
        double drainAmount = currentMaxHealth * MAX_HEALTH_DRAIN;
        double newMaxHealth = Math.max(currentMaxHealth - drainAmount, currentMaxHealth * SAFE_FLOOR);

        AttributeInstance attrInstance = player.getAttribute(Attributes.MAX_HEALTH);
        if (attrInstance != null && !attrInstance.hasModifier(MAX_HEALTH_MODIFIER_ID)) {
            AttributeModifier modifier = new AttributeModifier(
                    MAX_HEALTH_MODIFIER_ID,
                    -(currentMaxHealth - newMaxHealth),
                    Operation.ADD_VALUE
            );
            attrInstance.addTransientModifier(modifier);
        }
    }

    private void removeEffect(Player player) {
        AttributeInstance attrInstance = player.getAttribute(Attributes.MAX_HEALTH);
        if (attrInstance != null) {
            attrInstance.removeModifier(MAX_HEALTH_MODIFIER_ID);
        }
    }
}
