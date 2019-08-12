package codes.zwarts.alkahestry.common.tileEntities;

import codes.zwarts.alkahestry.Alkahestry;
import codes.zwarts.alkahestry.Util;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nonnull;

public class TileEntityBase extends TileEntity {
    protected String name;
    public TileEntityBase(String name) {
        this.name = name;
    }

    public void registerTileEntity() {
        GameRegistry.registerTileEntity(TileEntityBase.class, new ResourceLocation(Util.generateRegistryName(this.name)));
    }

    public void onInventoryChanged() {

    }
}
