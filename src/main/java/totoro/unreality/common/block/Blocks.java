package totoro.unreality.common.block;

import li.cil.oc.api.CreativeTab;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class Blocks {
    private static BlockExplosive explosive;

    public static void preInit() {
        explosive = new BlockExplosive();
    }

    @SideOnly(Side.CLIENT)
    public static void initClient() {
        explosive.initModel();
    }

    public static void init() {

    }

    public static void postInit() {
        explosive.setCreativeTab(CreativeTab.instance);
    }
}
