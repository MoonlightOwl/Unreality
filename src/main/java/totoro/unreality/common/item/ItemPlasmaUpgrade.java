package totoro.unreality.common.item;

import li.cil.oc.api.driver.item.UpgradeRenderer;
import li.cil.oc.api.event.RobotRenderEvent;
import li.cil.oc.api.internal.Robot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import totoro.unreality.Unreality;
import totoro.unreality.client.model.ModelPlasmaUpgrade;
import totoro.unreality.util.RenderState;

import java.util.Set;


public class ItemPlasmaUpgrade extends Item implements UpgradeRenderer {
    private ModelPlasmaUpgrade model = new ModelPlasmaUpgrade();
    ResourceLocation texture;
    int beamTexture;


    public ItemPlasmaUpgrade() {
        super();
        setRegistryName("plasmaupgrade");
        setUnlocalizedName(Unreality.MODID + ".plasmaupgrade");
        GameRegistry.register(this);

        texture = new ResourceLocation(Unreality.MODID, "textures/upgrades/gun.png");
        beamTexture = GlStateManager.generateTexture();
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0,
                new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public String computePreferredMountPoint(ItemStack stack, Robot robot, Set<String> availableMountPoints) {
        if (stack.getItem() instanceof ItemPlasmaUpgrade) {
            return availableMountPoints.contains(MountPointName.TopRight) ? MountPointName.TopRight
                    : availableMountPoints.contains(MountPointName.TopLeft) ? MountPointName.TopLeft
                    : MountPointName.None;
        }
        return MountPointName.None;
    }

    @Override
    public void render(ItemStack stack, RobotRenderEvent.MountPoint mountPoint, Robot robot, float pt) {
        if (stack.getItem() instanceof ItemPlasmaUpgrade) {
            // Tweak matrix
            GlStateManager.rotate(mountPoint.rotation.getW(), mountPoint.rotation.getX(),
                    mountPoint.rotation.getY(), mountPoint.rotation.getZ());
            GlStateManager.translate(mountPoint.offset.getX(), mountPoint.offset.getY(), mountPoint.offset.getZ());
            GlStateManager.pushMatrix();

            // Bind texture
            Minecraft.getMinecraft().getTextureManager().bindTexture(texture);

            // Render all gun details
            model.renderDetails();

            // Bind beam texture
            GlStateManager.bindTexture(beamTexture);

            // Set effects to the beam
            RenderState.disableEntityLighting();
            RenderState.makeItBlend();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
            GlStateManager.color(1, 0, 0.3f);

            // Render the beam
            GlStateManager.colorMask(false, false, false, false);
            GlStateManager.depthMask(true);
            model.renderBeam();
            GlStateManager.colorMask(true, true, true, true);
            GlStateManager.depthFunc(GL11.GL_EQUAL);
            model.renderBeam();
            GlStateManager.depthFunc(GL11.GL_LEQUAL);

            // Set all back
            GlStateManager.popMatrix();
            RenderState.disableBlend();
            RenderState.enableEntityLighting();
            GlStateManager.color(1, 1, 1, 1);
        }
    }
}
