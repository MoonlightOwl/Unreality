package totoro.unreality.common;

import li.cil.oc.api.CreativeTab;
import li.cil.oc.api.Driver;
import li.cil.oc.api.driver.EnvironmentProvider;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import totoro.unreality.common.driver.DriverPlasmaUpgrade;
import totoro.unreality.common.item.ItemPlasmaUpgrade;


public class CommonProxy {
    // Items
    private static ItemPlasmaUpgrade plasmaUpgrade;

    public void preInit(FMLPreInitializationEvent event) {
        plasmaUpgrade = new ItemPlasmaUpgrade();
        plasmaUpgrade.initModel();
    }

    public void init(FMLInitializationEvent event) {
        // Drivers
        DriverPlasmaUpgrade plasmaDriver = new DriverPlasmaUpgrade();
        Driver.add(plasmaDriver);
    }

    public void postInit(FMLPostInitializationEvent event) {
        plasmaUpgrade.setCreativeTab(CreativeTab.instance);
    }
}