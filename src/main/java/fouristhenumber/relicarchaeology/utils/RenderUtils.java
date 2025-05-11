package fouristhenumber.relicarchaeology.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

public class RenderUtils {

    private static Map<String, ResourceLocation> dynamicTextures = new HashMap<>();

    public static ResourceLocation getOrGenerateBlockTexture(String relicName, Block relic, int targetMeta) {
        ResourceLocation texture = dynamicTextures.get(relicName);
        if (texture != null) return texture;

        BufferedImage original = TextureUtils.loadBlockTexture(relic, targetMeta);
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

        DynamicTexture tex = new DynamicTexture(sepia);
        ResourceLocation loc = Minecraft.getMinecraft()
            .getTextureManager()
            .getDynamicTextureLocation("relicarchaeology/" + relicName, tex);
        dynamicTextures.put(relicName, loc);
        return loc;
    }
}
