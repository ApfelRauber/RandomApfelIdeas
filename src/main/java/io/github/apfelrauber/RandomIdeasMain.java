package io.github.apfelrauber;

import io.github.apfelrauber.event.AttackBlockHandler;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RandomIdeasMain implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("random-apfel-ideas");

	@Override
	public void onInitialize() {
		LOGGER.info("Random Apfel Ideas Initialize!");

		AttackBlockCallback.EVENT.register(new AttackBlockHandler());
	}
}