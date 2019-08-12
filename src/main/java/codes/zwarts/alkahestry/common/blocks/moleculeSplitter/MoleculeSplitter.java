package codes.zwarts.alkahestry.common.blocks.moleculeSplitter;

import codes.zwarts.alkahestry.Alkahestry;
import codes.zwarts.alkahestry.ModGuiHandler;
import codes.zwarts.alkahestry.common.blocks.BlockTileEntity;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class MoleculeSplitter extends BlockTileEntity<MoleculeSplitterTileEntity> {

    public MoleculeSplitter(String name) {
        super(Material.IRON, name);
        setHardness(3.5f);
        setSoundType(SoundType.METAL);
        setCreativeTab(Alkahestry.devTab);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            MoleculeSplitterTileEntity tile = getTileEntity(world, pos);
            IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
            if (!player.isSneaking()) {
                if (player.getHeldItem(hand).isEmpty()) {
                    player.setHeldItem(hand, itemHandler.extractItem(0, 64, false));
                } else {
                    player.setHeldItem(hand, itemHandler.insertItem(0, player.getHeldItem(hand), false));
                }
                tile.markDirty();
            } else {
                player.openGui(Alkahestry.instance, ModGuiHandler.MOLECULE_SPLITTER_GUI_ID, world, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        MoleculeSplitterTileEntity tile = getTileEntity(world, pos);
        IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
        ItemStack stack = itemHandler.getStackInSlot(0);
        if (!stack.isEmpty()) {
            EntityItem item = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack);
            world.spawnEntity(item);
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    public Class<MoleculeSplitterTileEntity> getTileEntityClass() {
        return MoleculeSplitterTileEntity.class;
    }

    @Nullable
    @Override
    public MoleculeSplitterTileEntity createTileEntity(World world, IBlockState state) {
        return new MoleculeSplitterTileEntity();
    }
}
