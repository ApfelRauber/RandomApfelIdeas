package io.github.apfelrauber;

import io.github.apfelrauber.block.ModBlocks;
import io.github.apfelrauber.block.entity.ModBlockEntities;
import io.github.apfelrauber.event.AttackBlockHandler;
import io.github.apfelrauber.screen.ModScreenHandlers;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RandomIdeasMain implements ModInitializer {
	public static final String MOD_ID = "random-apfel-ideas";
    public static final Logger LOGGER = LoggerFactory.getLogger("random-apfel-ideas");

	@Override
	public void onInitialize() {
		LOGGER.info("Random Apfel Ideas Initialize!");

		AttackBlockCallback.EVENT.register(new AttackBlockHandler());

		ModBlocks.registerModBlocks();
		ModBlockEntities.registerBlockEntities();

		ModScreenHandlers.registerScreenHandlers();
	}
}