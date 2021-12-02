package com.dkeva.treeores.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.Direction;

public class Log extends RotatedPillarBlock {
    public Log() {
        super(AbstractBlock.Properties.of(Material.WOOD, (blockState) -> (blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MaterialColor.WOOD : MaterialColor.PODZOL)).strength(2.0F).sound(SoundType.WOOD));
    }
}
