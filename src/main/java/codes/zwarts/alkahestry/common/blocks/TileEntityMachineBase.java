package codes.zwarts.alkahestry.common.blocks;

import codes.zwarts.alkahestry.Alkahestry;
import codes.zwarts.alkahestry.common.blocks.inventoryHandlers.PrivateInventoryHandler;
import codes.zwarts.alkahestry.common.blocks.inventoryHandlers.PublicInventoryHandler;
import codes.zwarts.alkahestry.common.recipes.MoleculeSplitterRecipe;
import codes.zwarts.alkahestry.common.tileEntities.TileEntityBase;
import codes.zwarts.alkahestry.craftingManagers.BaseRecipe;
import codes.zwarts.alkahestry.craftingManagers.CraftingManagerBase;
import codes.zwarts.alkahestry.craftingManagers.CraftingManagers;
import codes.zwarts.alkahestry.craftingManagers.machine.MoleculeSplitterCraftingManager;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.ArrayList;

public class TileEntityMachineBase extends TileEntityBase implements ITickable, IOnInventoryChanged {
    public boolean isWorking = false;
    public boolean isDoneWorking = false;
    public boolean shouldStartWorking = false;
    public int progress = 0;
    public BaseRecipe currentRecipe;
    public CraftingManagerBase.Validation currentValidation;
    public PrivateInventoryHandler privateInventoryHandler;
    public int[] OUTPUT_SLOTS;
    public int[] INPUT_SLOTS;
    public PublicInventoryHandler publicInventoryHandler;

    public TileEntityMachineBase(String name, int[] OUTPUT_SLOTS, int[] INPUT_SLOTS, int size) {
        super(name);
        this.INPUT_SLOTS = INPUT_SLOTS;
        privateInventoryHandler = new PrivateInventoryHandler(size, this);
        publicInventoryHandler = new PublicInventoryHandler(size, OUTPUT_SLOTS, privateInventoryHandler);
        this.OUTPUT_SLOTS = OUTPUT_SLOTS;
    }

    @Override
    public void onInventoryChanged() {
        CraftingManagerBase.Validation validation = CraftingManagers.MoleculeSplitter.isValidRecipe(privateInventoryHandler, INPUT_SLOTS);
        startWorkingIfPossible(validation);
    }

    public void startWorkingIfPossible(CraftingManagerBase.Validation validation) {
        if (validation.isValid
                && !isWorking
                && !shouldStartWorking
        ) {
            int validSlotCount = 0;
            for (int slot : OUTPUT_SLOTS) {
                for (BaseRecipe.InputOutput inputOutput : validation.recipe.output) {
                    if (
                        // If it's itemstack empty, it means that its stackable
                            (ItemHandlerHelper
                                    .canItemStacksStack(
                                            privateInventoryHandler.getStackInSlot(slot),
                                            new ItemStack(
                                                    (Item) inputOutput.ingredientReference, inputOutput.amount
                                            )
                                    )
                                    && privateInventoryHandler.getStackInSlot(slot)
                                    .getCount() + inputOutput.amount <= privateInventoryHandler.getStackInSlot(slot).getMaxStackSize()
                            )
                                    || privateInventoryHandler.getStackInSlot(slot).isEmpty()
                    ) {
                        validSlotCount++;
                        break;
                    }
                }
            }
            if (validSlotCount == validation.recipe.output.length) {
                Alkahestry.logger.info("[MOLECULE_SPLITTER]: STARTED_WORKING");
                currentRecipe = validation.recipe;
                currentValidation = validation;
                shouldStartWorking = true;
            }
        }
    }

    public int getWorkingSpeed() {
        return 50;
    }

    public void startWorking() {
        for (CraftingManagerBase.ValidationSlot validationSlot : currentValidation.validSlots) {
            privateInventoryHandler.extractItem(validationSlot.slot, validationSlot.ingredient.amount, false);
        }
        isWorking = true;
        shouldStartWorking = false;
    }

    public void finishWorking() {
        if (currentValidation.recipe.output.length > OUTPUT_SLOTS.length) {
            throw new Error("Can't output to output slots since there are more recipe outputs (ingredients) than output slots!");
        }
        for (BaseRecipe.InputOutput inputOutput : currentValidation.recipe.output) {
            for (int slot : OUTPUT_SLOTS) {
                if (
                    // If it's itemstack empty, it means that its stackable
                        (ItemHandlerHelper
                                .canItemStacksStack(
                                        privateInventoryHandler.getStackInSlot(slot),
                                        new ItemStack(
                                                (Item) inputOutput.ingredientReference, inputOutput.amount
                                        )
                                )
                                && privateInventoryHandler.getStackInSlot(slot)
                                .getCount() + inputOutput.amount <= privateInventoryHandler.getStackInSlot(slot).getMaxStackSize()
                        )
                                || privateInventoryHandler.getStackInSlot(slot).isEmpty()
                ) {
                    privateInventoryHandler.insertItem(
                            slot,
                            new ItemStack(
                                    (Item) inputOutput.ingredientReference, inputOutput.amount
                            ),
                            false
                    );
                    break;
                }
            }
        }
        resetState();
        CraftingManagerBase.Validation validation = CraftingManagers.MoleculeSplitter.isValidRecipe(privateInventoryHandler, INPUT_SLOTS);
        startWorkingIfPossible(validation);
    }

    public void resetState() {
        progress = 0;
        isWorking = false;
        isDoneWorking = false;
        shouldStartWorking = false;
        currentValidation = null;
        currentRecipe = null;
    }

    /*
    * Progress bar hooks (testing atm)
    * */

    public ArrayList<IProgressBarHook> hooks = new ArrayList<>();

    public interface IProgressBarHook {
        public void draw();
    }

    public void addProgressBarHook(IProgressBarHook hook) {
        hooks.add(hook);
    }

    public void progressBarHook() {
        for (IProgressBarHook hook : hooks) {
            hook.draw();
        }
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
                    // let the hooks know that the progress bar has been updated
                    progressBarHook();
                }
                return;
            }
            if (shouldStartWorking) {
                startWorking();
                return;
            }
            if (isDoneWorking) {
                finishWorking();
            }
        }
    }
}
