package com.uncannyvoxel.entity;

import com.uncannyvoxel.config.HorrorConfig;
import com.uncannyvoxel.registry.ModEntities;
import com.uncannyvoxel.registry.ModSoundEvents;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;
import java.util.EnumSet;

public class MimicEntity extends PathAwareEntity {

    private static final TrackedData<Boolean> SLIDING_SKIN = DataTracker.registerData(MimicEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> STUTTER_COOLDOWN = DataTracker.registerData(MimicEntity.class, TrackedDataHandlerRegistry.INTEGER);

    private int slideSkinTicks = 0;
    private int stutterTicks = 0;
    private int headRotationTicks = 0;
    private final Random random = new Random();

    public MimicEntity(EntityType<? extends PathAwareEntity> type, World world) {
        super(type, world);
        this.experiencePoints = 50;
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 40.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0)
                .add(EntityAttributes.GENERIC_ARMOR, 4.0)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.5);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new MimicStutterStepGoal(this));
        this.goalSelector.add(3, new MimicHeadRotationGoal(this));
        this.goalSelector.add(4, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.2));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 16.0f));
        this.goalSelector.add(7, new LookAroundGoal(this));

        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(2, new RevengeGoal(this));
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(SLIDING_SKIN, false);
        builder.add(STUTTER_COOLDOWN, 0);
    }

    public boolean isSlidingSkin() {
        return this.dataTracker.get(SLIDING_SKIN);
    }

    public void setSlidingSkin(boolean sliding) {
        this.dataTracker.set(SLIDING_SKIN, sliding);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.world.isClient) {
            if (isSlidingSkin()) {
                slideSkinClientTick();
            }
            if (stutterTicks > 0) {
                stutterClientTick();
            }
            if (headRotationTicks > 0) {
                headRotationClientTick();
            }
        } else {
            // Server-side logic
            if (slideSkinTicks > 0) {
                slideSkinTicks--;
                if (slideSkinTicks == 0) {
                    setSlidingSkin(false);
                }
            }

            if (this.dataTracker.get(STUTTER_COOLDOWN) > 0) {
                this.dataTracker.set(STUTTER_COOLDOWN, this.dataTracker.get(STUTTER_COOLDOWN) - 1);
            }
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        boolean damaged = super.damage(source, amount);

        if (damaged && !this.world.isClient && HorrorConfig.get().horrorEnabled) {
            // Trigger sliding skin animation on damage
            setSlidingSkin(true);
            slideSkinTicks = 60 + random.nextInt(60);
        }

        return damaged;
    }

    private void slideSkinClientTick() {
        // Client-side sliding skin animation
    }

    private void stutterClientTick() {
        stutterTicks--;
    }

    private void headRotationClientTick() {
        headRotationTicks--;
    }

    @Override
    protected void initCustomGoals() {
        // Custom goals can be added here
    }
}