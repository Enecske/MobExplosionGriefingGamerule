package net.enecske.mob_explosion_griefing.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.enecske.mob_explosion_griefing.MobExplosionGriefingGamerule;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.StepAndDestroyBlockGoal;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(StepAndDestroyBlockGoal.class)
public class StepAndDestroyBlockGoalMixin {
    @Shadow
    @Final
    private Block targetBlock;

    @WrapOperation(method = "canStart", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z"))
    private boolean redirectGameruleValueGet(GameRules instance, GameRules.Key<GameRules.BooleanRule> rule, Operation<Boolean> original) {
        if (rule == GameRules.DO_MOB_GRIEFING && this.targetBlock.equals(Blocks.TURTLE_EGG))
            return original.call(instance, GameRules.DO_MOB_GRIEFING) && original.call(instance, MobExplosionGriefingGamerule.TURTLE_EGG_GRIEFING);
        return original.call(instance, rule);
    }
}
