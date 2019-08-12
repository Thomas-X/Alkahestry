package codes.zwarts.alkahestry.common.blocks.moleculeSplitter;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class MoleculeSplitterPrivateInventoryHandler extends ItemStackHandler {
    private MoleculeSplitterTileEntity te;

    public MoleculeSplitterPrivateInventoryHandler() {
        super();
    }

    public MoleculeSplitterPrivateInventoryHandler(int size) {
        super(size);
    }

    public MoleculeSplitterPrivateInventoryHandler(int size, MoleculeSplitterTileEntity te) {
        super(size);
        this.te = te;
    }

    public MoleculeSplitterPrivateInventoryHandler(NonNullList<ItemStack> stacks) {
        super(stacks);
    }

    @Override
    protected void onContentsChanged(int slot) {
        // when either items are removed or added, re-check if recipe is valid given the current items
        te.onInventoryChanged();
        super.onContentsChanged(slot);
    }
}
