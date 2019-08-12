package codes.zwarts.alkahestry.common.blocks.moleculeSplitter;

import codes.zwarts.alkahestry.Alkahestry;
import codes.zwarts.alkahestry.common.blocks.ContainerBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class ContainerMoleculeSplitter extends ContainerBase {
    public static int INPUT_INDEX = 0;
    public static int OUTPUT_INDEX = 1;

    @Override
    public void putStackInSlot(int slotID, ItemStack stack) {
        super.putStackInSlot(slotID, stack);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        return super.transferStackInSlot(player, index);
    }

    public ContainerMoleculeSplitter(InventoryPlayer playerInv, final MoleculeSplitterTileEntity moleculeSplitterTileEntity) {
        // Molecule splitter's inventory
        IItemHandler inventory = moleculeSplitterTileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);

        // Normal inventory
        addSlotToContainer(new SlotItemHandler(inventory, INPUT_INDEX, 80, 35) {
            @Override
            public void onSlotChanged() {
                moleculeSplitterTileEntity.markDirty();
            }
        });

        // Should deny placing stacks
        addSlotToContainer(new SlotItemHandler(inventory, OUTPUT_INDEX, 120, 35) {
            @Override
            public void onSlotChanged() {
                moleculeSplitterTileEntity.markDirty();
            }
        });

        // Player inventory
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int k = 0; k < 9; k++) {
            addSlotToContainer(new Slot(playerInv, k, 8 + k * 18, 142));
        }
    }


    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
}