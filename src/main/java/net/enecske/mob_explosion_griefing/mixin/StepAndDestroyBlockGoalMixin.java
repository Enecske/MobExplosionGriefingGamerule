package net.enecske.mob_explosion_griefing.mixin;

import net.enecske.mob_explosion_griefing.MobExplosionGriefingGamerule;
import net.minecraft.entity.ai.goal.StepAndDestroyBlockGoal;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(StepAndDestroyBlockGoal.class)
public class StepAndDestroyBlockGoalMixin {
    @Redirect(method = "canStart", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z"))
    public boolean redirectGameruleQuery(GameRules instance, GameRules.Key<GameRules.BooleanRule> rule) {
        if (rule == GameRules.DO_MOB_GRIEFING)
            return instance.getBoolean(GameRules.DO_MOB_GRIEFING) && instance.getBoolean(MobExplosionGriefingGamerule.STEP_AND_DESTROY_GOAL);
        else return instance.getBoolean(rule);
    }
}
