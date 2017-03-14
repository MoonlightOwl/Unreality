package totoro.unreality.client;

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
}
