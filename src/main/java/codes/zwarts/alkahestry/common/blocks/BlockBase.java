package codes.zwarts.alkahestry.common.blocks;

import codes.zwarts.alkahestry.Alkahestry;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Objects;

public class BlockBase extends Block {
    protected String name;

    public BlockBase(Material blockMaterialIn, String name) {
        super(blockMaterialIn);
        this.name = name;
        setUnlocalizedName(Alkahestry.MODID + "." + name);
        setRegistryName(name);
    }

    public void registerItemModel(Item itemBlock) {
        Alkahestry.proxy.registerItemRenderer(itemBlock, 0, name);
    }

    public Item createItemBlock() {
        return new ItemBlock(this).setRegistryName(Objects.requireNonNull(getRegistryName()));
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }
}
