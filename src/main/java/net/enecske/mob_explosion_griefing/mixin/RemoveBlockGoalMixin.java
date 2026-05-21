package net.enecske.mob_explosion_griefing.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.enecske.mob_explosion_griefing.MobExplosionGriefingGamerule;
import net.minecraft.world.entity.ai.goal.RemoveBlockGoal;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRules;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(RemoveBlockGoal.class)
public class RemoveBlockGoalMixin {
    @Shadow
    @Final
    private Block blockToRemove;

    @WrapOperation(method = "canUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/gamerules/GameRules;get(Lnet/minecraft/world/level/gamerules/GameRule;)Ljava/lang/Object;"))
    public Object redirectGameruleValueGet(GameRules instance, GameRule<Boolean> gameRule, Operation<Boolean> original) {
        if (gameRule == GameRules.MOB_GRIEFING && this.blockToRemove.equals(Blocks.TURTLE_EGG))
            return original.call(instance, GameRules.MOB_GRIEFING) && original.call(instance, MobExplosionGriefingGamerule.TURTLE_EGG_GRIEFING);
        else return original.call(instance, gameRule);
    }
}
