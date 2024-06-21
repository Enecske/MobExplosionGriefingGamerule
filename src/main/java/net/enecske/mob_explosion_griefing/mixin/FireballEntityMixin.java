package net.enecske.mob_explosion_griefing.mixin;

import net.enecske.mob_explosion_griefing.MobExplosionGriefingGamerule;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FireballEntity.class)
public class FireballEntityMixin {
    @Redirect(method = "onCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z"))
    private boolean redirectMobGriefing(GameRules instance, GameRules.Key<GameRules.BooleanRule> rule) {
        if (rule == GameRules.DO_MOB_GRIEFING)
            return instance.getBoolean(MobExplosionGriefingGamerule.GHAST_GRIEFING);
        return instance.getBoolean(rule);
    }
}
