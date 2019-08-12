package codes.zwarts.alkahestry.craftingManagers.machine;

// Get recipes for molecule splitter manager
// Add recipes for molecule splitter manager
// https://github.com/CoFH/ThermalExpansion/blob/1.12/src/main/java/cofh/thermalexpansion/util/managers/machine/PulverizerManager.java

import codes.zwarts.alkahestry.Alkahestry;
import codes.zwarts.alkahestry.common.recipes.MoleculeSplitterRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Objects;

public class MoleculeSplitterCraftingManager {
    public static int NO_POWER_COST = 0;
    public static ArrayList<MoleculeSplitterRecipe> recipes = new ArrayList<>();

    static {
        addRecipe(NO_POWER_COST, "minecraft:sand", new MoleculeSplitterRecipe.CraftingOutput[]{
                new MoleculeSplitterRecipe.CraftingOutput("alkahestry:oxygen", 1),
        });
    }

    public static class Validation {
        public final boolean isValid;
        public final MoleculeSplitterRecipe recipe;

        public Validation(boolean isValid, MoleculeSplitterRecipe recipe) {
            this.isValid = isValid;
            this.recipe = recipe;
        }
    }

    public static Validation isValidRecipe(ItemStackHandler inventory, int[] inputSlots) {
        for (MoleculeSplitterRecipe recipe : recipes) {
            boolean inputSlotsValid = false;
            for (int inputSlot : inputSlots) {
                // if any of the items are in the input slot
                inputSlotsValid = Objects.equals(
                        inventory.getStackInSlot(inputSlot)
                                .getItem()
                                .getRegistryName(),
                        new ResourceLocation(
                                OreDictionary.getOreName(OreDictionary.getOreID(recipe.input))
                        ));
                if (inputSlotsValid) {
                    break;
                }
            }
            if (inputSlotsValid) {
                return new Validation(true, recipe);
            }
        }
        return new Validation(false, null);
    }

    public static void addRecipe(int powerCost, String inputRegistryName, MoleculeSplitterRecipe.CraftingOutput[] output) {
        recipes.add(new MoleculeSplitterRecipe(powerCost, inputRegistryName, output));
    }

    public static void removeRecipe(int powerCost, String inputRegistryName, MoleculeSplitterRecipe.CraftingOutput[] output) {
        for (MoleculeSplitterRecipe recipe : recipes) {
            if (recipe.powerCost == powerCost && recipe.input.equals(inputRegistryName)) {
                if (output.equals(recipe.output)) {
                    recipes.remove(recipe);
                }
            }
        }
    }
}
