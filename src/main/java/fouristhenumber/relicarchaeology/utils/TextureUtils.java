package fouristhenumber.relicarchaeology.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class TextureUtils {

    public static BufferedImage loadItemTexture(Item item, int meta) {
        IIcon icon = item.getIconFromDamage(meta);
        if (icon == null) return null;

        String iconName = icon.getIconName();
        String[] split = iconName.contains(":") ? iconName.split(":") : new String[]{"minecraft", iconName};
        String modid = split[0];
        String tex = split[1];

        ResourceLocation res = new ResourceLocation(modid, "textures/items/" + tex + ".png");
        try (InputStream stream = Minecraft.getMinecraft().getResourceManager().getResource(res).getInputStream()) {
            return ImageIO.read(stream);
        } catch (Exception e) {
            System.err.println("[RelicArchaeology] Failed to load texture: " + res);
            return null;
        }
    }

    public static BufferedImage applySepiaFilter(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();

        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgba = img.getRGB(x, y);
                Color col = new Color(rgba, true);

                int r = col.getRed();
                int g = col.getGreen();
                int b = col.getBlue();
                int a = col.getAlpha();

                int tr = (int)(0.393 * r + 0.769 * g + 0.189 * b);
                int tg = (int)(0.349 * r + 0.686 * g + 0.168 * b);
                int tb = (int)(0.272 * r + 0.534 * g + 0.131 * b);

                tr = Math.min(255, tr);
                tg = Math.min(255, tg);
                tb = Math.min(255, tb);

                result.setRGB(x, y, new Color(tr, tg, tb, a).getRGB());
            }
        }

        return result;
    }
}
