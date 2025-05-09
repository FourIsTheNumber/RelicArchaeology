package fouristhenumber.relicarchaeology.common.block;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * pedestal - fouristhenumber
 * Created using Tabula 4.1.1
 */
public class ModelPedestal extends ModelBase {

    public ModelRenderer displayPlate;
    public ModelRenderer pillar;
    public ModelRenderer basePlate;
    public ModelRenderer baseStrip1;
    public ModelRenderer baseStrip2;

    public ModelPedestal() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.basePlate = new ModelRenderer(this, 0, 8);
        this.basePlate.setRotationPoint(-8.0F, 23.0F, -7.0F);
        this.basePlate.addBox(0.0F, 0.0F, 0.0F, 16, 1, 14, 0.0F);
        this.baseStrip1 = new ModelRenderer(this, 0, 0);
        this.baseStrip1.setRotationPoint(-7.0F, 23.0F, 7.0F);
        this.baseStrip1.addBox(0.0F, 0.0F, 0.0F, 14, 1, 1, 0.0F);
        this.displayPlate = new ModelRenderer(this, 0, 23);
        this.displayPlate.setRotationPoint(-7.0F, 8.0F, -7.0F);
        this.displayPlate.addBox(0.0F, 0.0F, 0.0F, 14, 1, 14, 0.0F);
        this.baseStrip2 = new ModelRenderer(this, 0, 0);
        this.baseStrip2.setRotationPoint(-7.0F, 23.0F, -8.0F);
        this.baseStrip2.addBox(0.0F, 0.0F, 0.0F, 14, 1, 1, 0.0F);
        this.pillar = new ModelRenderer(this, 0, 38);
        this.pillar.setRotationPoint(-6.0F, 9.0F, -6.0F);
        this.pillar.addBox(0.0F, 0.0F, 0.0F, 12, 14, 12, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.basePlate.render(f5);
        this.baseStrip1.render(f5);
        this.displayPlate.render(f5);
        this.baseStrip2.render(f5);
        this.pillar.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
