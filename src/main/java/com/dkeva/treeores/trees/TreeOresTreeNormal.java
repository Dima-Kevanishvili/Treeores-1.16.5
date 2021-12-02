package com.dkeva.treeores.trees;

import net.minecraft.block.Block;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;

import javax.annotation.Nullable;
import java.util.Random;

public class TreeOresTreeNormal extends Tree {

    private Block log;
    private Block leaf;

    public TreeOresTreeNormal(Block log, Block leaf) {
        this.log = log;
        this.leaf = leaf;
    }

    @Nullable
    @Override
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(Random random, boolean p_225546_2_) {
        BaseTreeFeatureConfig treeFeatureConfig = new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(log.defaultBlockState()), new SimpleBlockStateProvider(leaf.defaultBlockState()), new BlobFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(0), 3), new StraightTrunkPlacer(4, 2, 0), new TwoLayerFeature(1, 0, 1)).ignoreVines().build();
        return Feature.TREE.configured(treeFeatureConfig);
    }
}
