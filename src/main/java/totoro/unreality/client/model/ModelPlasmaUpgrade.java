package totoro.unreality.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelPlasmaUpgrade extends ModelBase {
    private ModelRenderer base;

    public ModelPlasmaUpgrade() {
        base = new ModelRenderer(this);
        base.addBox(-5, -5, -5, 10, 10, 10);
    }

    public void render() {
        base.render(1 / 16f);
    }
}
