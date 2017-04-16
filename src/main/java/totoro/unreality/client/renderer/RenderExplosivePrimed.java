package totoro.unreality.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import totoro.unreality.Unreality;
import totoro.unreality.common.entity.EntityExplosivePrimed;

import javax.annotation.Nonnull;


@SuppressWarnings("unused")
@SideOnly(Side.CLIENT)
public class RenderExplosivePrimed extends Render<EntityExplosivePrimed> {
    private ResourceLocation texture;

    public RenderExplosivePrimed(RenderManager renderManagerIn) {
        super(renderManagerIn);
        this.shadowSize = 0.5F;
        texture = new ResourceLocation(Unreality.MODID, "textures/blocks/explosive_side.png");
    }

    /**
     * Renders the desired {@code T} type Entity.
     */
    public void doRender(@Nonnull EntityExplosivePrimed entity,
                         double x, double y, double z, float entityYaw, float partialTicks) {
        BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y + 0.5F, (float)z);

        if ((float)entity.getFuse() - partialTicks + 1.0F < 10.0F) {
            float f = 1.0F - ((float)entity.getFuse() - partialTicks + 1.0F) / 10.0F;
            f = MathHelper.clamp_float(f, 0.0F, 1.0F);
            f = f * f;
            f = f * f;
            float f1 = 1.0F + f * 0.3F;
            GlStateManager.scale(f1, f1, f1);
        }

        float f2 = (1.0F - ((float)entity.getFuse() - partialTicks + 1.0F) / 100.0F) * 0.8F;
        this.bindEntityTexture(entity);
        GlStateManager.rotate(entityYaw, 1.0F, 1.0F, 1.0F);
        double scale = Math.sin(entityYaw / 20) / 10 + 0.8;
        GlStateManager.scale(scale, scale, scale);
        GlStateManager.translate(-0.5F, -0.5F, 0.5F);
        blockrendererdispatcher.renderBlockBrightness(Blocks.TNT.getDefaultState(), entity.getBrightness(partialTicks));
        GlStateManager.translate(0.0F, 0.0F, 1.0F);

        if (this.renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
            blockrendererdispatcher.renderBlockBrightness(Blocks.TNT.getDefaultState(), 1.0F);
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        } else if (entity.getFuse() / 5 % 2 == 0) {
            GlStateManager.disableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.DST_ALPHA);
            GlStateManager.color(1.0F, 0.6F, 0.2F, f2);
            GlStateManager.doPolygonOffset(-3.0F, -3.0F);
            GlStateManager.enablePolygonOffset();
            blockrendererdispatcher.renderBlockBrightness(Blocks.TNT.getDefaultState(), 0.5F);
            GlStateManager.doPolygonOffset(0.0F, 0.0F);
            GlStateManager.disablePolygonOffset();
            GlStateManager.color(1.0F, 0.6F, 0.2F, 1.0F);
            GlStateManager.disableBlend();
            GlStateManager.enableLighting();
            GlStateManager.enableTexture2D();
        }

        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    @Nonnull
    protected ResourceLocation getEntityTexture(@Nonnull EntityExplosivePrimed entity) {
        return texture;
    }
}