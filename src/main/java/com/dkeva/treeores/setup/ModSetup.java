package com.dkeva.treeores.setup;

import com.dkeva.treeores.Refs;
import com.dkeva.treeores.crafting.MeltingRecipe;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = Refs.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModSetup {
    public static IRecipeType<MeltingRecipe> MELTING_RECIPE_TYPE;
    public static final ItemGroup itemGroup = new ItemGroup(Refs.MODID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Blocks.OAK_SAPLING);

        }
    };

    public static void init(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            MELTING_RECIPE_TYPE = IRecipeType.register(Refs.MODID + ":melting");
        });
    }
}
