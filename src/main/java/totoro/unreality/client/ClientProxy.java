package totoro.unreality.client;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import totoro.unreality.client.renderer.RenderFactoryExplosivePrimed;
import totoro.unreality.client.renderer.RenderFactoryPlasmaBolt;
import totoro.unreality.common.CommonProxy;
import totoro.unreality.common.block.Blocks;
import totoro.unreality.common.entity.EntityExplosivePrimed;
import totoro.unreality.common.entity.EntityPlasmaBolt;
import totoro.unreality.common.item.Items;


public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        Items.preInitClient();
        RenderingRegistry.registerEntityRenderingHandler(EntityPlasmaBolt.class,
                RenderFactoryPlasmaBolt.Instance);
        RenderingRegistry.registerEntityRenderingHandler(EntityExplosivePrimed.class,
                RenderFactoryExplosivePrimed.Instance);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        Blocks.initClient();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }
}
