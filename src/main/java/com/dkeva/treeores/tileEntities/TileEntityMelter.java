package com.dkeva.treeores.tileEntities;

import com.dkeva.treeores.Refs;
import com.dkeva.treeores.crafting.MeltingRecipe;
import com.dkeva.treeores.setup.ModSetup;
import com.dkeva.treeores.setup.Registration;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
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

public class TileEntityMelter extends TileEntity implements ITickableTileEntity, IRecipeHolder, IRecipeHelperPopulator, ISidedInventory {
    FluidTank tank = new FluidTank(Refs.MELTER_CAPACITY);

    private LazyOptional<IItemHandler> itemHandler = LazyOptional.of(this::createItemHandler);
    private LazyOptional<IFluidHandler> fluidHandler = LazyOptional.of(() -> tank);
    private NonNullList<ItemStack> items;


    private int counter = Refs.MELTER_COUNTER_MAX;
    private int heatFormUnderneath = 0;
    private boolean shouldCount = false;

    private final Object2IntOpenHashMap<ResourceLocation> recipesUsed = new Object2IntOpenHashMap<>();
    protected final IRecipeType<MeltingRecipe> recipeType;

    public TileEntityMelter() {
        super(Registration.MELTER_TILE.get());
        this.recipeType = ModSetup.MELTING_RECIPE_TYPE;
    }


    final IIntArray melterData = new IIntArray() {
        @Override
        public int get(int i) {
            switch (i) {
                case 0:
                    return TileEntityMelter.this.tank.getFluidAmount();
                case 1:
                    return TileEntityMelter.this.counter;
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
                    TileEntityMelter.this.counter = value;
                    break;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    // Todo: Recipes Used
    @Override
    public void load(BlockState state, CompoundNBT tag) {
        CompoundNBT invTag = tag.getCompound("inv");
        itemHandler.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(invTag));
        tank.readFromNBT(tag);
        counter = tag.getInt("counter");
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
        tag.putInt("counter", counter);
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
                    if (stack.getItem() == Items.IRON_INGOT) {
                        return true;
                    }
                    //Todo: add all ingots
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

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (slot == 0) {
                    if (stack.getItem() != Items.IRON_INGOT) {
                        return stack;
                    }
                    //Todo: add all ingots
                }
                if (slot == 1) {
                    if (stack.getItem() != Items.OAK_SAPLING) {
                        return stack;
                    }
                }
                if (slot == 2) {
                    if (stack.getItem() != Registration.IRON_SAPLING.get().asItem()) {
                        return stack;
                    }
                }
                shouldCount = true;
                return super.insertItem(slot, stack, simulate);
            }

            @Nonnull
            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate) {
                if (slot == 0) {
                    if (getStackInSlot(slot).isEmpty()) {
                        shouldCount = false;
                    }
                }
                return super.extractItem(slot, amount, simulate);
            }
        };
    }

    private void updateHeatFromUnderneath() {
        BlockPos underneathPos = getBlockPos().below();
        Block blockUnder = level.getBlockState(underneathPos).getBlock();
        if (blockUnder == Blocks.TORCH) {
            heatFormUnderneath = 2;
            setChanged();
        }


    }

    private void updateCounter() {
        if (counter > 0) {
            counter -= heatFormUnderneath;
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
        if (counter <= 0) {
            itemHandler.ifPresent(iHandler -> {
                fluidHandler.ifPresent(fHandler -> {
                    ItemStack stack = iHandler.getStackInSlot(0);
                    if (!stack.isEmpty()) {
                        System.out.println(this.level.getRecipeManager().getRecipes());
                        MeltingRecipe recipe = this.level.getRecipeManager().getRecipeFor(this.recipeType, this, this.level).orElse(null);
                        if (canMelt(recipe)) {
                            iHandler.extractItem(0, 1, false);
                            fHandler.fill(recipe.getResultLiquid(), IFluidHandler.FluidAction.EXECUTE);
                        }
                    }
                });
            });
            resetCounter();
        }
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
        if (shouldCount) {
            updateHeatFromUnderneath();
            updateCounter();
        }
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

    public void resetCounter() {
        counter = Refs.MELTER_COUNTER_MAX;
        setChanged();
    }

    public IIntArray getMelterData() {
        return melterData;
    }

    // TODO: Figure this out
    @Override
    public void fillStackedContents(RecipeItemHelper recipeItemHelper) {
    }

    // TODO: Do we need this?
    @Override
    public void setRecipeUsed(@Nullable IRecipe<?> recipe) {
        if (recipe != null) {
            ResourceLocation resourceLocation = recipe.getId();

        }
    }

    @Nullable
    @Override
    public IRecipe<?> getRecipeUsed() {
        return null;
    }

    @Override
    public int[] getSlotsForFace(Direction direction) {
        return new int[]{0, 1};
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
        return this.canPlaceItem(index, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return index == 1;
    }

    @Override
    public int getContainerSize() {
        return 3;
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public ItemStack getItem(int index) {
        return items.get(index);
    }

    @Override
    public ItemStack removeItem(int index, int amount) {
        return ItemStackHelper.removeItem(items, index, amount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        return ItemStackHelper.takeItem(items, index);
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        items.set(index, stack);
    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        return this.level != null
                && this.level.getBlockEntity(this.worldPosition) == this
                && player.distanceToSqr(this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 0.5, this.worldPosition.getZ()) <= 64;
    }

    @Override
    public void clearContent() {
        items.clear();
    }
}
