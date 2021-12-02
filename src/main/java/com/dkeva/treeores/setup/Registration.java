package com.dkeva.treeores.setup;

import com.dkeva.treeores.Refs;
import com.dkeva.treeores.blocks.Leaf;
import com.dkeva.treeores.blocks.Log;
import com.dkeva.treeores.blocks.Melter;
import com.dkeva.treeores.blocks.Sapling;
import com.dkeva.treeores.blocks.containers.MelterContainer;
import com.dkeva.treeores.crafting.MeltingRecipe;
import com.dkeva.treeores.tileEntities.TileEntityMelter;
import com.dkeva.treeores.trees.*;
import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.dkeva.treeores.Refs.MODID;

public class Registration {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    private static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MODID);
    private static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, MODID);
    private static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MODID);

    public static void init() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<Log> IRON_LOG = BLOCKS.register("iron_log", Log::new);
    public static final RegistryObject<Item> IRON_LOG_ITEM = ITEMS.register("iron_log", () -> new BlockItem(IRON_LOG.get(), new Item.Properties().tab(ModSetup.itemGroup)));
    public static final RegistryObject<Leaf> IRON_LEAF = BLOCKS.register("iron_leaf", Leaf::new);
    public static final RegistryObject<Item> IRON_LEAF_ITEM = ITEMS.register("iron_leaf", () -> new BlockItem(IRON_LEAF.get(), new Item.Properties().tab(ModSetup.itemGroup)));
    public static final RegistryObject<Sapling> IRON_SAPLING = BLOCKS.register("iron_sapling", () -> new Sapling(new IronTree()));
    public static final RegistryObject<Item> IRON_SAPLING_ITEM = ITEMS.register("iron_sapling", () -> new BlockItem(IRON_SAPLING.get(), new Item.Properties().tab(ModSetup.itemGroup)));

    public static final RegistryObject<Log> COAL_LOG = BLOCKS.register("coal_log", Log::new);
    public static final RegistryObject<Item> COAL_LOG_ITEM = ITEMS.register("coal_log", () -> new BlockItem(COAL_LOG.get(), new Item.Properties().tab(ModSetup.itemGroup)));
    public static final RegistryObject<Leaf> COAL_LEAF = BLOCKS.register("coal_leaf", Leaf::new);
    public static final RegistryObject<Item> COAL_LEAF_ITEM = ITEMS.register("coal_leaf", () -> new BlockItem(COAL_LEAF.get(), new Item.Properties().tab(ModSetup.itemGroup)));
    public static final RegistryObject<Sapling> COAL_SAPLING = BLOCKS.register("coal_sapling", () -> new Sapling(new CoalTree()));
    public static final RegistryObject<Item> COAL_SAPLING_ITEM = ITEMS.register("coal_sapling", () -> new BlockItem(COAL_SAPLING.get(), new Item.Properties().tab(ModSetup.itemGroup)));

    public static final RegistryObject<Log> DIAMOND_LOG = BLOCKS.register("diamond_log", Log::new);
    public static final RegistryObject<Item> DIAMOND_LOG_ITEM = ITEMS.register("diamond_log", () -> new BlockItem(DIAMOND_LOG.get(), new Item.Properties().tab(ModSetup.itemGroup)));
    public static final RegistryObject<Leaf> DIAMOND_LEAF = BLOCKS.register("diamond_leaf", Leaf::new);
    public static final RegistryObject<Item> DIAMOND_LEAF_ITEM = ITEMS.register("diamond_leaf", () -> new BlockItem(DIAMOND_LEAF.get(), new Item.Properties().tab(ModSetup.itemGroup)));
    public static final RegistryObject<Sapling> DIAMOND_SAPLING = BLOCKS.register("diamond_sapling", () -> new Sapling(new DiamondTree()));
    public static final RegistryObject<Item> DIAMOND_SAPLING_ITEM = ITEMS.register("diamond_sapling", () -> new BlockItem(DIAMOND_SAPLING.get(), new Item.Properties().tab(ModSetup.itemGroup)));

    public static final RegistryObject<Log> EMERALD_LOG = BLOCKS.register("emerald_log", Log::new);
    public static final RegistryObject<Item> EMERALD_LOG_ITEM = ITEMS.register("emerald_log", () -> new BlockItem(EMERALD_LOG.get(), new Item.Properties().tab(ModSetup.itemGroup)));
    public static final RegistryObject<Leaf> EMERALD_LEAF = BLOCKS.register("emerald_leaf", Leaf::new);
    public static final RegistryObject<Item> EMERALD_LEAF_ITEM = ITEMS.register("emerald_leaf", () -> new BlockItem(EMERALD_LEAF.get(), new Item.Properties().tab(ModSetup.itemGroup)));
    public static final RegistryObject<Sapling> EMERALD_SAPLING = BLOCKS.register("emerald_sapling", () -> new Sapling(new EmeraldTree()));
    public static final RegistryObject<Item> EMERALD_SAPLING_ITEM = ITEMS.register("emerald_sapling", () -> new BlockItem(EMERALD_SAPLING.get(), new Item.Properties().tab(ModSetup.itemGroup)));

    public static final RegistryObject<Log> GLOWSTONE_LOG = BLOCKS.register("glowstone_log", Log::new);
    public static final RegistryObject<Item> GLOWSTONE_LOG_ITEM = ITEMS.register("glowstone_log", () -> new BlockItem(GLOWSTONE_LOG.get(), new Item.Properties().tab(ModSetup.itemGroup)));
    public static final RegistryObject<Leaf> GLOWSTONE_LEAF = BLOCKS.register("glowstone_leaf", Leaf::new);
    public static final RegistryObject<Item> GLOWSTONE_LEAF_ITEM = ITEMS.register("glowstone_leaf", () -> new BlockItem(GLOWSTONE_LEAF.get(), new Item.Properties().tab(ModSetup.itemGroup)));
    public static final RegistryObject<Sapling> GLOWSTONE_SAPLING = BLOCKS.register("glowstone_sapling", () -> new Sapling(new GlowstoneTree()));
    public static final RegistryObject<Item> GLOWSTONE_SAPLING_ITEM = ITEMS.register("glowstone_sapling", () -> new BlockItem(GLOWSTONE_SAPLING.get(), new Item.Properties().tab(ModSetup.itemGroup)));

    public static final RegistryObject<Log> GOLD_LOG = BLOCKS.register("gold_log", Log::new);
    public static final RegistryObject<Item> GOLD_LOG_ITEM = ITEMS.register("gold_log", () -> new BlockItem(GOLD_LOG.get(), new Item.Properties().tab(ModSetup.itemGroup)));
    public static final RegistryObject<Leaf> GOLD_LEAF = BLOCKS.register("gold_leaf", Leaf::new);
    public static final RegistryObject<Item> GOLD_LEAF_ITEM = ITEMS.register("gold_leaf", () -> new BlockItem(GOLD_LEAF.get(), new Item.Properties().tab(ModSetup.itemGroup)));
    public static final RegistryObject<Sapling> GOLD_SAPLING = BLOCKS.register("gold_sapling", () -> new Sapling(new GoldTree()));
    public static final RegistryObject<Item> GOLD_SAPLING_ITEM = ITEMS.register("gold_sapling", () -> new BlockItem(GOLD_SAPLING.get(), new Item.Properties().tab(ModSetup.itemGroup)));

    public static final RegistryObject<Log> LAPIS_LOG = BLOCKS.register("lapis_log", Log::new);
    public static final RegistryObject<Item> LAPIS_LOG_ITEM = ITEMS.register("lapis_log", () -> new BlockItem(LAPIS_LOG.get(), new Item.Properties().tab(ModSetup.itemGroup)));
    public static final RegistryObject<Leaf> LAPIS_LEAF = BLOCKS.register("lapis_leaf", Leaf::new);
    public static final RegistryObject<Item> LAPIS_LEAF_ITEM = ITEMS.register("lapis_leaf", () -> new BlockItem(LAPIS_LEAF.get(), new Item.Properties().tab(ModSetup.itemGroup)));
    public static final RegistryObject<Sapling> LAPIS_SAPLING = BLOCKS.register("lapis_sapling", () -> new Sapling(new LapisTree()));
    public static final RegistryObject<Item> LAPIS_SAPLING_ITEM = ITEMS.register("lapis_sapling", () -> new BlockItem(LAPIS_SAPLING.get(), new Item.Properties().tab(ModSetup.itemGroup)));

    public static final RegistryObject<Log> OBSIDIAN_LOG = BLOCKS.register("obsidian_log", Log::new);
    public static final RegistryObject<Item> OBSIDIAN_LOG_ITEM = ITEMS.register("obsidian_log", () -> new BlockItem(OBSIDIAN_LOG.get(), new Item.Properties().tab(ModSetup.itemGroup)));
    public static final RegistryObject<Leaf> OBSIDIAN_LEAF = BLOCKS.register("obsidian_leaf", Leaf::new);
    public static final RegistryObject<Item> OBSIDIAN_LEAF_ITEM = ITEMS.register("obsidian_leaf", () -> new BlockItem(OBSIDIAN_LEAF.get(), new Item.Properties().tab(ModSetup.itemGroup)));
    public static final RegistryObject<Sapling> OBSIDIAN_SAPLING = BLOCKS.register("obsidian_sapling", () -> new Sapling(new ObsidianTree()));
    public static final RegistryObject<Item> OBSIDIAN_SAPLING_ITEM = ITEMS.register("obsidian_sapling", () -> new BlockItem(OBSIDIAN_SAPLING.get(), new Item.Properties().tab(ModSetup.itemGroup)));

    public static final RegistryObject<Log> QUARTZ_LOG = BLOCKS.register("quartz_log", Log::new);
    public static final RegistryObject<Item> QUARTZ_LOG_ITEM = ITEMS.register("quartz_log", () -> new BlockItem(QUARTZ_LOG.get(), new Item.Properties().tab(ModSetup.itemGroup)));
    public static final RegistryObject<Leaf> QUARTZ_LEAF = BLOCKS.register("quartz_leaf", Leaf::new);
    public static final RegistryObject<Item> QUARTZ_LEAF_ITEM = ITEMS.register("quartz_leaf", () -> new BlockItem(QUARTZ_LEAF.get(), new Item.Properties().tab(ModSetup.itemGroup)));
    public static final RegistryObject<Sapling> QUARTZ_SAPLING = BLOCKS.register("quartz_sapling", () -> new Sapling(new QuartzTree()));
    public static final RegistryObject<Item> QUARTZ_SAPLING_ITEM = ITEMS.register("quartz_sapling", () -> new BlockItem(QUARTZ_SAPLING.get(), new Item.Properties().tab(ModSetup.itemGroup)));

    public static final RegistryObject<Log> REDSTONE_LOG = BLOCKS.register("redstone_log", Log::new);
    public static final RegistryObject<Item> REDSTONE_LOG_ITEM = ITEMS.register("redstone_log", () -> new BlockItem(REDSTONE_LOG.get(), new Item.Properties().tab(ModSetup.itemGroup)));
    public static final RegistryObject<Leaf> REDSTONE_LEAF = BLOCKS.register("redstone_leaf", Leaf::new);
    public static final RegistryObject<Item> REDSTONE_LEAF_ITEM = ITEMS.register("redstone_leaf", () -> new BlockItem(REDSTONE_LEAF.get(), new Item.Properties().tab(ModSetup.itemGroup)));
    public static final RegistryObject<Sapling> REDSTONE_SAPLING = BLOCKS.register("redstone_sapling", () -> new Sapling(new RedstoneTree()));
    public static final RegistryObject<Item> REDSTONE_SAPLING_ITEM = ITEMS.register("redstone_sapling", () -> new BlockItem(REDSTONE_SAPLING.get(), new Item.Properties().tab(ModSetup.itemGroup)));

    public static final RegistryObject<Log> XP_LOG = BLOCKS.register("xp_log", Log::new);
    public static final RegistryObject<Item> XP_LOG_ITEM = ITEMS.register("xp_log", () -> new BlockItem(XP_LOG.get(), new Item.Properties().tab(ModSetup.itemGroup)));
    public static final RegistryObject<Leaf> XP_LEAF = BLOCKS.register("xp_leaf", Leaf::new);
    public static final RegistryObject<Item> XP_LEAF_ITEM = ITEMS.register("xp_leaf", () -> new BlockItem(XP_LEAF.get(), new Item.Properties().tab(ModSetup.itemGroup)));
    public static final RegistryObject<Sapling> XP_SAPLING = BLOCKS.register("xp_sapling", () -> new Sapling(new XpTree()));
    public static final RegistryObject<Item> XP_SAPLING_ITEM = ITEMS.register("xp_sapling", () -> new BlockItem(XP_SAPLING.get(), new Item.Properties().tab(ModSetup.itemGroup)));


    public static final RegistryObject<Melter> MELTER = BLOCKS.register("melter", Melter::new);
    public static final RegistryObject<Item> MELTER_ITEM = ITEMS.register("melter", () -> new BlockItem(MELTER.get(), new Item.Properties().tab(ModSetup.itemGroup)));


    public static final RegistryObject<TileEntityType<TileEntityMelter>> MELTER_TILE = TILES.register("melter", () -> TileEntityType.Builder.of(TileEntityMelter::new, MELTER.get()).build(null));
    public static final RegistryObject<ContainerType<MelterContainer>> MELTER_CONTAINER = CONTAINERS.register("melter", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        World world = inv.player.getCommandSenderWorld();
        return new MelterContainer(windowId, world, pos, inv, inv.player);
    }));

    public static final RegistryObject<IRecipeSerializer<?>> MELTING_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("melting", MeltingRecipe.Serializer::new);


}
