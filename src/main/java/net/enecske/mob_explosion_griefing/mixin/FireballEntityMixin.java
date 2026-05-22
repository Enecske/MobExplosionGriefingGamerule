package net.enecske.mob_explosion_griefing.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.enecske.mob_explosion_griefing.FireballAdditionalDataHolder;
import net.enecske.mob_explosion_griefing.MobExplosionGriefingGamerule;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FireballEntity.class)
public class FireballEntityMixin implements FireballAdditionalDataHolder {
    @Unique
    private boolean shotByGhast;

    @Inject(method = "<init>(Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;DDDI)V", at = @At("TAIL"))
    private void setShotByGhastFlagOnFireball(World world, LivingEntity owner, double velocityX, double velocityY, double velocityZ, int explosionPower, CallbackInfo ci) {
        if (owner instanceof GhastEntity) this.shotByGhast = true;
    }

    @WrapOperation(method = "onCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z"))
    private boolean redirectMobGriefing(GameRules instance, GameRules.Key<GameRules.BooleanRule> rule, Operation<Boolean> original) {
        if (rule == GameRules.DO_MOB_GRIEFING && shotByGhast)
            return original.call(instance, GameRules.DO_MOB_GRIEFING) && original.call(instance, MobExplosionGriefingGamerule.GHAST_GRIEFING);
        return original.call(instance, rule);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void addShotByGhastTag(NbtCompound nbt, CallbackInfo ci) {
        nbt.putBoolean("shotByGhast", shotByGhast);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void readShotByGhastTag(NbtCompound nbt, CallbackInfo ci) {
        this.shotByGhast = nbt.getBoolean("shotByGhast");
    }

    @Override
    public boolean mobexplosiongriefinggamerule$isShotByGhast() {
        return shotByGhast;
    }
}
