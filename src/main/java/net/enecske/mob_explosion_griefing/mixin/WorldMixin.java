package net.enecske.mob_explosion_griefing.mixin;

import net.enecske.mob_explosion_griefing.MobExplosionGriefingGamerule;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public abstract class WorldMixin{
    @Shadow public abstract GameRules getGameRules();
    @Shadow protected abstract Explosion.DestructionType getDestructionType(GameRules.Key<GameRules.BooleanRule> gameRuleKey);

    @Inject(method = "createExplosion(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;Lnet/minecraft/world/explosion/ExplosionBehavior;DDDFZLnet/minecraft/world/World$ExplosionSourceType;ZLnet/minecraft/particle/ParticleEffect;Lnet/minecraft/particle/ParticleEffect;Lnet/minecraft/registry/entry/RegistryEntry;)Lnet/minecraft/world/explosion/Explosion;", at = @At("HEAD"), cancellable = true)
    private void injectGameruleLogic(
            @Nullable Entity entity,
            @Nullable DamageSource damageSource,
            @Nullable ExplosionBehavior behavior,
            double x,
            double y,
            double z,
            float power,
            boolean createFire,
            World.ExplosionSourceType explosionSourceType,
            boolean particles,
            ParticleEffect particle,
            ParticleEffect emitterParticle,
            RegistryEntry<SoundEvent> soundEvent,
            CallbackInfoReturnable<Explosion> cir
    ) {
        if (explosionSourceType != World.ExplosionSourceType.MOB) return;

        Explosion.DestructionType destructionType = this.getGameRules().getBoolean(MobExplosionGriefingGamerule.MOB_EXPLOSION_GRIEFING)
                ? this.getDestructionType(GameRules.MOB_EXPLOSION_DROP_DECAY)
                : Explosion.DestructionType.KEEP;

        if (entity instanceof WitherEntity || entity instanceof WitherSkullEntity) {
            destructionType = this.getGameRules().getBoolean(MobExplosionGriefingGamerule.WITHER_GRIEFING)
                    ? getDestructionType(GameRules.MOB_EXPLOSION_DROP_DECAY)
                    : Explosion.DestructionType.KEEP;
        }

        if (destructionType != Explosion.DestructionType.KEEP) {
            if (entity instanceof CreeperEntity) {
                destructionType = this.getGameRules().getBoolean(MobExplosionGriefingGamerule.CREEPER_GRIEFING)
                        ? getDestructionType(GameRules.MOB_EXPLOSION_DROP_DECAY)
                        : Explosion.DestructionType.KEEP;
            }

            if (entity instanceof FireballEntity) {
                destructionType = this.getGameRules().getBoolean(MobExplosionGriefingGamerule.GHAST_GRIEFING)
                        ? getDestructionType(GameRules.MOB_EXPLOSION_DROP_DECAY)
                        : Explosion.DestructionType.KEEP;
            }
        }

        Explosion explosion = new Explosion((World) (Object) this, entity, damageSource, behavior, x, y, z, power, createFire, destructionType, particle, emitterParticle, soundEvent);
        explosion.collectBlocksAndDamageEntities();
        explosion.affectWorld(particles);

        cir.setReturnValue(explosion);
    }
}
