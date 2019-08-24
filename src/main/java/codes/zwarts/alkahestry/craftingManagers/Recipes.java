package codes.zwarts.alkahestry.craftingManagers;

import codes.zwarts.alkahestry.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

public class Recipes {
    public static ArrayList<BaseRecipe> MoleculeSplitter = new ArrayList<>();

    static {
        MoleculeSplitter.add(
                new BaseRecipe(null, new BaseRecipe.InputOutput[]{
                        new BaseRecipe.InputOutput<>(Blocks.SAND.getRegistryName().toString(), 1, Blocks.SAND)
                },
                        new BaseRecipe.InputOutput[]{
                                new BaseRecipe.InputOutput<>(ModItems.items.get(ModItems.OXYGEN).getRegistryName().toString(), 3, ModItems.items.get(ModItems.OXYGEN)),
                                new BaseRecipe.InputOutput<>(ModItems.items.get(ModItems.NITROGEN).getRegistryName().toString(), 1, ModItems.items.get(ModItems.NITROGEN))
                        })
        );
        MoleculeSplitter.add(
                new BaseRecipe(null, new BaseRecipe.InputOutput[]{
                        new BaseRecipe.InputOutput<>(Blocks.STONE.getRegistryName().toString(), 3, Blocks.STONE)
                },
                        new BaseRecipe.InputOutput[]{
                                new BaseRecipe.InputOutput<>(ModItems.items.get(ModItems.NITROGEN).getRegistryName().toString(), 5, ModItems.items.get(ModItems.NITROGEN))
                        })
        );
    }

}
