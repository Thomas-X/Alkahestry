package codes.zwarts.alkahestry.common.blocks.inventoryHandlers;

import codes.zwarts.alkahestry.common.blocks.BlockTileEntity;
import codes.zwarts.alkahestry.common.tileEntities.TileEntityBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class PrivateInventoryHandler extends ItemStackHandler {
    private TileEntityBase te;

    public PrivateInventoryHandler() {
        super();
    }

    public PrivateInventoryHandler(int size) {
        super(size);
    }

    public PrivateInventoryHandler(int size, TileEntityBase te) {
        super(size);
        this.te = te;
    }

    public PrivateInventoryHandler(NonNullList<ItemStack> stacks) {
        super(stacks);
    }

    @Override
    protected void onContentsChanged(int slot) {
        // when either items are removed or added, re-check if recipe is valid given the current items
        te.onInventoryChanged();
        super.onContentsChanged(slot);
    }
}
