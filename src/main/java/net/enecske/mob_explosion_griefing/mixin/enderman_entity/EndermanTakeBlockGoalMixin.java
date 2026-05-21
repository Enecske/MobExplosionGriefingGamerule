package net.enecske.mob_explosion_griefing.mixin.enderman_entity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.enecske.mob_explosion_griefing.MobExplosionGriefingGamerule;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net.minecraft.world.entity.monster.EnderMan$EndermanTakeBlockGoal")
public abstract class EndermanTakeBlockGoalMixin extends Goal {
    @WrapOperation(method = "canUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/gamerules/GameRules;get(Lnet/minecraft/world/level/gamerules/GameRule;)Ljava/lang/Object;"))
    private Object redirectGameruleValueGet(GameRules instance, GameRule<Boolean> gameRule, Operation<Boolean> original) {
        return original.call(instance, MobExplosionGriefingGamerule.ENDERMAN_GRIEFING) && instance.get(GameRules.MOB_GRIEFING);
    }
}
