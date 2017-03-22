package totoro.unreality;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import totoro.unreality.common.CommonProxy;


@Mod(modid = Unreality.MODID, version = Unreality.VERSION)
public class Unreality {
    public static final String MODID = "unreality";
    public static final String VERSION = "0.1.0";

    @SidedProxy(
            clientSide = "totoro.unreality.client.ClientProxy",
            serverSide = "totoro.unreality.common.CommonProxy"
    )
    public static CommonProxy proxy;

    @Instance(MODID)
    public static Unreality instance;


    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Config.load(event.getSuggestedConfigurationFile());
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
