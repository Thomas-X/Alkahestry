package codes.zwarts.alkahestry.craftingManagers;

import java.util.ArrayList;

public class Recipes {
    public static ArrayList<BaseRecipe> MoleculeSplitter = new ArrayList<>();


    static {
        MoleculeSplitter.add(
                new BaseRecipe(null, new BaseRecipe.InputOutput[]{
                        new BaseRecipe.InputOutput("minecraft:sand", 1)
                },
                        new BaseRecipe.InputOutput[]{
                                new BaseRecipe.InputOutput("alkahestry:oxygen", 1)
                        })
        );
    }

}
