package fouristhenumber.relicarchaeology.common.item;

import fouristhenumber.relicarchaeology.utils.TextureUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class RelicItemRenderer implements IItemRenderer {

    public static final RelicItemRenderer INSTANCE = new RelicItemRenderer();

    private final Map<String, ResourceLocation> dynamicTextures = new HashMap<>();

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return item.getItem() instanceof RelicItem;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(IItemRenderer.ItemRenderType type, ItemStack stack, Object... data) {
        RelicItem relic = (RelicItem) stack.getItem();
        String relicName = relic.getRelicName();

        ResourceLocation texture = dynamicTextures.get(relicName);
        if (texture == null) {
            texture = generateDynamicTexture(relic.getRelic(), relic.getTargetMeta(), relicName);
            if (texture != null) {
                dynamicTextures.put(relicName, texture);
            }
        }

        if (texture != null) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
            GL11.glPushMatrix();
            applyStandardItemTransform(type);
            GL11.glDisable(GL11.GL_LIGHTING);

            Tessellator tess = Tessellator.instance;
            tess.startDrawingQuads();
            tess.setColorOpaque(255, 255, 255);

            tess.addVertex(0, 0, 0);
            tess.addVertex(1, 0, 0);
            tess.addVertex(1, 1, 0);
            tess.addVertex(0, 1, 0);

            tess.draw();

            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glPopMatrix();
        }
    }

    public static void applyStandardItemTransform(IItemRenderer.ItemRenderType type) {
        if (type == IItemRenderer.ItemRenderType.ENTITY) {
            if (RenderItem.renderInFrame) {
                // Magic numbers calculated from vanilla code
                GL11.glScalef(1.025641F, 1.025641F, 1.025641F);
                GL11.glTranslatef(0.0F, -0.05F, 0.0F);
            }

            if (Minecraft.getMinecraft().gameSettings.fancyGraphics) {
                if (RenderItem.renderInFrame) {
                    GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
                }
                // Magic numbers calculated from vanilla code
                GL11.glTranslatef(-0.5F, -0.25F, 0.0421875F);
            }
        }
    }

    private ResourceLocation generateDynamicTexture(Item targetItem, int meta, String relicName) {
        BufferedImage original = TextureUtils.loadItemTexture(targetItem, meta);
        if (original == null) return null;

        BufferedImage sepia = TextureUtils.applySepiaFilter(original);
        DynamicTexture dynTex = new DynamicTexture(sepia);
        return Minecraft.getMinecraft().getTextureManager()
            .getDynamicTextureLocation("relicarchaeology/" + relicName, dynTex);
    }
}
