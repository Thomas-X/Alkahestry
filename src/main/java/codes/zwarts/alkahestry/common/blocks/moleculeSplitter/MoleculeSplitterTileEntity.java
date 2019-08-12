package codes.zwarts.alkahestry.common.blocks.moleculeSplitter;

import codes.zwarts.alkahestry.Alkahestry;
import codes.zwarts.alkahestry.ModItems;
import codes.zwarts.alkahestry.Util;
import codes.zwarts.alkahestry.common.blocks.TileEntityMachineBase;
import codes.zwarts.alkahestry.common.recipes.MoleculeSplitterRecipe;
import codes.zwarts.alkahestry.common.tileEntities.TileEntityBase;
import codes.zwarts.alkahestry.craftingManagers.machine.MoleculeSplitterCraftingManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.List;

// TODO Better un spaghetti of private inventory handlers and public ones
// TODO multi-input slot support
// TODO multi-output slot support
// TODO generic machine recipe class
// TODO better inventory checking (not just output slot(s) empty but all slots empty or of same type and not higher than ItemStack's stack limit)
// TODO rework all of this, issues with GUI now as well (not updating)
public class MoleculeSplitterTileEntity extends TileEntityMachineBase implements ITickable {
    public MoleculeSplitterTileEntity() {
        super("moleculeSplitterTileEntity");
    }

    public static int[] outputSlots = {ContainerMoleculeSplitter.OUTPUT_INDEX};
    public boolean isWorking = false;
    public boolean isDoneWorking = false;
    public boolean shouldStartWorking = false;
    public int progress = 0;
    public ItemStack currentItemStack;
    public MoleculeSplitterRecipe currentRecipe;
    // This can be used so that the tile entity on finish can output to output slot (insert) without external ways.
    private ItemStackHandler privateInventoryHandler = new MoleculeSplitterPrivateInventoryHandler(2, this);
    private PublicInventoryHandler publicInventoryHandler = new PublicInventoryHandler(2, outputSlots, privateInventoryHandler);


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

    public void onInventoryChanged() {
        MoleculeSplitterCraftingManager.Validation validation = MoleculeSplitterCraftingManager.isValidRecipe(privateInventoryHandler, new int[]{ContainerMoleculeSplitter.INPUT_INDEX});
        Alkahestry.logger.info("VALIDATION: {} ", validation.isValid);
        Alkahestry.logger.info("RECIPE: {} ", validation.recipe);
        Alkahestry.logger.info("OUTPUT SLOT EMPTY: {} ", privateInventoryHandler.getStackInSlot(ContainerMoleculeSplitter.OUTPUT_INDEX) == ItemStack.EMPTY);
        startWorkingIfPossible(validation);
    }

    public void startWorkingIfPossible (MoleculeSplitterCraftingManager.Validation validation) {
        if (validation.isValid
                && !isWorking
                && (privateInventoryHandler.getStackInSlot(ContainerMoleculeSplitter.OUTPUT_INDEX) == ItemStack.EMPTY || privateInventoryHandler.getStackInSlot(ContainerMoleculeSplitter.OUTPUT_INDEX).getItem().getRegistryName().equals(new ResourceLocation(validation.recipe.output[0].registryName)))
                && !shouldStartWorking
        ) {
            Alkahestry.logger.info("Started working");
            currentRecipe = validation.recipe;
            shouldStartWorking = true;
        }
    }

    //
    public int getWorkingSpeed() {
        // for now a flat amount, TODO speed upgrades etc
        // 20 ticks = 1 sec, which means that in 5 sec 100 ticks passed, resulting in work being done
        return 1;
    }

    public void startWorking(MoleculeSplitterRecipe recipe) {
        currentItemStack = privateInventoryHandler.extractItem(ContainerMoleculeSplitter.INPUT_INDEX, 1, false);
        currentRecipe = recipe;
        isWorking = true;
        shouldStartWorking = false;
    }

    public void finishWorking() {
        // TODO rework hardcoded output value
        Item item = ModItems.items.get(Util.stripModIdFromRegistryName(currentRecipe.output[0].registryName));
        ItemStack itemStack = new ItemStack(
                item, currentRecipe.output[0].amount
        );
        privateInventoryHandler.insertItem(ContainerMoleculeSplitter.OUTPUT_INDEX,
                itemStack,
                false
        );
        resetState();
        // On finish working, try to check the input slot, just incase the user inputted i.e a stack of the items that can be worked with
        MoleculeSplitterCraftingManager.Validation validation = MoleculeSplitterCraftingManager.isValidRecipe(privateInventoryHandler, new int[]{ContainerMoleculeSplitter.INPUT_INDEX});
        startWorkingIfPossible(validation);
    }

    public void interruptWorking() {
        // since items are taken when machine starts working this should only be called once power is implemented
//        resetState();
    }

    public void resetState() {
        progress = 0;
        isWorking = false;
        isDoneWorking = false;
        shouldStartWorking = false;
        currentRecipe = null;
        currentItemStack = null;
    }

    @Override
    public void update() {
        if (!world.isRemote) {
            // Loop progress loop
            if (isWorking) {
                if ((this.progress + getWorkingSpeed()) > 100) {
                    isWorking = false;
                    isDoneWorking = true;
                } else if ((this.progress + getWorkingSpeed()) < 0) {
                    this.progress = 0;
                } else {
                    this.progress += getWorkingSpeed();
                    Alkahestry.logger.info(this.progress);
                }
                return;
            }
            if (shouldStartWorking) {
                startWorking(currentRecipe);
                return;
            }
            if (isDoneWorking) {
                finishWorking();
            }
        }

        // Check if is working, update vars etc and if necessary set isDoneWorking = true
        // If shouldStartWorking = true (by the hook in the overridden ItemStack handler) then call start working, extract items etc


//        ItemStack itemStack = publicInventoryHandler.getStackInSlot(ContainerMoleculeSplitter.INPUT_INDEX);
//        if (itemStack != ItemStack.EMPTY) {
//            privateInventoryHandler.setStackInSlot(ContainerMoleculeSplitter.INPUT_INDEX, ItemStack.EMPTY);
//            privateInventoryHandler.insertItem(ContainerMoleculeSplitter.OUTPUT_INDEX, itemStack, false);
//        }
    }
}
