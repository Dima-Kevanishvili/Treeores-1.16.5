package com.dkeva.treeores.blocks.screens;

public class ScreenHelpers {
    public static boolean isMouseInBoundingBox(int mouseX, int mouseY, int relX, int relY, int x0, int x1, int y0, int y1){
        return mouseX > relX + x0 && mouseX < relX + x1 && mouseY > relY + y0 && mouseY < relY + y1;
    }
}
