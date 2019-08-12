package codes.zwarts.alkahestry.proxy;

import codes.zwarts.alkahestry.Alkahestry;
import codes.zwarts.alkahestry.Config;
import codes.zwarts.alkahestry.ModBlocks;
import codes.zwarts.alkahestry.common.blocks.NotDirtBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.util.Objects;

@Mod.EventBusSubscriber
public class CommonProxy {

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
    }

    public void preInit(FMLPreInitializationEvent event) {
        Config.preInit(event);
    }

    public void init(FMLInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent event) {
        Config.postInit(event);
    }

    // Client only
    public void registerItemRenderer(Item item, int meta, String id) {
    }

    @SuppressWarnings("deprecation")
    public String localize(String unlocalized, Object... args) {
        return I18n.translateToLocalFormatted(unlocalized, args);
    }
}
