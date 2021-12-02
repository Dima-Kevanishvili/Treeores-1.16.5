package com.dkeva.treeores.tileEntities;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class MelterInventory implements IInventory {
    private final IItemHandler handler;
    public MelterInventory(IItemHandler handler){
        this.handler = handler;
    }
    @Override
    public int getContainerSize() {
        return handler.getSlots();
    }

    @Override
    public boolean isEmpty() {
        for(int slot = 0; slot < handler.getSlots(); slot ++){
            if(!handler.getStackInSlot(slot).isEmpty()){
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getItem(int slot) {
        return handler.getStackInSlot(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        return handler.extractItem(slot, amount, false);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return handler.extractItem(slot, handler.getStackInSlot(slot).getCount(), false);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        handler.insertItem(slot, stack, false);
    }

    @Override
    public void setChanged() {

    }

    @Override
    public boolean stillValid(PlayerEntity player) {
            return true;
    }

    @Override
    public void clearContent() {

    }
}
