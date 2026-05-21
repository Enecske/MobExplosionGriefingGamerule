package net.enecske.mob_explosion_griefing.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.enecske.mob_explosion_griefing.MobExplosionGriefingGamerule;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WitherBoss.class)
public class WitherBossMixin {
    @WrapOperation(method = "customServerAiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/gamerules/GameRules;get(Lnet/minecraft/world/level/gamerules/GameRule;)Ljava/lang/Object;"))
    private Object redirectGameruleValueGet(GameRules instance, GameRule<Boolean> gameRule, Operation<Boolean> original) {
        if (gameRule == GameRules.MOB_GRIEFING)
            return original.call(instance, MobExplosionGriefingGamerule.WITHER_GRIEFING) && original.call(instance, GameRules.MOB_GRIEFING);
        return original.call(instance, gameRule);
    }
}
