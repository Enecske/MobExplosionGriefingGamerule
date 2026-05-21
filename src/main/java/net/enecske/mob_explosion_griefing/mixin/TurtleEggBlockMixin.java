package net.enecske.mob_explosion_griefing.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.enecske.mob_explosion_griefing.MobExplosionGriefingGamerule;
import net.minecraft.world.level.block.TurtleEggBlock;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TurtleEggBlock.class)
public class TurtleEggBlockMixin {
    @WrapOperation(method = "canDestroyEgg", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/gamerules/GameRules;get(Lnet/minecraft/world/level/gamerules/GameRule;)Ljava/lang/Object;"))
    public Object redirectGameruleValueGet(GameRules instance, GameRule<Boolean> gameRule, Operation<Boolean> original) {
        if (gameRule == GameRules.MOB_GRIEFING)
            return original.call(instance, GameRules.MOB_GRIEFING) && original.call(instance, MobExplosionGriefingGamerule.TURTLE_EGG_GRIEFING);
        else return original.call(instance, gameRule);
    }
}
