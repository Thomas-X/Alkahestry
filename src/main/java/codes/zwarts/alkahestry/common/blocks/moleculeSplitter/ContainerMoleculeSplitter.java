package codes.zwarts.alkahestry.common.blocks.moleculeSplitter;

import codes.zwarts.alkahestry.Alkahestry;
import codes.zwarts.alkahestry.common.blocks.ContainerBase;
import net.minecraft.client.Minecraft;
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
    public static int[] INPUT_SLOTS = new int[] { 0 };
    public static int[] OUTPUT_SLOTS = new int[] { 1, 2 };
    public static int SLOTS = 3;
    public final MoleculeSplitterTileEntity moleculeSplitterTileEntity;

    @Override
    public void putStackInSlot(int slotID, ItemStack stack) {
        super.putStackInSlot(slotID, stack);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        return super.transferStackInSlot(player, index);
    }

    public ContainerMoleculeSplitter(InventoryPlayer playerInv, final MoleculeSplitterTileEntity moleculeSplitterTileEntity) {
        this.moleculeSplitterTileEntity = moleculeSplitterTileEntity;
        // Molecule splitter's inventory
        IItemHandler inventory = moleculeSplitterTileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);

        // Normal inventory
        addSlotToContainer(new SlotItemHandler(inventory, INPUT_SLOTS[0], 80, 11) {
            @Override
            public void onSlotChanged() {
                moleculeSplitterTileEntity.markDirty();
            }
        });

        // Should deny placing stacks
        addSlotToContainer(new SlotItemHandler(inventory, OUTPUT_SLOTS[0], 44, 45) {
            @Override
            public void onSlotChanged() {
                moleculeSplitterTileEntity.markDirty();
            }
        });
        // Should deny placing stacks
        addSlotToContainer(new SlotItemHandler(inventory, OUTPUT_SLOTS[1], 116, 45) {
            @Override
            public void onSlotChanged() {
                moleculeSplitterTileEntity.markDirty();
            }
        });

        // Add player inventory (simple, doesn't work if y-positions change because of the texutre)
        super.addPlayerInventorySlots(playerInv);
    }


    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
}