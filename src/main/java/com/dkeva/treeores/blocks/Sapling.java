package com.dkeva.treeores.blocks;

import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.trees.Tree;

public class Sapling extends SaplingBlock {
    public Sapling(Tree tree) {
        super(tree, Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS));
    }
}
