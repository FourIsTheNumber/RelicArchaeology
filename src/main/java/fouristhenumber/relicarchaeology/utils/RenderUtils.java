package fouristhenumber.relicarchaeology.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

public class RenderUtils {

    private static Map<String, ResourceLocation> dynamicTextures = new HashMap<>();

    public static ResourceLocation getOrGenerateTexture(String relicName, Block relic, int targetMeta) {
        ResourceLocation texture = dynamicTextures.get(relicName);
        if (texture != null) return texture;

        BufferedImage original = TextureUtils.loadBlockTexture(relic, targetMeta);
        if (original == null) return null;

        BufferedImage sepia = TextureUtils.applySepiaFilter(original);

        try {
            ResourceLocation override = new ResourceLocation(
                "relicarchaeology",
                "textures/blocks/overrides/" + relicName + ".png");
            InputStream overrideStream = Minecraft.getMinecraft()
                .getResourceManager()
                .getResource(override)
                .getInputStream();
            sepia = ImageIO.read(overrideStream);
        } catch (IOException ignored) {}

        DynamicTexture tex = new DynamicTexture(sepia);
        ResourceLocation loc = Minecraft.getMinecraft()
            .getTextureManager()
            .getDynamicTextureLocation("relicarchaeology/" + relicName, tex);
        dynamicTextures.put(relicName, loc);
        return loc;
    }
}
