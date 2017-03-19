package totoro.unreality.common.item;

import li.cil.oc.api.CreativeTab;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class Items {
    private static ItemPlasmaUpgrade plasmaUpgrade;

    public static void preInit() {
        plasmaUpgrade = new ItemPlasmaUpgrade();
    }
    @SideOnly(Side.CLIENT)
    public static void preInitClient() {
        plasmaUpgrade.initModel();
    }

    public static void init() {

    }

    public static void postInit() {
        plasmaUpgrade.setCreativeTab(CreativeTab.instance);
    }
}
