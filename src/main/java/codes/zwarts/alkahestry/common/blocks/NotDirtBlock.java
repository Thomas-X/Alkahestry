package codes.zwarts.alkahestry.common.blocks;

import codes.zwarts.alkahestry.Alkahestry;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NotDirtBlock extends BlockBase {
    public NotDirtBlock(String name) {
        super(Material.ICE, name);
        setHardness(0.8f);
        setSoundType(SoundType.STONE);
        setCreativeTab(Alkahestry.devTab);
    }


    // On right click on the block, what to do?
    // could be useful for GUI stuff or multiblock stuff
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        // If client, ignore
        if (worldIn.isRemote) {
            Alkahestry.logger.info("HELLO FROM CLIENT SIDE");
            return true;
        } else {
            switch (hand) {
                case MAIN_HAND:
                    Alkahestry.logger.info("MAIN_HAND {}", playerIn.getHeldItemMainhand());
                    break;
                case OFF_HAND:
                    Alkahestry.logger.info("OFF_HAND {}", playerIn.getHeldItemOffhand());
                    break;
            }
            Alkahestry.logger.info("HELLO FROM SERVER SIDE");
            return true;
        }
    }
}
