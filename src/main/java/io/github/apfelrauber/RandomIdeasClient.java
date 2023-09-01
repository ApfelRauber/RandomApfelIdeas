package io.github.apfelrauber;

import io.github.apfelrauber.screen.DeepslateChestScreen;
import io.github.apfelrauber.screen.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class RandomIdeasClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(ModScreenHandlers.DEEPSLATE_CHEST_SCREEN_HANDLER, DeepslateChestScreen::new);
    }
}
