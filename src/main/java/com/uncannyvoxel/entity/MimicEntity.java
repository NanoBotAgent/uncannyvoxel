package com.uncannyvoxel.entity;

import com.uncannyvoxel.config.HorrorConfig;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.sounds.SoundSource;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;

import java.util.Random;

public class MimicEntity extends PathfinderMob {

    private static final EntityDataAccessor<Boolean> SLIDING_SKIN =
            SynchedEntityData.defineId(MimicEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> STUTTER_COOLDOWN =
            SynchedEntityData.defineId(MimicEntity.class, EntityDataSerializers.INT);

    private int slideSkinTicks = 0;
    private int stutterTicks = 0;
    private int headRotationTicks = 0;
    private final Random random = new Random();

    public MimicEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 40.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.ATTACK_DAMAGE, 8.0)
                .add(Attributes.FOLLOW_RANGE, 48.0)
                .add(Attributes.ARMOR, 4.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MimicStutterStepGoal(this));
        this.goalSelector.addGoal(3, new MimicHeadRotationGoal(this));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(5, new RandomStrollGoal(this, 0.2));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 16.0f));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SLIDING_SKIN, false);
        builder.define(STUTTER_COOLDOWN, 0);
    }

    public boolean isSlidingSkin() {
        return this.getEntityData().get(SLIDING_SKIN);
    }

    public void setSlidingSkin(boolean sliding) {
        this.getEntityData().set(SLIDING_SKIN, sliding);
    }

    public void triggerStutterStep() {
        stutterTicks = 20 + random.nextInt(20);
    }

    public void triggerHeadRotation(int ticks) {
        headRotationTicks = ticks;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
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
            if (slideSkinTicks > 0) {
                slideSkinTicks--;
                if (slideSkinTicks == 0) {
                    setSlidingSkin(false);
                }
            }

            if (this.getEntityData().get(STUTTER_COOLDOWN) > 0) {
                this.getEntityData().set(STUTTER_COOLDOWN, this.getEntityData().get(STUTTER_COOLDOWN) - 1);
            }
        }
    }

    @Override
    public void hurt(net.minecraft.world.damagesource.DamageSource source, float amount) {
        super.hurt(source, amount);

        if (!this.level().isClientSide() && HorrorConfig.get().horrorEnabled) {
            setSlidingSkin(true);
            slideSkinTicks = 60 + random.nextInt(60);
        }
    }

    private void slideSkinClientTick() {
    }

    private void stutterClientTick() {
        stutterTicks--;
    }

    private void headRotationClientTick() {
        headRotationTicks--;
    }
}
