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
        public final int amount;
        public final BaseRecipe.InputOutput ingredient;
        public final int slot;

        public ValidationSlot(int amount, BaseRecipe.InputOutput ingredient, int slot) {
            this.amount = amount;
            this.ingredient = ingredient;
            this.slot = slot;
        }
    }

    public static class Validation {
        public final boolean isValid;
        public final ValidationSlot validSlots;
        public final BaseRecipe recipe;

        public Validation(boolean isValid, ValidationSlot validSlots, BaseRecipe recipe) {
            this.isValid = isValid;
            this.validSlots = validSlots;
            this.recipe = recipe;
        }
    }

    public Validation isValidRecipe(ItemStackHandler inventory, int[] inputSlots) {
        // Iterate through all recipes, incredibly inefficient!
        for (BaseRecipe recipe : recipes) {
            int validInputCount = recipe.input.length;
            for (int inputSlot : inputSlots) {
                for (BaseRecipe.InputOutput inputOutput : recipe.input) {
                    if (Objects.equals(
                            inventory.getStackInSlot(inputSlot)
                                    .getItem()
                                    .getRegistryName(),
                            new ResourceLocation(
                                    OreDictionary.getOreName(OreDictionary.getOreID(inputOutput.registryName))
                            )
                    )
                    ) {
                        // TODO
                    }
                }
            }
        }
    }
}
