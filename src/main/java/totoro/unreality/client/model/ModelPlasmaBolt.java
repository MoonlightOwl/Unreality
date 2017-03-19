package totoro.unreality.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;


public class ModelPlasmaBolt extends ModelBase {
    private ModelRenderer bolt;

    public ModelPlasmaBolt() {
        this.bolt = new ModelRenderer(this);
        this.bolt.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bolt.addBox(-0.5F, -0.5F, -4.0F, 1, 1, 8);
    }

    public void render() {
        bolt.render(0.06f);
    }

    public void setRotation(float yaw, float pitch) {
        bolt.rotateAngleX = (float) Math.toRadians(-pitch);
        bolt.rotateAngleY = (float) Math.toRadians(yaw);
        bolt.rotateAngleZ = 0;
    }
}
