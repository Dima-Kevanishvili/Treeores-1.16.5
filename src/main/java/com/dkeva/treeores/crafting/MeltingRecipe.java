package com.dkeva.treeores.crafting;

import com.dkeva.treeores.Refs;
import com.dkeva.treeores.setup.ModSetup;
import com.dkeva.treeores.setup.Registration;
import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class MeltingRecipe implements IRecipe<IInventory> {
    private final ResourceLocation recipeId;
    private Ingredient ingredient;
    private FluidStack resultLiquid;
    private int minHeatAmount;
    private int processTime;

    public MeltingRecipe(ResourceLocation recipeId, Ingredient ingredient, FluidStack result, int minHeatAmount, int processTime){
        this.recipeId = recipeId;
        this.ingredient = ingredient;
        this.resultLiquid = result;
        this.minHeatAmount = minHeatAmount;
        this.processTime = processTime;
    }

    @Override
    public boolean matches(IInventory inventory, World world) {
        ItemStack stack = inventory.getItem(0);
        return this.ingredient.test(stack);
    }

    @Override
    public ItemStack assemble(IInventory p_77572_1_) {
            return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }
    public FluidStack getResultLiquid() {
        return this.resultLiquid;
    }

    @Override
    public ResourceLocation getId() {
        return recipeId;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return Registration.MELTING_RECIPE_SERIALIZER.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return ModSetup.MELTING_RECIPE_TYPE;
    }

    public Ingredient getIngredient() {
        return this.ingredient;
    }

    public int getMinHeatAmount() {
        return minHeatAmount;
    }

    public int getProcessTime() {
        return processTime;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<MeltingRecipe> {

        @Override
        public MeltingRecipe fromJson(ResourceLocation recipeid, JsonObject jsonObject) {
            Ingredient ingredient = Ingredient.fromJson(jsonObject.get("ingredient"));
            ResourceLocation fluidId = new ResourceLocation(JSONUtils.getAsString(jsonObject, "result"));
            int liquidAmount = JSONUtils.getAsInt(jsonObject, "amount");
            FluidStack resultFluid = new FluidStack(ForgeRegistries.FLUIDS.getValue(fluidId), liquidAmount);
            int minHeat = JSONUtils.getAsInt(jsonObject, "minimumHeat");
            int processingTime = JSONUtils.getAsInt(jsonObject, "processingTime");

            return new MeltingRecipe(recipeid, ingredient, resultFluid, minHeat, processingTime);
        }

        @Nullable
        @Override
        public MeltingRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            FluidStack result = buffer.readFluidStack();
            int[] intArr = buffer.readVarIntArray(2);
            return new MeltingRecipe(recipeId, ingredient, result, intArr[0], intArr[1]);

        }

        @Override
        public void toNetwork(PacketBuffer buffer, MeltingRecipe recipe) {
            recipe.ingredient.toNetwork(buffer);
            buffer.writeFluidStack(recipe.resultLiquid);
            buffer.writeVarIntArray(new int[]{recipe.minHeatAmount, recipe.processTime});
        }
    }
}
