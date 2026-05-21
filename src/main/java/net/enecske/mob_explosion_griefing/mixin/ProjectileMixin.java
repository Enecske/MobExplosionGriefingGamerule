package net.enecske.mob_explosion_griefing.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.enecske.mob_explosion_griefing.MobExplosionGriefingGamerule;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Projectile.class)
public class ProjectileMixin {
    @WrapOperation(method = "mayInteract", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/gamerules/GameRules;get(Lnet/minecraft/world/level/gamerules/GameRule;)Ljava/lang/Object;"))
    private Object redirectGameruleValueGet(GameRules instance, GameRule<Boolean> gameRule, Operation<Boolean> original) {
        if (gameRule == GameRules.MOB_GRIEFING)
            return original.call(instance, MobExplosionGriefingGamerule.MOB_EXPLOSION_GRIEFING) && original.call(instance, GameRules.MOB_GRIEFING);
        return original.call(instance, gameRule);
    }
}
