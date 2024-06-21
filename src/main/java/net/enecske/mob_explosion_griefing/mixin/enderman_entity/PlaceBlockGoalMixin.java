package net.enecske.mob_explosion_griefing.mixin.enderman_entity;

import net.enecske.mob_explosion_griefing.MobExplosionGriefingGamerule;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.EndermanEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(targets = "net.minecraft.entity.mob.EndermanEntity$PlaceBlockGoal")
public class PlaceBlockGoalMixin extends Goal {
    @Final @Shadow private EndermanEntity enderman;

    @Override
    public boolean canStart() {
        if (this.enderman.getCarriedBlock() == null) {
            return false;
        } else {
            return this.enderman.getWorld().getGameRules().getBoolean(MobExplosionGriefingGamerule.ENDERMAN_GRIEFING) && this.enderman.getRandom().nextInt(toGoalTicks(2000)) == 0;
        }
    }
}
