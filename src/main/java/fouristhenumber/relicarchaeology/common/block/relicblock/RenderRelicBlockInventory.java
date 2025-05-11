package fouristhenumber.relicarchaeology.common.block.relicblock;

import static fouristhenumber.relicarchaeology.utils.RenderUtils.getOrGenerateBlockTexture;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class RenderRelicBlockInventory implements ISimpleBlockRenderingHandler {

    public static final int renderId = RenderingRegistry.getNextAvailableRenderId();

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        if (!(block instanceof RelicBlock relic)) return;

        ResourceLocation tex = getOrGenerateBlockTexture(relic.getRelicName(), relic.getRelic(), relic.getTargetMeta());
        Minecraft.getMinecraft()
            .getTextureManager()
            .bindTexture(tex);

        Minecraft.getMinecraft()
            .getTextureManager()
            .bindTexture(tex);

        Tessellator tess = Tessellator.instance;
        GL11.glPushMatrix();

        GL11.glTranslatef(-0.5F, 0.5F, 0.5F);
        GL11.glScalef(1.0F, -1.0F, -1.0F);

        float minU = 0f;
        float maxU = 1f;
        float minV = 0f;
        float maxV = 1f;

        tess.startDrawingQuads();

        // +Y face
        tess.addVertexWithUV(0, 1, 1, minU, minV);
        tess.addVertexWithUV(1, 1, 1, maxU, minV);
        tess.addVertexWithUV(1, 1, 0, maxU, maxV);
        tess.addVertexWithUV(0, 1, 0, minU, maxV);

        // -Y face
        tess.addVertexWithUV(0, 0, 0, minU, minV);
        tess.addVertexWithUV(1, 0, 0, maxU, minV);
        tess.addVertexWithUV(1, 0, 1, maxU, maxV);
        tess.addVertexWithUV(0, 0, 1, minU, maxV);

        // +Z face
        tess.addVertexWithUV(0, 0, 1, minU, minV);
        tess.addVertexWithUV(1, 0, 1, maxU, minV);
        tess.addVertexWithUV(1, 1, 1, maxU, maxV);
        tess.addVertexWithUV(0, 1, 1, minU, maxV);

        // -Z face
        tess.addVertexWithUV(0, 1, 0, minU, minV);
        tess.addVertexWithUV(1, 1, 0, maxU, minV);
        tess.addVertexWithUV(1, 0, 0, maxU, maxV);
        tess.addVertexWithUV(0, 0, 0, minU, maxV);

        // +X face
        tess.addVertexWithUV(1, 0, 1, minU, minV);
        tess.addVertexWithUV(1, 0, 0, maxU, minV);
        tess.addVertexWithUV(1, 1, 0, maxU, maxV);
        tess.addVertexWithUV(1, 1, 1, minU, maxV);

        // -X face
        tess.addVertexWithUV(0, 0, 0, minU, minV);
        tess.addVertexWithUV(0, 0, 1, maxU, minV);
        tess.addVertexWithUV(0, 1, 1, maxU, maxV);
        tess.addVertexWithUV(0, 1, 0, minU, maxV);

        tess.draw();

        GL11.glPopMatrix();
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        return false;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return renderId;
    }
}
