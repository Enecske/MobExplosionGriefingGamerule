package net.enecske.mob_explosion_griefing.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.enecske.mob_explosion_griefing.MobExplosionGriefingGamerule;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.world.rule.GameRule;
import net.minecraft.world.rule.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WitherEntity.class)
public class WitherEntityMixin {
    @WrapOperation(method = "mobTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/rule/GameRules;getValue(Lnet/minecraft/world/rule/GameRule;)Ljava/lang/Object;"))
    private Object redirectBlockBreakingGriefing(GameRules instance, GameRule<Boolean> rule, Operation<Boolean> original) {
        if (rule == GameRules.DO_MOB_GRIEFING)
            return original.call(instance, MobExplosionGriefingGamerule.WITHER_GRIEFING) && original.call(instance, GameRules.DO_MOB_GRIEFING);
        return original.call(instance, rule);
    }
}
