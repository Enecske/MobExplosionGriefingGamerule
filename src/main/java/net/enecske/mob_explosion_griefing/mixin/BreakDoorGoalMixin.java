package net.enecske.mob_explosion_griefing.mixin;

import net.enecske.mob_explosion_griefing.MobExplosionGriefingGamerule;
import net.minecraft.entity.ai.goal.BreakDoorGoal;
import net.minecraft.world.rule.GameRule;
import net.minecraft.world.rule.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BreakDoorGoal.class)
public class BreakDoorGoalMixin {
    @Redirect(method = "canStart", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/rule/GameRules;getValue(Lnet/minecraft/world/rule/GameRule;)Ljava/lang/Object;"))
    public Object redirectGameruleQuery(GameRules instance, GameRule<Boolean> rule) {
        if (rule == GameRules.DO_MOB_GRIEFING)
            return instance.getValue(GameRules.DO_MOB_GRIEFING) && instance.getValue(MobExplosionGriefingGamerule.DOOR_BREAKING_GRIEFING);
        else return instance.getValue(rule);
    }
}
