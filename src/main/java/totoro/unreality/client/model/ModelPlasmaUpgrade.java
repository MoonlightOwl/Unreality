package totoro.unreality.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelPlasmaUpgrade extends ModelBase {
    private ModelRenderer beam;
    private ModelRenderer details, ribs;

    public ModelPlasmaUpgrade() {
        beam = new ModelRenderer(this);
        beam.addBox(-4, -1, 3, 24, 2, 2);

        details = new ModelRenderer(this);
        details.addBox(-4, 1, 3, 24, 1, 2);
        details.addBox(-4, -2, 3, 24, 1, 2);
        details.addBox(18, 2, 3, 2, 2, 2);
        details.addBox(-12, -6, 2, 8, 8, 4);
        details.addBox(-4, -4, 2, 4, 2, 4);
        details.addBox(6, -6, 2, 12, 4, 4);

        ribs = new ModelRenderer(this);
        ribs.addBox(-1, -2, 2, 1, 6, 4);
        ribs.addBox(3, -2, 2, 1, 7, 4);
        ribs.addBox(7, -2, 2, 1, 7, 4);
        ribs.addBox(11, -2, 2, 1, 7, 4);
    }

    public void renderBeam() {
        beam.renderWithRotation(1 / 32f);
    }
    public void renderDetails() {
        details.render(1 / 32f);
        ribs.render(1 / 32f);
    }
}
