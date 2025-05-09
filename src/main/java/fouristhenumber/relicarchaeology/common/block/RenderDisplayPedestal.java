package fouristhenumber.relicarchaeology.common.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class RenderDisplayPedestal extends TileEntitySpecialRenderer {

    private final RenderItem itemRenderer = new RenderItem();

    private static final ResourceLocation TEXTURE = new ResourceLocation(
        "relicarchaeology:textures/models/pedestal.png");
    private final ModelPedestal model = new ModelPedestal();

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTicks) {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5, y + 1.5, z + 0.5);
        GL11.glRotatef(180, 0, 0, 1);

        this.bindTexture(TEXTURE);
        model.render(null, 0, 0, 0, 0, 0, 0.0625F);

        GL11.glPopMatrix();

        TileEntityDisplayPedestal pedestal = (TileEntityDisplayPedestal) tile;
        ItemStack stack = pedestal.getStack();
        if (stack != null) {
            GL11.glPushMatrix();

            if (stack.getItem() instanceof ItemBlock) {
                Block block = ((ItemBlock) stack.getItem()).field_150939_a;

                if (block.getRenderType() > 2
                    || (block.getRenderType() == 0 && block.isOpaqueCube() && block.renderAsNormalBlock())) {
                    GL11.glTranslated(x + 0.5, y + 1.05, z + 0.5);
                    GL11.glScalef(2F, 2F, 2F);
                } else {
                    GL11.glTranslated(x + 0.5, y + 1.02, z + 0.25);
                    GL11.glRotatef(90, 1F, 0F, 0F);
                }
            } else {
                GL11.glTranslated(x + 0.5, y + 1.02, z + 0.25);
                GL11.glRotatef(90, 1F, 0F, 0F);
            }

            EntityItem entityitem = new EntityItem(tile.getWorldObj(), 0, 0, 0, stack);
            entityitem.hoverStart = 0.0F;

            RenderManager.instance.renderEntityWithPosYaw(entityitem, 0, 0, 0, 0F, 0F);

            GL11.glPopMatrix();
        }
    }
}
