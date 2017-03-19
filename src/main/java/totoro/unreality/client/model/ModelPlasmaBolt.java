package totoro.unreality.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;


public class ModelPlasmaBolt extends ModelBase {
    private ModelRenderer bolt;

    public ModelPlasmaBolt() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.bolt = new ModelRenderer(this, 23, 38);
        this.bolt.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bolt.addBox(-0.5F, -0.5F, -4.0F, 1, 1, 8, 0.0F);
    }

    public void render() {
        bolt.render(0.1f);
    }
}
