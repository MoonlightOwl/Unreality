package totoro.unreality.client.renderer;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import totoro.unreality.common.entity.EntityPlasmaBolt;


public class RenderFactoryPlasmaBolt implements IRenderFactory<EntityPlasmaBolt> {
    public static final RenderFactoryPlasmaBolt Instance = new RenderFactoryPlasmaBolt();

    @Override
    public Render<? super EntityPlasmaBolt> createRenderFor(RenderManager manager) {
        return new RenderPlasmaBolt(manager, 1);
    }
}
