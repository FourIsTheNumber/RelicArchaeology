package fouristhenumber.relicarchaeology;

import static fouristhenumber.relicarchaeology.RelicArchaeology.relicItems;

import net.minecraftforge.client.MinecraftForgeClient;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import fouristhenumber.relicarchaeology.common.block.RenderDisplayPedestal;
import fouristhenumber.relicarchaeology.common.block.RenderPedestalInventory;
import fouristhenumber.relicarchaeology.common.block.TileEntityDisplayPedestal;
import fouristhenumber.relicarchaeology.common.block.relicblock.RenderRelicBlock;
import fouristhenumber.relicarchaeology.common.block.relicblock.RenderRelicBlockInventory;
import fouristhenumber.relicarchaeology.common.block.relicblock.TileEntityRelicBlock;
import fouristhenumber.relicarchaeology.common.item.RelicItem;
import fouristhenumber.relicarchaeology.common.item.RelicItemRenderer;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRelicBlock.class, new RenderRelicBlock());
        RenderingRegistry.registerBlockHandler(new RenderRelicBlockInventory());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDisplayPedestal.class, new RenderDisplayPedestal());
        RenderingRegistry.registerBlockHandler(new RenderPedestalInventory());

        if (relicItems != null) {
            for (RelicItem relicItem : relicItems.values()) {
                MinecraftForgeClient.registerItemRenderer(relicItem, RelicItemRenderer.INSTANCE);
            }
        }

        super.preInit(event);
    }
}
