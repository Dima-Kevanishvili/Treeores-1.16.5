package com.dkeva.treeores.blocks;

import com.dkeva.treeores.blocks.containers.MelterContainer;
import com.dkeva.treeores.tileEntities.TileEntityMelter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class Melter extends Block {
    public Melter() {
        super(Properties.of(Material.STONE).strength(2.0f).sound(SoundType.STONE));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileEntityMelter();
    }

    @Nullable
    private LazyOptional<IFluidHandler> getFluidHandler(IBlockReader world, BlockPos pos) {
        return world.getBlockEntity(pos).getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return defaultBlockState().setValue(BlockStateProperties.FACING, context.getNearestLookingDirection().getOpposite());
    }

//    @Override
//    public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity livingEntity, ItemStack itemStack) {
//        getFluidHandler(world, pos).ifPresent(fluidHandler -> {
//            FluidUtil.tryEmptyContainer(itemStack, fluidHandler, 20000, null, true);
//        });
//    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType use(BlockState blockState, World world, BlockPos pos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if (!world.isClientSide) {
            TileEntity te = world.getBlockEntity(pos);
            if (te instanceof TileEntityMelter) {
                INamedContainerProvider containerProvider = new INamedContainerProvider() {

                    @Override
                    public ITextComponent getDisplayName() {
                        return new TranslationTextComponent("screen.treeores.melter");
                    }

                    @Override
                    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                        return new MelterContainer(i, world, pos, playerInventory, playerEntity, ((TileEntityMelter) te).getMelterData());
                    }
                };
                NetworkHooks.openGui((ServerPlayerEntity) playerEntity, containerProvider, te.getBlockPos());
            } else {
                throw new IllegalStateException("Our named container provider is missing!");
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.FACING);
    }
}
