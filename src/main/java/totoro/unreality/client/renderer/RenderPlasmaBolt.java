package totoro.unreality.client.renderer;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import totoro.unreality.client.model.ModelPlasmaBolt;
import totoro.unreality.common.entity.EntityPlasmaBolt;
import totoro.unreality.util.RenderState;

import javax.annotation.Nonnull;


public class RenderPlasmaBolt extends Render<EntityPlasmaBolt> {
    private final float scale;
    private final ModelPlasmaBolt model;
    private int texture;

    public RenderPlasmaBolt(RenderManager renderManager, float scaleIn) {
        super(renderManager);
        this.scale = scaleIn;
        this.model = new ModelPlasmaBolt();
        this.texture = GlStateManager.generateTexture();
    }

    public void doRender(@Nonnull EntityPlasmaBolt entity, double x, double y, double z,
                         float entityYaw, float partialTicks) {
        int color = entity.getColor();

        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y, (float)z);

        GlStateManager.enableColorMaterial();
        GlStateManager.enableOutlineMode(this.getTeamColor(entity));

        RenderState.disableEntityLighting();
        RenderState.makeItBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        color = color & 0xFFFFFF;
        GlStateManager.color(((color >> 16) & 0xFF) / 255f,
                ((color >> 8) & 0xFF) / 255f,
                (color & 0xFF) / 255f);

        // Render the model
        GlStateManager.bindTexture(texture);
        model.setRotation(entity.rotationYaw, entity.rotationPitch);
        model.render();

        // Unset effects
        GlStateManager.disableOutlineMode();
        GlStateManager.disableColorMaterial();

        GlStateManager.popMatrix();
        RenderState.disableBlend();
        RenderState.enableEntityLighting();
        GlStateManager.color(1, 1, 1, 1);

        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    @Override
    @Nonnull
    protected ResourceLocation getEntityTexture(@Nonnull EntityPlasmaBolt entity) {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }
}
