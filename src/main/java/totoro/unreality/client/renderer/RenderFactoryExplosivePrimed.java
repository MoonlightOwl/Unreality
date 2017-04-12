package totoro.unreality.client.renderer;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import totoro.unreality.common.entity.EntityExplosivePrimed;


public class RenderFactoryExplosivePrimed implements IRenderFactory<EntityExplosivePrimed> {
    public static final RenderFactoryExplosivePrimed Instance = new RenderFactoryExplosivePrimed();

    @Override
    public Render<? super EntityExplosivePrimed> createRenderFor(RenderManager manager) {
        return new RenderExplosivePrimed(manager);
    }
}
