package net.enecske.mob_explosion_griefing.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.enecske.mob_explosion_griefing.MobExplosionGriefingGamerule;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameRules;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {
    @Shadow
    public abstract GameRules getGameRules();

    @Shadow
    protected abstract Explosion.DestructionType getDestructionType(GameRules.Key<GameRules.BooleanRule> decayRule);

    @ModifyExpressionValue(
            method = "createExplosion",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/world/ServerWorld;getDestructionType(Lnet/minecraft/world/GameRules$Key;)Lnet/minecraft/world/explosion/Explosion$DestructionType;"
            ),
            slice = @Slice(
                    from = @At(
                            value = "FIELD",
                            target = "Lnet/minecraft/world/GameRules;MOB_EXPLOSION_DROP_DECAY:Lnet/minecraft/world/GameRules$Key;"
                    )
            )
    )
    private Explosion.DestructionType modifyMobExplosionGriefing(Explosion.DestructionType original, @Local(argsOnly = true) Entity entity) {
        if (!this.getGameRules().getBoolean(MobExplosionGriefingGamerule.MOB_EXPLOSION_GRIEFING))
            return Explosion.DestructionType.KEEP;
        return switch (entity) {
            case WitherSkullEntity ignored -> {
                if (this.getGameRules().getBoolean(MobExplosionGriefingGamerule.WITHER_GRIEFING))
                    yield this.getDestructionType(GameRules.MOB_EXPLOSION_DROP_DECAY);
                yield Explosion.DestructionType.KEEP;
            }
            case WitherEntity ignored -> {
                if (this.getGameRules().getBoolean(MobExplosionGriefingGamerule.WITHER_GRIEFING))
                    yield this.getDestructionType(GameRules.MOB_EXPLOSION_DROP_DECAY);
                yield Explosion.DestructionType.KEEP;
            }
            case CreeperEntity ignored -> {
                if (this.getGameRules().getBoolean(MobExplosionGriefingGamerule.CREEPER_GRIEFING))
                    yield this.getDestructionType(GameRules.MOB_EXPLOSION_DROP_DECAY);
                yield Explosion.DestructionType.KEEP;
            }
            case FireballEntity fireball -> {
                if (fireball.getOwner() instanceof GhastEntity && this.getGameRules().getBoolean(MobExplosionGriefingGamerule.GHAST_GRIEFING))
                    yield this.getDestructionType(GameRules.MOB_EXPLOSION_DROP_DECAY);
                yield Explosion.DestructionType.KEEP;
            }
            default -> original;
        };

    }
}
