package fouristhenumber.relicarchaeology;

import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

import fouristhenumber.relicarchaeology.common.item.RelicItemRenderer;

public class ClientProxy extends CommonProxy {

    // Override CommonProxy methods here, if you want a different behaviour on the client (e.g. registering renders).
    // Don't forget to call the super methods as well.

    public static void registerRelicRenderer(Item item) {
        MinecraftForgeClient.registerItemRenderer(item, RelicItemRenderer.INSTANCE);
    }
}
