package codes.zwarts.alkahestry;

import codes.zwarts.alkahestry.common.blocks.BlockTileEntity;
import codes.zwarts.alkahestry.common.blocks.moleculeSplitter.MoleculeSplitter;
import codes.zwarts.alkahestry.common.blocks.NotDirtBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
public class ModBlocks {

//    public static final String NOT_DIRT_BLOCK = "notdirtblock";
    public static final String MOLECULE_SPLITTER = "moleculeSplitter";

    // List of blocks we add, with registering/model/itemBlock taken care of.
    public static Map<String, BlockTileEntity> blocks = new HashMap<>();

    static {
//        blocks.put(NOT_DIRT_BLOCK, new NotDirtBlock(NOT_DIRT_BLOCK));
        blocks.put(MOLECULE_SPLITTER, new MoleculeSplitter(MOLECULE_SPLITTER));
    }

    public static void register(IForgeRegistry<Block> registry) {
        for (BlockTileEntity block : blocks.values()) {
            registry.register(block);
        }
    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry) {
        for (BlockTileEntity block : blocks.values()) {
            registry.register(block.createItemBlock());
        }
    }

    public static void registerModels() {
        for (BlockTileEntity block : blocks.values()) {
            block.registerItemModel(Item.getItemFromBlock(block));
        }
    }

    public static void registerTileEntities(IForgeRegistry<Block> registry) {
        for (BlockTileEntity block : blocks.values()) {
            GameRegistry.registerTileEntity(block.getTileEntityClass(), new ResourceLocation(Objects.requireNonNull(block.getRegistryName()).toString()));
        }
    }
}
