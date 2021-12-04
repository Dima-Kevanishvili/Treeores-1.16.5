package com.dkeva.treeores.blocks.screens;

import com.dkeva.treeores.Refs;
import com.dkeva.treeores.blocks.containers.MelterContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class MelterScreen extends ContainerScreen<MelterContainer> {

    public MelterScreen(MelterContainer melterContainer, PlayerInventory playerInventory, ITextComponent name) {
        super(melterContainer, playerInventory, name);
    }

    @Override
    protected void renderLabels(MatrixStack matrixStack, int mouseX, int mouseY) {
        this.font.draw(matrixStack, this.title, 10, 10, 4210752);
    }

    protected void renderFluidAmount(MatrixStack matrixStack, int relX, int relY) {
        final int fluidLevel = menu.getFluidAmount() / 54;
        this.blit(matrixStack, relX + 152, relY + 62 - fluidLevel, 176, 62 - fluidLevel, 16, 1 + fluidLevel);
    }

    protected void renderProcessProgress(MatrixStack matrixStack, int relX, int relY) {
        final int processTime = menu.getProcessTime() == 0 ? 0 : menu.getCounter() * 32 / menu.getProcessTime();
        this.blit(matrixStack, relX + 43, relY + 31 + processTime, 193, processTime, 18, processTime == 0 ? 0 : 32 - processTime);
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(Refs.MELTER_GUI);
        int relY = (this.height - this.imageHeight) / 2;
        int relX = (this.width - this.imageWidth) / 2;
        this.blit(matrixStack, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
        renderFluidAmount(matrixStack, relX, relY);
        renderProcessProgress(matrixStack, relX, relY);
    }

    protected void renderAmountTooltip(MatrixStack matrixStack, int mouseX, int mouseY, int relX, int relY) {
        if (ScreenHelpers.isMouseInBoundingBox(mouseX, mouseY, relX,relY, 150, 170, 6, 63)) {
            this.renderTooltip(matrixStack, new StringTextComponent("Amount: " + menu.getFluidAmount()), mouseX, mouseY);
        }
    }

    @Override
    protected void renderTooltip(MatrixStack matrixStack, int mouseX, int mouseY) {
        int relY = (this.height - this.imageHeight) / 2;
        int relX = (this.width - this.imageWidth) / 2;
        renderAmountTooltip(matrixStack, mouseX, mouseY, relX, relY);
        super.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }
}
