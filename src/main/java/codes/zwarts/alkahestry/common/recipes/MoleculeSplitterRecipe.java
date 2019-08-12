package codes.zwarts.alkahestry.common.recipes;

public class MoleculeSplitterRecipe {

    public static class CraftingOutput {
        public CraftingOutput(String registryName, int amount) {
            this.registryName = registryName;
            this.amount = amount;
        }

        public String registryName;
        public int amount;
    }

    public int powerCost;
    // Registered name (i.e: minecraft:sand)
    public String input;
    // Registered name(s) (i.e: alkahestry:oxygen, alkahestry:nitrogen)
    public CraftingOutput[] output;

    public MoleculeSplitterRecipe(int powerCost, String input, CraftingOutput[] output) {
        this.powerCost = powerCost;
        this.input = input;
        this.output = output;
    }
}
