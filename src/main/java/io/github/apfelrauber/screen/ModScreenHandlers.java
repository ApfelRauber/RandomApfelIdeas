package io.github.apfelrauber.screen;

import io.github.apfelrauber.RandomIdeasMain;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    public static ScreenHandlerType<DeepslateChestScreenHandler> DEEPSLATE_CHEST_SCREEN_HANDLER = new ScreenHandlerType<>(DeepslateChestScreenHandler::new, null);

    public static void registerScreenHandlers() {
        Registry.register(Registries.SCREEN_HANDLER, new Identifier(RandomIdeasMain.MOD_ID, "deepslate_chest_screen_handler"), DEEPSLATE_CHEST_SCREEN_HANDLER);
    }

}
