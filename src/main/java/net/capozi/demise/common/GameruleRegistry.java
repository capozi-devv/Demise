package net.capozi.demise.common;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class GameruleRegistry {
    public static final GameRules.Key<GameRules.BooleanRule> SAVE_TRINKETS = GameRuleRegistry.register("saveTrinkets", GameRules.Category.PLAYER, GameRuleFactory.createBooleanRule(true));
    public static final GameRules.Key<GameRules.BooleanRule> CREATE_GRAVE = GameRuleRegistry.register("createGraves", GameRules.Category.PLAYER, GameRuleFactory.createBooleanRule(true));

    public static void register() {

    }
}
