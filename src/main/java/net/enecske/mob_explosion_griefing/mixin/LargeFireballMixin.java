package net.enecske.mob_explosion_griefing.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.enecske.mob_explosion_griefing.FireballAdditionalDataHolder;
import net.enecske.mob_explosion_griefing.MobExplosionGriefingGamerule;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.hurtingprojectile.LargeFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LargeFireball.class)
public abstract class LargeFireballMixin extends Projectile implements FireballAdditionalDataHolder {
    @Unique
    private boolean shotByGhast = false;

    public LargeFireballMixin(EntityType<? extends Projectile> type, Level level) {
        super(type, level);
    }

    @Inject(method = "<init>(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/phys/Vec3;I)V", at = @At("TAIL"))
    private void setShotByGhastFlagOnFireball(Level level, LivingEntity mob, Vec3 direction, int explosionPower, CallbackInfo ci) {
        if (mob instanceof Ghast) this.shotByGhast = true;
    }

    @WrapOperation(method = "onHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/gamerules/GameRules;get(Lnet/minecraft/world/level/gamerules/GameRule;)Ljava/lang/Object;"))
    private Object redirectGameruleValueGet(GameRules instance, GameRule<Boolean> gameRule, Operation<Boolean> original) {
        if (gameRule == GameRules.MOB_GRIEFING && shotByGhast)
            return original.call(instance, MobExplosionGriefingGamerule.GHAST_GRIEFING) && original.call(instance, GameRules.MOB_GRIEFING);
        return original.call(instance, gameRule);
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void addShotByGhastTag(ValueOutput output, CallbackInfo ci) {
        output.putBoolean("shotByGhast", this.shotByGhast);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void readShotByGhastTag(final ValueInput input, CallbackInfo ci) {
        this.shotByGhast = input.getBooleanOr("shotByGhast", false);
    }

    @Override
    public boolean mobexplosiongriefinggamerule$isShotByGhast() {
        return this.shotByGhast;
    }
}
