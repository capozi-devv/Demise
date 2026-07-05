package net.capozi.demise.common;

import net.capozi.demise.Demise;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.minecraft.world.rule.GameRule;
import net.minecraft.world.rule.GameRuleCategory;
import net.minecraft.world.rule.GameRules;

public class GameruleRegistry {
    public static final GameRule<Boolean> CREATE_GRAVE = GameRuleBuilder.forBoolean(true).category(GameRuleCategory.PLAYER).buildAndRegister(Demise.id("create_graves"));
    public static void register() {}
}
