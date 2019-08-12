package codes.zwarts.alkahestry;

import codes.zwarts.alkahestry.common.blocks.moleculeSplitter.ContainerMoleculeSplitter;
import codes.zwarts.alkahestry.common.blocks.moleculeSplitter.GuiMoleculeSplitter;
import codes.zwarts.alkahestry.common.blocks.moleculeSplitter.MoleculeSplitterTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class ModGuiHandler implements IGuiHandler {
    public static final int MOLECULE_SPLITTER_GUI_ID = 0;

    @Override
    public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case MOLECULE_SPLITTER_GUI_ID:
                return new ContainerMoleculeSplitter(player.inventory, (MoleculeSplitterTileEntity) world.getTileEntity(new BlockPos(x, y, z)));
            default:
                return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case MOLECULE_SPLITTER_GUI_ID:
                return new GuiMoleculeSplitter(getServerGuiElement(ID, player, world, x, y, z), player.inventory);
            default:
                return null;
        }
    }
}
