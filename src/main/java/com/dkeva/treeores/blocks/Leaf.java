package com.dkeva.treeores.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class Leaf extends LeavesBlock {
    public Leaf() {
        super(Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isSuffocating((BlockState bs, IBlockReader ibr, BlockPos pos) -> false).isViewBlocking((BlockState bs, IBlockReader ibr, BlockPos pos) -> false));
    }
}
