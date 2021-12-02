package com.dkeva.treeores.setup;

import com.dkeva.treeores.Refs;
import com.dkeva.treeores.blocks.screens.MelterScreen;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import javax.annotation.Nullable;
import java.awt.*;


@Mod.EventBusSubscriber(modid = Refs.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSideSetup {
    public static void init(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ScreenManager.register(Registration.MELTER_CONTAINER.get(), MelterScreen::new);
            registerBlockColor(Registration.IRON_LEAF.get(), new Color(216, 175, 147));
            registerBlockColor(Registration.GOLD_LEAF.get(), new Color(250, 238, 77));
            registerBlockColor(Registration.COAL_LEAF.get(), new Color(76, 76, 76));
            registerBlockColor(Registration.REDSTONE_LEAF.get(), new Color(150, 44, 44));
            registerBlockColor(Registration.DIAMOND_LEAF.get(), new Color(92, 213, 219));
            registerBlockColor(Registration.EMERALD_LEAF.get(), new Color(0, 217, 58));
            registerBlockColor(Registration.LAPIS_LEAF.get(), new Color(74, 128, 255));
            registerBlockColor(Registration.OBSIDIAN_LEAF.get(), new Color(21, 20, 31));
            registerBlockColor(Registration.GLOWSTONE_LEAF.get(), new Color(255, 255, 1));
            registerBlockColor(Registration.QUARTZ_LEAF.get(), new Color(255, 255, 255));
            registerBlockColor(Registration.XP_LEAF.get(), new Color(127, 178, 56));
        });


    }

    private static void registerBlockColor(Block block, Color color) {
        Minecraft.getInstance().getBlockColors().register((BlockState bs, @Nullable IBlockDisplayReader iBlockDisplayReader, @Nullable BlockPos bp, int i) -> color.getRGB(), block);
        Minecraft.getInstance().getItemColors().register((ItemStack itemStack, int i) -> color.getRGB(), block);
    }
}
