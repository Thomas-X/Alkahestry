package codes.zwarts.alkahestry.craftingManagers;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Objects;

public class CraftingManagerBase {
    private ArrayList<BaseRecipe> recipes;

    public CraftingManagerBase(ArrayList<BaseRecipe> recipes) {
        this.recipes = recipes;
    }

    public static class ValidationSlot {
        public final BaseRecipe.InputOutput ingredient;
        public final int slot;

        public ValidationSlot(BaseRecipe.InputOutput ingredient, int slot) {
            this.ingredient = ingredient;
            this.slot = slot;
        }
    }

    public static class Validation {
        public final boolean isValid;
        public final ArrayList<ValidationSlot> validSlots;
        public final BaseRecipe recipe;

        public Validation(boolean isValid, ArrayList<ValidationSlot> validSlots, BaseRecipe recipe) {
            this.isValid = isValid;
            this.validSlots = validSlots;
            this.recipe = recipe;
        }
    }

    public Validation isValidRecipe(ItemStackHandler inventory, int[] inputSlots) {
        // Iterate through all recipes, incredibly inefficient!
        for (BaseRecipe recipe : recipes) {
            ArrayList<ValidationSlot> validationSlots = new ArrayList<>();
            for (int inputSlot : inputSlots) {
                for (BaseRecipe.InputOutput inputOutput : recipe.input) {
                    if (Objects.equals(
                            inventory.getStackInSlot(inputSlot)
                                    .getItem()
                                    .getRegistryName(),
                            new ResourceLocation(
                                    OreDictionary.getOreName(OreDictionary.getOreID(inputOutput.registryName))
                            )
                    ) && inventory.getStackInSlot(inputSlot).getCount() >= inputOutput.amount
                            // This way, you need to have every ingredient with the correct amount
                            // In the inventory (i.e recipe: 2 sand in slot 1&2 = 4 glass)
                            && !validationSlots.contains(
                                    new ValidationSlot(inputOutput, inputSlot)
                    )
                    ) {
                        validationSlots.add(
                                new ValidationSlot(
                                        inputOutput, inputSlot
                                )
                        );
                    }
                }
            }
            // if all of the slots are valid (i.e there are 6 slots, slot 1-4 must contain sand)
            if (validationSlots.size() == recipe.input.length) {
                return new Validation(
                        true,
                        validationSlots,
                        recipe
                );
            }
        }
        return new Validation(
                false,
                null,
                null
        );
    }


}
