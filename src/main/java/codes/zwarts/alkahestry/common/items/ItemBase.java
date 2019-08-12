package codes.zwarts.alkahestry.common.items;

import codes.zwarts.alkahestry.Alkahestry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import javax.annotation.Nullable;

public class ItemBase extends Item {

    protected String name;

    public ItemBase(String name) {
        this.name = name;
        setUnlocalizedName(Alkahestry.MODID + "." + name);
        setRegistryName(name);
    }

    public ItemBase(String name, @Nullable CreativeTabs tab) {
        this(name);
        if (tab != null) {
            setCreativeTab(tab);
        }
    }

    public void registerItemModel() {
        Alkahestry.proxy.registerItemRenderer(this, 0, name);
    }
}
