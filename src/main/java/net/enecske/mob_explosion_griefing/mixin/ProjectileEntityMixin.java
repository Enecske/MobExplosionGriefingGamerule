package net.enecske.mob_explosion_griefing.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.enecske.mob_explosion_griefing.MobExplosionGriefingGamerule;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ProjectileEntity.class)
public class ProjectileEntityMixin {
    @WrapOperation(method = "canModifyAt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z"))
    private boolean redirectMobGriefing(GameRules instance, GameRules.Key<GameRules.BooleanRule> rule, Operation<Boolean> original) {
        if (rule == GameRules.DO_MOB_GRIEFING)
            return original.call(instance, MobExplosionGriefingGamerule.MOB_EXPLOSION_GRIEFING) && original.call(instance, GameRules.DO_MOB_GRIEFING);
        return original.call(instance, rule);
    }
}
