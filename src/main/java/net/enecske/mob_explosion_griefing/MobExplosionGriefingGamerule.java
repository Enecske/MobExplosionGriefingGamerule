package net.enecske.mob_explosion_griefing;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.world.rule.GameRule;
import net.minecraft.world.rule.GameRuleCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MobExplosionGriefingGamerule implements ModInitializer {
	public static final String MOD_ID = "mob_explosion_griefing";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final GameRule<Boolean> MOB_EXPLOSION_GRIEFING =
			GameRuleBuilder.forBoolean(true).category(GameRuleCategory.MOBS).buildAndRegister(Identifier.of(MOD_ID, "mob_explosion_griefing"));

	public static final GameRule<Boolean> WITHER_GRIEFING =
			GameRuleBuilder.forBoolean(true).category(GameRuleCategory.MOBS).buildAndRegister(Identifier.of(MOD_ID, "wither_griefing"));

	public static final GameRule<Boolean> CREEPER_GRIEFING =
			GameRuleBuilder.forBoolean(true).category(GameRuleCategory.MOBS).buildAndRegister(Identifier.of(MOD_ID, "creeper_griefing"));

	public static final GameRule<Boolean> GHAST_GRIEFING =
			GameRuleBuilder.forBoolean(true).category(GameRuleCategory.MOBS).buildAndRegister(Identifier.of(MOD_ID, "ghast_griefing"));

	public static final GameRule<Boolean> ENDERMAN_GRIEFING =
			GameRuleBuilder.forBoolean(true).category(GameRuleCategory.MOBS).buildAndRegister(Identifier.of(MOD_ID, "enderman_griefing"));

	public static final GameRule<Boolean> DOOR_BREAKING_GRIEFING =
			GameRuleBuilder.forBoolean(true).category(GameRuleCategory.MOBS).buildAndRegister(Identifier.of(MOD_ID, "door_breaking_griefing"));

	public static final GameRule<Boolean> STEP_AND_DESTROY_GOAL =
			GameRuleBuilder.forBoolean(true).category(GameRuleCategory.MOBS).buildAndRegister(Identifier.of(MOD_ID, "step_and_destroy_goal"));

	@Override
	public void onInitialize() {
		LOGGER.info("MobExplosionGriefingGamerule is loaded!");
	}
}