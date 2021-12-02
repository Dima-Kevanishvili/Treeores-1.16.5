package com.dkeva.treeores;

import com.dkeva.treeores.setup.*;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Refs.MODID)
public class Treeores {
    private static final Logger LOGGER = LogManager.getLogger();

    public Treeores() {
        Registration.init();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ModSetup::init);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSideSetup::init);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSideSetup::registerBlockColors);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSideSetup::registerItemColors);
    }
}