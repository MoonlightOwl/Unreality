package totoro.unreality.client;

import li.cil.oc.api.CreativeTab;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import totoro.unreality.common.CommonProxy;
import totoro.unreality.common.item.ItemPlasmaUpgrade;


public class ClientProxy extends CommonProxy {
    // Items
    public static ItemPlasmaUpgrade plasmaUpgrade;

    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        plasmaUpgrade = new ItemPlasmaUpgrade();
        plasmaUpgrade.initModel();
    }

    public void postInit(FMLPostInitializationEvent event) {
        plasmaUpgrade.setCreativeTab(CreativeTab.instance);
    }
}
