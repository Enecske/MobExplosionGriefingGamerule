package net.enecske.mob_explosion_griefing.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.enecske.mob_explosion_griefing.MobExplosionGriefingGamerule;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.world.rule.GameRule;
import net.minecraft.world.rule.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ProjectileEntity.class)
public class ProjectileEntityMixin {
    @WrapOperation(method = "canModifyAt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/rule/GameRules;getValue(Lnet/minecraft/world/rule/GameRule;)Ljava/lang/Object;"))
    private Object redirectMobGriefing(GameRules instance, GameRule<Boolean> rule, Operation<Boolean> original) {
        if (rule == GameRules.DO_MOB_GRIEFING)
            return original.call(instance, MobExplosionGriefingGamerule.MOB_EXPLOSION_GRIEFING) && original.call(instance, GameRules.DO_MOB_GRIEFING);
        return original.call(instance, rule);
    }
}
