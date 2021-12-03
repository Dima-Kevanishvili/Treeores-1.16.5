package com.dkeva.treeores.blocks.containers;

import com.dkeva.treeores.setup.Registration;
import com.dkeva.treeores.tileEntities.TileEntityMelter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class MelterContainer extends Container {

    private final TileEntityMelter tileEntity;
    private final PlayerEntity playerEntity;
    private final IItemHandler playerInventory;
    private final IIntArray melterData;

    public MelterContainer(int windowId, World world, BlockPos pos, PlayerInventory pInv, PlayerEntity playerEntity) {
        this(windowId, world, pos, pInv, playerEntity, new IntArray(4));
    }

    public MelterContainer(int windowId, World world, BlockPos pos, PlayerInventory pInv, PlayerEntity playerEntity, IIntArray melterData) {
        super(Registration.MELTER_CONTAINER.get(), windowId);
        tileEntity = (TileEntityMelter) world.getBlockEntity(pos);
        this.playerEntity = playerEntity;
        this.playerInventory = new InvWrapper(pInv);
        this.melterData = melterData;
        layoutPlayerInventorySlots(8, 66);
        tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
            addSlot(new SlotItemHandler(handler, 0, 44, 13));
            addSlot(new SlotItemHandler(handler, 1, 125, 38));
            addSlot(new SlotItemHandler(handler, 2, 125, 13));
        });
        addDataSlots(melterData);

    }

    public int getFluidAmount() {
        return this.melterData.get(0);
    }

    public int getCounter() {
        return this.melterData.get(1);
    }

    public int getProcessTime() {
        return this.melterData.get(2);
    }

    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0; i < amount; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0; j < verAmount; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        // Player inventory
        addSlotBox(this.playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(this.playerInventory, 0, leftCol, topRow, 9, 18);
    }

    @Override
    public boolean stillValid(PlayerEntity playerIn) {
        // TODO: Figure out which player to pass here
        return stillValid(IWorldPosCallable.create(tileEntity.getLevel(), tileEntity.getBlockPos()), playerEntity, Registration.MELTER.get());
    }

    // TODO: Sort this out.
    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();
            itemstack = stack.copy();
            if (index == 0) {
                if (!this.moveItemStackTo(stack, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(stack, itemstack);
            } else {
                if (stack.getItem() == Items.IRON_INGOT) {
                    if (!this.moveItemStackTo(stack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 28) {
                    if (!this.moveItemStackTo(stack, 28, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 37 && !this.moveItemStackTo(stack, 1, 28, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, stack);
        }

        return itemstack;
    }
}
