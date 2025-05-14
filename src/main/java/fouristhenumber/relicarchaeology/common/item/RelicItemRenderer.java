package fouristhenumber.relicarchaeology.common.item;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import fouristhenumber.relicarchaeology.utils.TextureUtils;

public class RelicItemRenderer implements IItemRenderer {

    public static final RelicItemRenderer INSTANCE = new RelicItemRenderer();

    private final Map<String, ResourceLocation> dynamicTextures = new HashMap<>();

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return item.getItem() instanceof RelicItem;
    }

    @Override
    public boolean shouldUseRenderHelper(final ItemRenderType type, final ItemStack item,
        final ItemRendererHelper helper) {
        return type == ItemRenderType.ENTITY && helper == ItemRendererHelper.ENTITY_BOBBING
            || (helper == ItemRendererHelper.ENTITY_ROTATION && Minecraft.getMinecraft().gameSettings.fancyGraphics);
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
            Tessellator tessellator = Tessellator.instance;
            // UV coordinates are always 0,1,0,1, since we are drawing from an "atlas" of one item
            IIcon originalIcon = relic.getRelic()
                .getIconFromDamage(relic.getTargetMeta());
            float minV = 0F;
            float minU = 0F;
            float maxV = 1F;
            float maxU = 1F;

            GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
            GL11.glPushMatrix();

            Minecraft.getMinecraft().renderEngine.bindTexture(texture);

            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            GL11.glDisable(GL11.GL_CULL_FACE);

            applyStandardItemTransform(type);
            switch (type) {
                case ENTITY -> {
                    if (Minecraft.getMinecraft().gameSettings.fancyGraphics) {
                        ItemRenderer.renderItemIn2D(
                            tessellator,
                            maxU,
                            minV,
                            minU,
                            maxV,
                            originalIcon.getIconWidth(),
                            originalIcon.getIconHeight(),
                            0.0625F);
                    } else {
                        GL11.glPushMatrix();

                        if (!RenderItem.renderInFrame) {
                            GL11.glRotatef(180.0F - RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
                        }

                        tessellator.startDrawingQuads();
                        tessellator.setNormal(0.0F, 1.0F, 0.0F);
                        tessellator.addVertexWithUV(0.0F - 0.5F, 0.0F - 0.25F, 0.0D, minU, maxV);
                        tessellator.addVertexWithUV(1.0F - 0.5F, 0.0F - 0.25F, 0.0D, maxU, maxV);
                        tessellator.addVertexWithUV(1.0F - 0.5F, 1.0F - 0.25F, 0.0D, maxU, minV);
                        tessellator.addVertexWithUV(0.0F - 0.5F, 1.0F - 0.25F, 0.0D, minU, minV);
                        tessellator.draw();

                        GL11.glPopMatrix();
                    }
                }
                case EQUIPPED, EQUIPPED_FIRST_PERSON -> {
                    ItemRenderer.renderItemIn2D(
                        tessellator,
                        maxU,
                        minV,
                        minU,
                        maxV,
                        originalIcon.getIconWidth(),
                        originalIcon.getIconHeight(),
                        0.0625F);
                }
                case INVENTORY -> {
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0F, 0F, -1.0F);
                    tessellator.addVertexWithUV(0F, 16.0D, 0.001, minU, maxV);
                    tessellator.addVertexWithUV(16.0D, 16.0D, 0.001, maxU, maxV);
                    tessellator.addVertexWithUV(16.0D, 0F, 0.001, maxU, minV);
                    tessellator.addVertexWithUV(0F, 0F, 0.001, minU, minV);
                    tessellator.draw();
                }
                default -> {}
            }
            GL11.glPopMatrix();
            GL11.glPopAttrib();
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

    // Will try to write all the auto-generated textures to disk, to make sure they are functioning.
    public static final boolean SAVE_DEBUG_TEXTURES = false;

    private ResourceLocation generateDynamicTexture(Item targetItem, int meta, String relicName) {
        BufferedImage original = TextureUtils.loadItemTexture(targetItem, meta);
        if (original == null) return null;

        BufferedImage sepia = TextureUtils.applySepiaFilter(original);

        File overrideFile = new File(
            Minecraft.getMinecraft().mcDataDir,
            "config/relicarchaeology/textureoverrides/" + relicName + ".png");

        if (overrideFile.exists()) {
            try {
                sepia = ImageIO.read(overrideFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        DynamicTexture dynTex = new DynamicTexture(sepia);
        if (SAVE_DEBUG_TEXTURES) saveDebugTexture(sepia, relicName);
        return Minecraft.getMinecraft()
            .getTextureManager()
            .getDynamicTextureLocation("relicarchaeology/" + relicName, dynTex);
    }

    public void saveDebugTexture(BufferedImage image, String name) {
        try {
            File debugDir = new File(Minecraft.getMinecraft().mcDataDir, "relic_debug_textures");
            if (!debugDir.exists()) {
                debugDir.mkdirs();
            }

            File outputFile = new File(debugDir, name + ".png");
            ImageIO.write(image, "png", outputFile);
            System.out.println("Saved debug texture to: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
