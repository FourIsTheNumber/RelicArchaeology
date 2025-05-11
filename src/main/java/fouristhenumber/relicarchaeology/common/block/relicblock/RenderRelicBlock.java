package fouristhenumber.relicarchaeology.common.block.relicblock;

import static fouristhenumber.relicarchaeology.utils.RenderUtils.getOrGenerateBlockTexture;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class RenderRelicBlock extends TileEntitySpecialRenderer {

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks) {
        if (!(te instanceof TileEntityRelicBlock relic)) return;

        ResourceLocation tex = getOrGenerateBlockTexture(relic.relicName, relic.relic, relic.targetMeta);

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);

        Minecraft.getMinecraft()
            .getTextureManager()
            .bindTexture(tex);

        Tessellator tess = Tessellator.instance;

        float minU = 0f;
        float maxU = 1f;
        float minV = 0f;
        float maxV = 1f;

        double x2 = x + 1;
        double y2 = y + 1;
        double z2 = z + 1;

        tess.startDrawingQuads();

        // +Y face
        tess.addVertexWithUV(x, y2, z2, minU, minV);
        tess.addVertexWithUV(x2, y2, z2, maxU, minV);
        tess.addVertexWithUV(x2, y2, z, maxU, maxV);
        tess.addVertexWithUV(x, y2, z, minU, maxV);

        // -Y face
        tess.addVertexWithUV(x, y, z, minU, minV);
        tess.addVertexWithUV(x2, y, z, maxU, minV);
        tess.addVertexWithUV(x2, y, z2, maxU, maxV);
        tess.addVertexWithUV(x, y, z2, minU, maxV);

        // +Z face
        tess.addVertexWithUV(x, y, z2, minU, minV);
        tess.addVertexWithUV(x2, y, z2, maxU, minV);
        tess.addVertexWithUV(x2, y2, z2, maxU, maxV);
        tess.addVertexWithUV(x, y2, z2, minU, maxV);

        // -Z face
        tess.addVertexWithUV(x, y2, z, minU, minV);
        tess.addVertexWithUV(x2, y2, z, maxU, minV);
        tess.addVertexWithUV(x2, y, z, maxU, maxV);
        tess.addVertexWithUV(x, y, z, minU, maxV);

        // +X face
        tess.addVertexWithUV(x2, y, z2, minU, minV);
        tess.addVertexWithUV(x2, y, z, maxU, minV);
        tess.addVertexWithUV(x2, y2, z, maxU, maxV);
        tess.addVertexWithUV(x2, y2, z2, minU, maxV);

        // -X face
        tess.addVertexWithUV(x, y, z, minU, minV);
        tess.addVertexWithUV(x, y, z2, maxU, minV);
        tess.addVertexWithUV(x, y2, z2, maxU, maxV);
        tess.addVertexWithUV(x, y2, z, minU, maxV);

        tess.draw();
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }
}
