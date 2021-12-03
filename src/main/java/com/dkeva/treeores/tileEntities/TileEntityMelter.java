package com.dkeva.treeores.tileEntities;

import com.dkeva.treeores.Refs;
import com.dkeva.treeores.crafting.MeltingRecipe;
import com.dkeva.treeores.setup.ModSetup;
import com.dkeva.treeores.setup.Registration;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

public class TileEntityMelter extends TileEntity implements ITickableTileEntity {
    FluidTank tank = new FluidTank(Refs.MELTER_CAPACITY);

    private LazyOptional<IItemHandler> itemHandler = LazyOptional.of(this::createItemHandler);
    private LazyOptional<IFluidHandler> fluidHandler = LazyOptional.of(() -> tank);

    private MeltingRecipe currentRecipe;
    private int processTime = 0;
    private int currentHeatLevel = 0;
    private boolean isProcessing = false;

    protected final IRecipeType<MeltingRecipe> recipeType;
    private MelterInventory inventory;

    private Map<Block, Integer> heatMap;


    public TileEntityMelter() {
        super(Registration.MELTER_TILE.get());
        this.recipeType = ModSetup.MELTING_RECIPE_TYPE;
        itemHandler.ifPresent(handler -> {
            this.inventory = new MelterInventory(handler);
        });
        heatMap = genHeatMap();
    }

    private Map<Block, Integer> genHeatMap() {
        Map<Block, Integer> map = Maps.newLinkedHashMap();
        map.put(Blocks.TORCH, 2);
        return map;
    }


    final IIntArray melterData = new IIntArray() {
        @Override
        public int get(int i) {
            switch (i) {
                case 0:
                    return TileEntityMelter.this.tank.getFluidAmount();
                case 1:
                    return TileEntityMelter.this.processTime;
                case 2: {
                    try {
                        return TileEntityMelter.this.currentRecipe.getProcessTime();
                    } catch (NullPointerException e) {
                        return 0;
                    }
                }
                default:
                    return 0;
            }
        }

        @Override
        public void set(int i, int value) {
            switch (i) {
                case 0:
                    TileEntityMelter.this.tank.getFluid().setAmount(value);
                    break;
                case 1:
                    TileEntityMelter.this.processTime = value;
                    break;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    };

    // Todo: Recipes Used
    @Override
    public void load(BlockState state, CompoundNBT tag) {
        CompoundNBT invTag = tag.getCompound("inv");
        itemHandler.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(invTag));
        tank.readFromNBT(tag);
        processTime = tag.getInt("counter");
        super.load(state, tag);
    }

    // Todo: Recipes Used
    @Override
    public CompoundNBT save(CompoundNBT tag) {
        itemHandler.ifPresent(h -> {
            CompoundNBT compound = ((INBTSerializable<CompoundNBT>) h).serializeNBT();
            tag.put("inv", compound);
        });
        tank.writeToNBT(tag);
        tag.putInt("counter", processTime);
        return super.save(tag);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        itemHandler.invalidate();
        fluidHandler.invalidate();
    }

    private IItemHandler createItemHandler() {
        return new ItemStackHandler(3) {

            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                if (slot == 0) {
                    boolean flag = false;
                    for (MeltingRecipe recipe : level.getRecipeManager().getAllRecipesFor(ModSetup.MELTING_RECIPE_TYPE)) {
                        if (recipe.getIngredient().test(stack)) {
                            flag = true;
                        }
                    }
                    return flag;
                }
                if (slot == 1) {
                    if (stack.getItem() == Items.OAK_SAPLING) {
                        return true;
                    }
                }
                if (slot == 2) {
                    if (stack.getItem() == Registration.IRON_SAPLING.get().asItem()) {
                        return true;
                    }
                }

                return false;
            }
        };
    }

    private void updateCurrentHeatLevel() {
        BlockPos underneathPos = getBlockPos().below();
        Block blockUnder = level.getBlockState(underneathPos).getBlock();
        int heatForBlock = heatMap.get(blockUnder);
        if (heatForBlock != currentHeatLevel) {
            currentHeatLevel = heatForBlock;
            setChanged();
        }
    }

    private void updateCounter() {
        if (isProcessing && processTime > 0) {
            processTime -= currentHeatLevel;
            setChanged();
        }
    }

    private boolean canMelt(@Nullable MeltingRecipe meltingRecipe) {
        if (meltingRecipe == null) {
            return false;
        }
        FluidStack result = meltingRecipe.getResultLiquid();
        if (tank.isEmpty()) {
            return true;
        }
        if (currentHeatLevel < meltingRecipe.getMinHeatAmount()) {
            return false;
        }
        if (!tank.getFluid().isFluidEqual(result)) {
            return false;
        }
        if (tank.getFluid().getAmount() + result.getAmount() <= Refs.MELTER_CAPACITY) {
            return true;
        }
        return false;
    }

    // Todo: Find Using Recipes | Implement canMelt
    private void handleItemToLiquid() {
        itemHandler.ifPresent(iHandler -> {
            fluidHandler.ifPresent(fHandler -> {
                ItemStack stack = iHandler.getStackInSlot(0);
                if (!stack.isEmpty()) {
                    currentRecipe = this.level.getRecipeManager().getRecipeFor(this.recipeType, inventory, this.level).orElse(null);
                    if (canMelt(currentRecipe)) {
                        if (!isProcessing) {
                            processTime = currentRecipe.getProcessTime();
                            isProcessing = true;
                            setChanged();
                        }
                        if (processTime <= 0) {
                            iHandler.extractItem(0, 1, false);
                            fHandler.fill(currentRecipe.getResultLiquid(), IFluidHandler.FluidAction.EXECUTE);
                            isProcessing = false;
                            setChanged();
                        }
                    } else if (isProcessing) {
                        resetProcessing();
                    }
                } else if (isProcessing) {
                    resetProcessing();
                }
            });
        });
    }

    private void resetProcessing() {
        isProcessing = false;
        processTime = 0;
        setChanged();
    }

    // Todo: Add New Recipe Type and Find Using Recipes
    private void handleLiquidToItem() {
        itemHandler.ifPresent(iHandler -> {
            fluidHandler.ifPresent(fHandler -> {
                ItemStack stack = iHandler.getStackInSlot(1);
                if (stack.getItem() == Items.OAK_SAPLING && tank.getFluidAmount() >= 1000) {
                    iHandler.extractItem(1, 1, false);
                    iHandler.insertItem(2, new ItemStack(Registration.IRON_SAPLING.get().asItem(), 1), false);
                    fHandler.drain(new FluidStack(Fluids.WATER, 1000), IFluidHandler.FluidAction.EXECUTE);
                }

            });
        });
    }

    @Override
    public void tick() {
        if (level.isClientSide) {
            return;
        }
        updateCurrentHeatLevel();
        updateCounter();

        handleItemToLiquid();
        handleLiquidToItem();

    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return itemHandler.cast();
        }
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return fluidHandler.cast();
        }
        return LazyOptional.empty();
    }

    public int getTankAmount() {
        return tank.getFluidAmount();
    }


    public boolean isTankFull() {
        return getTankAmount() >= Refs.MELTER_CAPACITY;
    }


    public IIntArray getMelterData() {
        return melterData;
    }

}
