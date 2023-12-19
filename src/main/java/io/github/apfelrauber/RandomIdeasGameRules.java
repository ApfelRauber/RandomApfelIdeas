package io.github.apfelrauber;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class RandomIdeasGameRules {
    public static GameRules.Key<GameRules.IntRule> MAX_PLAYER_BOATS;

    public static void setupGamerules() {
        MAX_PLAYER_BOATS = GameRuleRegistry.register("maxPlayerBoats", GameRules.Category.PLAYER, GameRuleFactory.createIntRule(30, 0, 1000));
    }
}
