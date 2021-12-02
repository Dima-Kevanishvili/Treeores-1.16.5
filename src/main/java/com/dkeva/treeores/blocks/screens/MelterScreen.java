package com.dkeva.treeores.blocks.screens;

import com.dkeva.treeores.Refs;
import com.dkeva.treeores.blocks.containers.MelterContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class MelterScreen  extends ContainerScreen<MelterContainer> {

    private ResourceLocation GUI = new ResourceLocation(Refs.MODID, "textures/gui/melter.png");
    public MelterScreen(MelterContainer melterContainer, PlayerInventory playerInventory, ITextComponent name) {
        super(melterContainer, playerInventory, name);
    }

    @Override
    protected void renderLabels(MatrixStack matrixStack, int mouseX, int mouseY) {
        drawString(matrixStack, Minecraft.getInstance().font, "Melter", 10,10,  4210752);
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(GUI);
        int relY = (this.height - this.imageHeight) / 2;
        int relX = (this.width - this.imageWidth) / 2;
        this.blit(matrixStack, relX, relY, 0, 0,this.imageWidth, this.imageHeight);
        final int fluidLevel = menu.getFluidAmount()/54;
        this.blit(matrixStack, relX + 152, relY + 62 - fluidLevel,176, 62 - fluidLevel, 16, 1+ fluidLevel);
        final int counter = menu.getCounter()*32/Refs.MELTER_COUNTER_MAX;
        this.blit(matrixStack,relX + 43, relY + 31 + counter,193, counter, 18,  counter == 0 ? 0 : 32 - counter);
    }

    @Override
    protected void renderTooltip(MatrixStack matrixStack, int mouseX, int mouseY) {
        int relY = (this.height - this.imageHeight) / 2;
        int relX = (this.width - this.imageWidth) / 2;
        if(mouseX > relX + 150 && mouseX <relX + 170 && mouseY > relY+ 6 && mouseY < relY+63){
            this.renderTooltip(matrixStack,new StringTextComponent("Amount: "+ menu.getFluidAmount()), mouseX,mouseY);
        }
        super.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }
}