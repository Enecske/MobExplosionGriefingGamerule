package net.enecske.mob_explosion_griefing.mixin;

import net.enecske.mob_explosion_griefing.MobExplosionGriefingGamerule;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public abstract class WorldMixin{
    @Shadow public abstract GameRules getGameRules();
    @Shadow protected abstract Explosion.DestructionType getDestructionType(GameRules.Key<GameRules.BooleanRule> gameRuleKey);

    @Redirect(method = "createExplosion(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;Lnet/minecraft/world/explosion/ExplosionBehavior;DDDFZLnet/minecraft/world/World$ExplosionSourceType;Z)Lnet/minecraft/world/explosion/Explosion;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z"))
    private boolean redirectMobGriefing(GameRules instance, GameRules.Key<GameRules.BooleanRule> rule) {
        if (rule == GameRules.DO_MOB_GRIEFING)
            return instance.getBoolean(MobExplosionGriefingGamerule.MOB_EXPLOSION_GRIEFING);
        return instance.getBoolean(rule);
    }

    @Inject(method = "createExplosion(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;Lnet/minecraft/world/explosion/ExplosionBehavior;DDDFZLnet/minecraft/world/World$ExplosionSourceType;Z)Lnet/minecraft/world/explosion/Explosion;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/explosion/Explosion;<init>(Lnet/minecraft/world/World;Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;Lnet/minecraft/world/explosion/ExplosionBehavior;DDDFZLnet/minecraft/world/explosion/Explosion$DestructionType;)V"), cancellable = true)
    private void injectGameruleLogic(Entity entity, DamageSource damageSource, ExplosionBehavior behavior, double x, double y, double z, float power, boolean createFire, World.ExplosionSourceType explosionSourceType, boolean particles, CallbackInfoReturnable<Explosion> cir) {
        if (entity instanceof WitherEntity || entity instanceof WitherSkullEntity) {
            Explosion.DestructionType destructionType = this.getGameRules().getBoolean(MobExplosionGriefingGamerule.WITHER_GRIEFING) ? getDestructionType(GameRules.MOB_EXPLOSION_DROP_DECAY) : Explosion.DestructionType.KEEP;

            Explosion explosion = new Explosion((World) (Object) this, entity, damageSource, behavior, x, y, z, power, createFire, destructionType);
            explosion.collectBlocksAndDamageEntities();
            explosion.affectWorld(particles);

            cir.setReturnValue(explosion);
        }
    }
}
