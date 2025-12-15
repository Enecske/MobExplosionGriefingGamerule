package net.enecske.mob_explosion_griefing.mixin.enderman_entity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.enecske.mob_explosion_griefing.MobExplosionGriefingGamerule;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.world.rule.GameRule;
import net.minecraft.world.rule.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net.minecraft.entity.mob.EndermanEntity$PickUpBlockGoal")
public abstract class PickUpBlockGoalMixin extends Goal {
    @WrapOperation(method = "canStart", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/rule/GameRules;getValue(Lnet/minecraft/world/rule/GameRule;)Ljava/lang/Object;"))
    private Object modifyCanStart(GameRules instance, GameRule<Boolean> rule, Operation<Boolean> original) {
        return original.call(instance, MobExplosionGriefingGamerule.ENDERMAN_GRIEFING) && original.call(instance, GameRules.DO_MOB_GRIEFING);
    }
}
