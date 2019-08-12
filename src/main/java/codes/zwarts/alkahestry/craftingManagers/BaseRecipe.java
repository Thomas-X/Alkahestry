package codes.zwarts.alkahestry.craftingManagers;

import java.util.ArrayList;

public class BaseRecipe {
    public static class InputOutput<T> {
        /**
         * Registry name, with modid, i.e minecraft:sand, alkahestry:oxygen
         */
        public String registryName;
        /**
         * Amount needed (for input) and given in output
         */
        public int amount;
        /**
        * Ingredient reference (i.e Blocks.SAND, ModBlocks.blocks.get(ModBlocks.SAND))
        * */
        public T ingredientReference;

        public InputOutput(String registryName, int amount, T ingredientReference) {
            this.registryName = registryName;
            this.amount = amount;
            this.ingredientReference = ingredientReference;
        }
    }

    /**
     * Metadata needed for the recipe, i.e, power costs, side effects, etc
     */
    public Object meta;
    public InputOutput[] input;
    public InputOutput[] output;

    public BaseRecipe(Object meta, InputOutput[] input, InputOutput[] output) {
        this.meta = meta;
        this.input = input;
        this.output = output;
    }
}
