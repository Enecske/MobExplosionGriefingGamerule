package net.enecske.mob_explosion_griefing;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MobExplosionGriefingGamerule implements ModInitializer {
	public static final String MOD_ID = "mob_explosion_griefing";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	public static final GameRules.Key<GameRules.BooleanRule> MOB_EXPLOSION_GRIEFING =
			GameRuleRegistry.register("mobExplosionGriefing", GameRules.Category.MOBS, GameRuleFactory.createBooleanRule(true));

	public static final GameRules.Key<GameRules.BooleanRule> WITHER_GRIEFING =
			GameRuleRegistry.register("witherGriefing", GameRules.Category.MOBS, GameRuleFactory.createBooleanRule(true));

	@Override
	public void onInitialize() {
		LOGGER.info("MobExplosionGriefingGamerule is loaded!");
	}
}