package com.dkeva.treeores.blocks;

import net.minecraft.block.LeavesBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class Leaf extends LeavesBlock {
    public Leaf() {
        super(Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS));
    }
}
