package totoro.unreality.client;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import totoro.unreality.common.CommonProxy;
import totoro.unreality.common.UpgradeRenderer;


public class ClientProxy extends CommonProxy {
    // Renderers
    private static UpgradeRenderer upgradeRenderer;

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        // Renderers
        upgradeRenderer = new UpgradeRenderer();
        MinecraftForge.EVENT_BUS.register(upgradeRenderer);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }
}
