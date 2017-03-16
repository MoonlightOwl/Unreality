package totoro.unreality.common;

import li.cil.oc.api.event.RobotRenderEvent;
import li.cil.oc.api.internal.Robot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import totoro.unreality.client.model.ModelPlasmaUpgrade;
import totoro.unreality.common.item.ItemPlasmaUpgrade;

import java.util.Set;

@SideOnly(Side.CLIENT)
public class UpgradeRenderer implements li.cil.oc.api.driver.item.UpgradeRenderer {
    private ModelPlasmaUpgrade plasmaUpgradeModel = new ModelPlasmaUpgrade();

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
            Minecraft mc = Minecraft.getMinecraft();
            TextureManager tm = mc.getTextureManager();
            GlStateManager.pushAttrib();
            //tm.bindTexture(model);
            GlStateManager.disableCull();
            GlStateManager.rotate(180, 1, 0, 0);
            GlStateManager.rotate(mountPoint.rotation.getW(), mountPoint.rotation.getX(), mountPoint.rotation.getY(), mountPoint.rotation.getZ());
            GlStateManager.translate(0F, -0.8F, 0F);
            GlStateManager.translate(mountPoint.offset.getX(), mountPoint.offset.getY(), mountPoint.offset.getZ());
            GlStateManager.scale(0.3f, 0.3f, 0.3f);
            GlStateManager.pushMatrix();
            plasmaUpgradeModel.render();
            GlStateManager.popMatrix();
            GlStateManager.popAttrib();
        }
    }
}
