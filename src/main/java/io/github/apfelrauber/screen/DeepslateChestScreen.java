package io.github.apfelrauber.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.apfelrauber.RandomIdeasMain;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class DeepslateChestScreen extends HandledScreen<DeepslateChestScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(RandomIdeasMain.MOD_ID, "textures/gui/deepslate_chest_gui.png");

    public final Vector2f[] slotPositions;

    public DeepslateChestScreen(DeepslateChestScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.slotPositions = handler.slotPositions.clone();
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);

        for (Vector2f currentPosition : slotPositions) {
            x = (width - backgroundWidth) / 2 + (int) currentPosition.getX() - 1;
            y = (height - backgroundHeight) / 2 + (int) currentPosition.getY() - 1;
            context.drawTexture(TEXTURE, x, y, (int) currentPosition.getX() - 8, 167, 18, 18);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
