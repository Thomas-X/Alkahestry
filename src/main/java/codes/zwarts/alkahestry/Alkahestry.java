package codes.zwarts.alkahestry;

import codes.zwarts.alkahestry.common.blocks.NotDirtBlock;
import codes.zwarts.alkahestry.proxy.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Logger;

import java.util.Dictionary;
import java.util.Objects;

@Mod(modid = Alkahestry.MODID, name = Alkahestry.NAME, version = Alkahestry.VERSION)
public class Alkahestry
{
    public static Logger logger;
    public static final String MODID = "alkahestry";
    public static final String NAME = "Alkahestry";
    public static final String VERSION = "1.0";
    @SidedProxy(clientSide = "codes.zwarts.alkahestry.proxy.ClientProxy", serverSide = "codes.zwarts.alkahestry.proxy.CommonProxy", modId = Alkahestry.MODID)
    public static CommonProxy proxy;
    public static CreativeTabs devTab = new CreativeTabs("DevRoom") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ModItems.items.get(0));
        }
    };

    @Mod.Instance
    public static Alkahestry instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.preInit(event);
        logger = event.getModLog();
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new ModGuiHandler());
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
        // some example code
        logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
        logger.info(Config.yourRealName);
    }

}
