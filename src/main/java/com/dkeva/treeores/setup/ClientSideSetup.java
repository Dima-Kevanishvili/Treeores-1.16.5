package com.dkeva.treeores.setup;

import com.dkeva.treeores.Refs;
import com.dkeva.treeores.blocks.screens.MelterScreen;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import javax.annotation.Nullable;
import java.awt.*;


@Mod.EventBusSubscriber(modid = Refs.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSideSetup {
    public static void init(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ScreenManager.register(Registration.MELTER_CONTAINER.get(), MelterScreen::new);

            RenderTypeLookup.setRenderLayer(Registration.IRON_SAPLING.get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(Registration.GOLD_SAPLING.get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(Registration.COAL_SAPLING.get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(Registration.REDSTONE_SAPLING.get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(Registration.DIAMOND_SAPLING.get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(Registration.EMERALD_SAPLING.get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(Registration.LAPIS_SAPLING.get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(Registration.OBSIDIAN_SAPLING.get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(Registration.GLOWSTONE_SAPLING.get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(Registration.QUARTZ_SAPLING.get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(Registration.XP_SAPLING.get(), RenderType.cutout());
        });
    }

    private static void registerBlockColor(Block block, Color color) {
        Minecraft.getInstance().getBlockColors().register((BlockState bs, @Nullable IBlockDisplayReader iBlockDisplayReader, @Nullable BlockPos bp, int i) -> color.getRGB(), block);
        Minecraft.getInstance().getItemColors().register((ItemStack itemStack, int i) -> color.getRGB(), block);
    }

    public static void registerBlockColors(ColorHandlerEvent.Block event) {
        event.getBlockColors().register((BlockState bs, @Nullable IBlockDisplayReader ibr, @Nullable BlockPos bp, int i) -> new Color(216, 175, 147).getRGB(), Registration.IRON_LEAF.get());
        event.getBlockColors().register((BlockState bs, @Nullable IBlockDisplayReader ibr, @Nullable BlockPos bp, int i) -> new Color(250, 238, 77).getRGB(), Registration.GOLD_LEAF.get());
        event.getBlockColors().register((BlockState bs, @Nullable IBlockDisplayReader ibr, @Nullable BlockPos bp, int i) -> new Color(76, 76, 76).getRGB(), Registration.COAL_LEAF.get());
        event.getBlockColors().register((BlockState bs, @Nullable IBlockDisplayReader ibr, @Nullable BlockPos bp, int i) -> new Color(150, 44, 44).getRGB(), Registration.REDSTONE_LEAF.get());
        event.getBlockColors().register((BlockState bs, @Nullable IBlockDisplayReader ibr, @Nullable BlockPos bp, int i) -> new Color(92, 213, 219).getRGB(), Registration.DIAMOND_LEAF.get());
        event.getBlockColors().register((BlockState bs, @Nullable IBlockDisplayReader ibr, @Nullable BlockPos bp, int i) -> new Color(0, 217, 58).getRGB(), Registration.EMERALD_LEAF.get());
        event.getBlockColors().register((BlockState bs, @Nullable IBlockDisplayReader ibr, @Nullable BlockPos bp, int i) -> new Color(74, 128, 255).getRGB(), Registration.LAPIS_LEAF.get());
        event.getBlockColors().register((BlockState bs, @Nullable IBlockDisplayReader ibr, @Nullable BlockPos bp, int i) -> new Color(21, 20, 31).getRGB(), Registration.OBSIDIAN_LEAF.get());
        event.getBlockColors().register((BlockState bs, @Nullable IBlockDisplayReader ibr, @Nullable BlockPos bp, int i) -> new Color(255, 255, 1).getRGB(), Registration.GLOWSTONE_LEAF.get());
        event.getBlockColors().register((BlockState bs, @Nullable IBlockDisplayReader ibr, @Nullable BlockPos bp, int i) -> new Color(255, 255, 255).getRGB(), Registration.QUARTZ_LEAF.get());
        event.getBlockColors().register((BlockState bs, @Nullable IBlockDisplayReader ibr, @Nullable BlockPos bp, int i) -> new Color(127, 178, 56).getRGB(), Registration.XP_LEAF.get());
    }

    public static void registerItemColors(ColorHandlerEvent.Item event) {
        event.getItemColors().register((ItemStack itemStack, int i) -> new Color(216, 175, 147).getRGB(), Registration.IRON_LEAF.get());
        event.getItemColors().register((ItemStack itemStack, int i) -> new Color(250, 238, 77).getRGB(), Registration.GOLD_LEAF.get());
        event.getItemColors().register((ItemStack itemStack, int i) -> new Color(76, 76, 76).getRGB(), Registration.COAL_LEAF.get());
        event.getItemColors().register((ItemStack itemStack, int i) -> new Color(150, 44, 44).getRGB(), Registration.REDSTONE_LEAF.get());
        event.getItemColors().register((ItemStack itemStack, int i) -> new Color(92, 213, 219).getRGB(), Registration.DIAMOND_LEAF.get());
        event.getItemColors().register((ItemStack itemStack, int i) -> new Color(0, 217, 58).getRGB(), Registration.EMERALD_LEAF.get());
        event.getItemColors().register((ItemStack itemStack, int i) -> new Color(74, 128, 255).getRGB(), Registration.LAPIS_LEAF.get());
        event.getItemColors().register((ItemStack itemStack, int i) -> new Color(21, 20, 31).getRGB(), Registration.OBSIDIAN_LEAF.get());
        event.getItemColors().register((ItemStack itemStack, int i) -> new Color(255, 255, 1).getRGB(), Registration.GLOWSTONE_LEAF.get());
        event.getItemColors().register((ItemStack itemStack, int i) -> new Color(255, 255, 255).getRGB(), Registration.QUARTZ_LEAF.get());
        event.getItemColors().register((ItemStack itemStack, int i) -> new Color(127, 178, 56).getRGB(), Registration.XP_LEAF.get());
    }
}
