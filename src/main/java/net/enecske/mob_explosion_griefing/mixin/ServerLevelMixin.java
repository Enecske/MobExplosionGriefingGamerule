package net.enecske.mob_explosion_griefing.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.enecske.mob_explosion_griefing.FireballAdditionalDataHolder;
import net.enecske.mob_explosion_griefing.MobExplosionGriefingGamerule;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.projectile.hurtingprojectile.LargeFireball;
import net.minecraft.world.entity.projectile.hurtingprojectile.WitherSkull;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRules;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin {
    @Shadow
    public abstract GameRules getGameRules();

    @Shadow
    protected abstract Explosion.BlockInteraction getDestroyType(GameRule<Boolean> gameRule);

    @ModifyExpressionValue(
            method = "explode",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerLevel;getDestroyType(Lnet/minecraft/world/level/gamerules/GameRule;)Lnet/minecraft/world/level/Explosion$BlockInteraction;"
            ),
            slice = @Slice(
                    from = @At(
                            value = "FIELD",
                            target = "Lnet/minecraft/world/level/gamerules/GameRules;MOB_EXPLOSION_DROP_DECAY:Lnet/minecraft/world/level/gamerules/GameRule;", opcode = Opcodes.GETSTATIC)
            )
    )
    private Explosion.BlockInteraction modifyMobExplosionGriefing(Explosion.BlockInteraction original, Entity source) {
        if (!this.getGameRules().get(GameRules.MOB_GRIEFING)) return Explosion.BlockInteraction.KEEP;

        return switch (source) {
            case WitherSkull ignored -> {
                if (this.getGameRules().get(MobExplosionGriefingGamerule.WITHER_GRIEFING))
                    yield this.getDestroyType(GameRules.MOB_EXPLOSION_DROP_DECAY);
                yield Explosion.BlockInteraction.KEEP;
            }
            case WitherBoss ignored -> {
                if (this.getGameRules().get(MobExplosionGriefingGamerule.WITHER_GRIEFING))
                    yield this.getDestroyType(GameRules.MOB_EXPLOSION_DROP_DECAY);
                yield Explosion.BlockInteraction.KEEP;
            }
            case Creeper ignored -> {
                if (this.getGameRules().get(MobExplosionGriefingGamerule.CREEPER_GRIEFING) && this.getGameRules().get(MobExplosionGriefingGamerule.MOB_EXPLOSION_GRIEFING))
                    yield this.getDestroyType(GameRules.MOB_EXPLOSION_DROP_DECAY);
                yield Explosion.BlockInteraction.KEEP;
            }
            case LargeFireball fireball -> {
                if (((FireballAdditionalDataHolder) fireball).mobexplosiongriefinggamerule$isShotByGhast()) {
                    if (this.getGameRules().get(MobExplosionGriefingGamerule.GHAST_GRIEFING) && this.getGameRules().get(MobExplosionGriefingGamerule.MOB_EXPLOSION_GRIEFING))
                        yield this.getDestroyType(GameRules.MOB_EXPLOSION_DROP_DECAY);
                    yield Explosion.BlockInteraction.KEEP;
                }
                yield original;
            }
            case null, default -> original;
        };
    }
}
