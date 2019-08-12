package codes.zwarts.alkahestry.common.blocks.moleculeSplitter;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.List;

public class PublicInventoryHandler extends ItemStackHandler {
    public int[] outputSlots;
    private final ItemStackHandler privateStackHandler;

    public PublicInventoryHandler(int size, int[] outputSlots, ItemStackHandler privateStackHandler) {
        super(size);
        this.outputSlots = outputSlots;
        this.privateStackHandler = privateStackHandler;
    }

    @Override
    public void setSize(int size) {
        privateStackHandler.setSize(size);
    }

    @Override
    public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
        privateStackHandler.setStackInSlot(slot, stack);
    }

    @Override
    public int getSlots() {
        return privateStackHandler.getSlots();
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int slot) {
        return privateStackHandler.getStackInSlot(slot);
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return privateStackHandler.extractItem(slot, amount, simulate);
    }

    @Override
    public int getSlotLimit(int slot) {
        return privateStackHandler.getSlotLimit(slot);
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return privateStackHandler.isItemValid(slot, stack);
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return privateStackHandler.serializeNBT();
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        privateStackHandler.deserializeNBT(nbt);
    }


    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        for (Integer outputSlot : this.outputSlots) {
            if (outputSlot == slot) {
                // if the public (user, machines) want to insert item into slot that's an "output" slot, deny it.
                return stack;
            }
        }
        return privateStackHandler.insertItem(slot, stack, simulate);
    }
}
