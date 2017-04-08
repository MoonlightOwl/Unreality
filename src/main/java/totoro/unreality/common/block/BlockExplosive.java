package totoro.unreality.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import totoro.unreality.Unreality;


@SuppressWarnings("WeakerAccess")
public class BlockExplosive extends Block {
    public BlockExplosive() {
        super(Material.TNT);
        this.setRegistryName("explosive");
        this.setUnlocalizedName(Unreality.MODID + ".explosive");
        GameRegistry.register(this);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
                .register(Item.getItemFromBlock(this), 0,
                        new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
