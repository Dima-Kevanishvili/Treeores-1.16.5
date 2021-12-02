package com.dkeva.treeores.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.RecipeProvider;

class Recipes extends RecipeProvider {

    public Recipes(DataGenerator generatorIn) {
        super(generatorIn);
    }
//
//    @Override
//    protected void registerRecipes(~Consumer<IFinishedRecipe> consumer) {
//        ShapedRecipeBuilder.shapedRecipe(ModBlocks.IRONSAPLING)
//                .patternLine("xxx")
//                .patternLine("x#x")
//                .patternLine("xxx")
//                .key('x', Blocks.COBBLESTONE)
//                .key('#', Tags.Items.DYES_RED)
//                .setGroup("treeores")
//                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
//                .build(consumer);
//    }
}