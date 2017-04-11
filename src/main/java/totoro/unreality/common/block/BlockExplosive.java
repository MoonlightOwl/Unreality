package totoro.unreality.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import totoro.unreality.Config;
import totoro.unreality.Unreality;
import totoro.unreality.common.entity.EntityPlasmaBolt;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


@SuppressWarnings("WeakerAccess")
public class BlockExplosive extends Block {
    public static final PropertyBool EXPLODE = PropertyBool.create("explode");

    public BlockExplosive() {
        super(Material.TNT);
        this.setDefaultState(this.blockState.getBaseState().withProperty(EXPLODE, Boolean.FALSE));
        this.setRegistryName("explosive");
        this.setUnlocalizedName(Unreality.MODID + ".explosive");
        GameRegistry.register(this);
        GameRegistry.register(new ItemBlock(this), getRegistryName());
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
                .register(Item.getItemFromBlock(this), 0,
                        new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {
        if (!worldIn.isRemote) {
            EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(worldIn, (double)((float)pos.getX() + 0.5F), (double)pos.getY(), (double)((float)pos.getZ() + 0.5F), explosionIn.getExplosivePlacedBy());
            entitytntprimed.setFuse(Config.EXPLOSIVE_FUSE / 2);
            worldIn.spawnEntityInWorld(entitytntprimed);
        }
    }

    public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
        this.explode(worldIn, pos, state, null);
    }

    public void explode(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase igniter) {
        if (!worldIn.isRemote) {
            if (state.getValue(EXPLODE)) {
                EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(worldIn,
                        (double)((float)pos.getX() + 0.5F), (double)pos.getY(), (double)((float)pos.getZ() + 0.5F), igniter);
                entitytntprimed.setFuse(Config.EXPLOSIVE_FUSE);
                worldIn.spawnEntityInWorld(entitytntprimed);
                worldIn.playSound(null, entitytntprimed.posX, entitytntprimed.posY, entitytntprimed.posZ,
                        SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
        }
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
                                    EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side,
                                    float hitX, float hitY, float hitZ) {
        this.explode(worldIn, pos, state.withProperty(EXPLODE, Boolean.TRUE), playerIn);
        worldIn.setBlockState(pos, net.minecraft.init.Blocks.AIR.getDefaultState(), 11);
        return true;
    }

    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        if (!worldIn.isRemote && entityIn instanceof EntityPlasmaBolt) {
            this.explode(worldIn, pos, worldIn.getBlockState(pos).withProperty(EXPLODE, Boolean.TRUE), null);
            worldIn.setBlockToAir(pos);
        }
    }

    public boolean canDropFromExplosion(Explosion explosionIn) {
        return false;
    }

    @Nonnull
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(EXPLODE, (meta & 1) > 0);
    }

    public int getMetaFromState(IBlockState state) {
        return state.getValue(EXPLODE) ? 1 : 0;
    }

    @Nonnull
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, EXPLODE);
    }
}
