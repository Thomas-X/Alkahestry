package codes.zwarts.alkahestry;

import codes.zwarts.alkahestry.common.blocks.BlockTileEntity;
import codes.zwarts.alkahestry.common.items.Banana;
import codes.zwarts.alkahestry.common.items.ItemBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModItems {

    public static final String OXYGEN = "oxygen";
    public static final String NITROGEN = "nitrogen";
    public static final String BANANA = "banana";

    public static Map<String, ItemBase> items = new HashMap<>();

    static {
        items.put(OXYGEN, new ItemBase(OXYGEN, Alkahestry.devTab));
        items.put(NITROGEN, new ItemBase(NITROGEN, Alkahestry.devTab));
        items.put(BANANA, new Banana());
    }
    public static void register(IForgeRegistry<Item> registry) {
        for (ItemBase itemBase : items.values()) {
            registry.register(itemBase);
        }
    }

    public static void registerModels() {
        for (ItemBase itemBase : items.values()) {
            itemBase.registerItemModel();
        }
    }

}
