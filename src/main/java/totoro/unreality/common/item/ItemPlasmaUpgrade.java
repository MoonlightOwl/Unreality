package totoro.unreality.common.item;

import li.cil.oc.api.CreativeTab;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import totoro.unreality.Unreality;


public class ItemPlasmaUpgrade extends Item {
    public ItemPlasmaUpgrade() {
        super();
        setRegistryName("plasmaupgrade");
        setUnlocalizedName(Unreality.MODID + ".plasmaupgrade");
        setCreativeTab(CreativeTab.instance);
        GameRegistry.register(this);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0,
                new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
