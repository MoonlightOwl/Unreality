package totoro.unreality.common;

import li.cil.oc.api.Driver;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import totoro.unreality.Unreality;
import totoro.unreality.common.block.Blocks;
import totoro.unreality.common.driver.DriverPlasmaUpgrade;
import totoro.unreality.common.entity.EntityPlasmaBolt;
import totoro.unreality.common.item.Items;
import totoro.unreality.common.sounds.Sounds;


public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        EntityRegistry.registerModEntity(EntityPlasmaBolt.class, "unreality:plasma",
                2017, Unreality.instance,
                80, 1, true);
        Items.preInit();
        Blocks.preInit();
    }

    public void init(FMLInitializationEvent event) {
        DriverPlasmaUpgrade plasmaDriver = new DriverPlasmaUpgrade(null);
        Driver.add(plasmaDriver);
        Items.init();
        Blocks.init();
        Sounds.init();
    }

    public void postInit(FMLPostInitializationEvent event) {
        Items.postInit();
        Blocks.postInit();
    }
}