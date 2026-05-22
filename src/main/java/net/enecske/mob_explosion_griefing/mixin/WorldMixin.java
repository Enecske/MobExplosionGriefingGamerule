package net.enecske.mob_explosion_griefing.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.enecske.mob_explosion_griefing.FireballAdditionalDataHolder;
import net.enecske.mob_explosion_griefing.MobExplosionGriefingGamerule;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(World.class)
public abstract class WorldMixin {
    @Shadow public abstract GameRules getGameRules();
    @Shadow protected abstract Explosion.DestructionType getDestructionType(GameRules.Key<GameRules.BooleanRule> gameRuleKey);

    @ModifyExpressionValue(
            method = "createExplosion(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;Lnet/minecraft/world/explosion/ExplosionBehavior;DDDFZLnet/minecraft/world/World$ExplosionSourceType;Z)Lnet/minecraft/world/explosion/Explosion;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;getDestructionType(Lnet/minecraft/world/GameRules$Key;)Lnet/minecraft/world/explosion/Explosion$DestructionType;"),
            slice = @Slice(
                    from = @At(
                            value = "FIELD",
                            target = "Lnet/minecraft/world/GameRules;MOB_EXPLOSION_DROP_DECAY:Lnet/minecraft/world/GameRules$Key;", opcode = Opcodes.GETSTATIC)
            )
    )
    private Explosion.DestructionType modifyMobExplosionGriefing(Explosion.DestructionType original, Entity source) {
        if (!this.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) return Explosion.DestructionType.KEEP;

        if (source instanceof WitherSkullEntity || source instanceof WitherEntity) {
            if (this.getGameRules().getBoolean(MobExplosionGriefingGamerule.WITHER_GRIEFING) && this.getGameRules().getBoolean(MobExplosionGriefingGamerule.MOB_EXPLOSION_GRIEFING))
                return this.getDestructionType(GameRules.MOB_EXPLOSION_DROP_DECAY);
            return Explosion.DestructionType.KEEP;
        }
        if (source instanceof CreeperEntity) {
            if (this.getGameRules().getBoolean(MobExplosionGriefingGamerule.CREEPER_GRIEFING) && this.getGameRules().getBoolean(MobExplosionGriefingGamerule.MOB_EXPLOSION_GRIEFING))
                return this.getDestructionType(GameRules.MOB_EXPLOSION_DROP_DECAY);
            return Explosion.DestructionType.KEEP;
        }
        if (source instanceof FireballEntity fireball && ((FireballAdditionalDataHolder) fireball).mobexplosiongriefinggamerule$isShotByGhast()) {
            if (this.getGameRules().getBoolean(MobExplosionGriefingGamerule.GHAST_GRIEFING) && this.getGameRules().getBoolean(MobExplosionGriefingGamerule.MOB_EXPLOSION_GRIEFING))
                return this.getDestructionType(GameRules.MOB_EXPLOSION_DROP_DECAY);
            return Explosion.DestructionType.KEEP;
        }

        return original;
    }
}
