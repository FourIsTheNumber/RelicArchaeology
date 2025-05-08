package fouristhenumber.relicarchaeology;

import fouristhenumber.relicarchaeology.common.item.RelicItemRenderer;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {

    // Override CommonProxy methods here, if you want a different behaviour on the client (e.g. registering renders).
    // Don't forget to call the super methods as well.

    public static void registerRelicRenderer(Item item) {
        MinecraftForgeClient.registerItemRenderer(item, RelicItemRenderer.INSTANCE);
    }
}
