package codes.zwarts.alkahestry.common.blocks.moleculeSplitter;

import codes.zwarts.alkahestry.Alkahestry;
import codes.zwarts.alkahestry.ModItems;
import codes.zwarts.alkahestry.Util;
import codes.zwarts.alkahestry.common.blocks.IOnInventoryChanged;
import codes.zwarts.alkahestry.common.blocks.TileEntityMachineBase;
import codes.zwarts.alkahestry.common.recipes.MoleculeSplitterRecipe;
import codes.zwarts.alkahestry.craftingManagers.machine.MoleculeSplitterCraftingManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;

// TODO Better un spaghetti of private inventory handlers and public ones
// TODO multi-input slot support
// TODO multi-output slot support
// TODO generic machine recipe class
// TODO better inventory checking (not just output slot(s) empty but all slots empty or of same type and not higher than ItemStack's stack limit)
// TODO rework all of this, issues with GUI now as well (not updating)
public class MoleculeSplitterTileEntity extends TileEntityMachineBase implements ITickable, IOnInventoryChanged {
    public MoleculeSplitterTileEntity() {
        super("moleculeSplitterTileEntity",
                ContainerMoleculeSplitter.OUTPUT_SLOTS,
                ContainerMoleculeSplitter.INPUT_SLOTS,
                ContainerMoleculeSplitter.SLOTS
        );
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("inventory", publicInventoryHandler.serializeNBT());
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        publicInventoryHandler.deserializeNBT(compound.getCompoundTag("inventory"));
        super.readFromNBT(compound);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
                ? (T) publicInventoryHandler
                : super.getCapability(capability, facing);
    }
}
