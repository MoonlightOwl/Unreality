package totoro.unreality.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.GLU;

/*
 * Taken from OpenComputers sources (written by Sangar)
 */

// This class has evolved into a wrapper for GlStateManager that basically does
// nothing but call the corresponding GlStateManager methods and then also
// forcefully applies whatever that call *should* do. This way the state
// manager's internal state is kept up-to-date but we also avoid issues with
// that state being incorrect causing wrong behavior (I've had too many render
// bugs where textures were not bound correctly or state was not updated
// because the state manager thought it already was in the state to change to,
// so I frankly don't care if this is less performant anymore).
public class RenderState {
    static boolean arb = GLContext.getCapabilities().GL_ARB_multitexture && !GLContext.getCapabilities().OpenGL13;

    public static void checkError(String where) {
        int error = GL11.glGetError();
        if (error != 0) {
            System.out.println("GL ERROR @ " + where + ": " + GLU.gluErrorString(error));
        }
    }

    public static boolean compilingDisplayList() {
        if (GL11.glGetInteger(GL11.GL_LIST_INDEX) != 0) {
            int mode = GL11.glGetInteger(GL11.GL_LIST_MODE);
            return mode == GL11.GL_COMPILE || mode == GL11.GL_COMPILE_AND_EXECUTE;
        }
        else return false;
    }

    // pushAttrib/popAttrib currently breaks the GlStateManager because it doesn't
    // accordingly pushes/pops its cache, so it gets into an illegal state...
    // See https://gist.github.com/fnuecke/9a5b2499835fca9b52419277dc6239ca
    public static void pushAttrib() {
//    GlStateManager.glPushAttrib(mask)
    }

    public static void popAttrib() {
//    GlStateManager.popAttrib()
    }

    public static void disableEntityLighting() {
        Minecraft.getMinecraft().entityRenderer.disableLightmap();
        GlStateManager.disableLighting();
        GlStateManager.disableLight(0);
        GlStateManager.disableLight(1);
        GlStateManager.disableColorMaterial();
    }

    public static void enableEntityLighting() {
        Minecraft.getMinecraft().entityRenderer.enableLightmap();
        GlStateManager.enableLighting();
        GlStateManager.enableLight(0);
        GlStateManager.enableLight(1);
        GlStateManager.enableColorMaterial();
    }

    public static void makeItBlend() {
        GlStateManager.enableBlend();
        GL11.glEnable(GL11.GL_BLEND);
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    public static void disableBlend() {
    GlStateManager.blendFunc(GL11.GL_ONE, GL11.GL_ZERO);
    GlStateManager.disableBlend();
    GL11.glDisable(GL11.GL_BLEND);
    }

    public static void setBlendAlpha(float alpha) {
        GlStateManager.color(1, 1, 1, alpha);
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
    }

    public static void bindTexture(int id) {
        GlStateManager.bindTexture(id);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
    }
}
