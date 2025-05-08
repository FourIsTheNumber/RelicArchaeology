package fouristhenumber.relicarchaeology;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import fouristhenumber.relicarchaeology.common.item.RelicItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import fouristhenumber.relicarchaeology.common.block.RelicBlock;
import fouristhenumber.relicarchaeology.common.block.RelicBlockDefinition;
import fouristhenumber.relicarchaeology.common.block.RelicConfigLoader;
import fouristhenumber.relicarchaeology.common.item.RelicItem;
import fouristhenumber.relicarchaeology.common.item.RelicItemDefinition;

@Mod(
    modid = RelicArchaeology.MODID,
    version = Tags.VERSION,
    name = "RelicArchaeology",
    acceptedMinecraftVersions = "[1.7.10]")
public class RelicArchaeology {

    public static final String MODID = "relicarchaeology";
    public static final Logger LOG = LogManager.getLogger(MODID);

    @SidedProxy(
        clientSide = "fouristhenumber.relicarchaeology.ClientProxy",
        serverSide = "fouristhenumber.relicarchaeology.CommonProxy")
    public static CommonProxy proxy;

    public static List<RelicBlockDefinition> relicBlockDefinitions;
    public static List<RelicItemDefinition> relicItemDefinitions;

    public static List<RelicBlock> relicBlocks = new ArrayList<>();
    public static List<RelicItem> relicItems = new ArrayList<>();

    @Mod.EventHandler
    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    // GameRegistry."
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);

        File configDir = event.getModConfigurationDirectory();
        relicBlockDefinitions = RelicConfigLoader.loadRelicBlocks(configDir);
        relicItemDefinitions = RelicConfigLoader.loadRelicItems(configDir);

        for (RelicBlockDefinition def : relicBlockDefinitions) {
            RelicBlock relicBlock = new RelicBlock(def.relicBlockName);
            relicBlock.bindTarget(def.targetBlock, def.targetModId, def.targetMeta);
            GameRegistry.registerBlock(relicBlock, def.relicBlockName);
            relicBlocks.add(relicBlock);
        }

        for (RelicItemDefinition def : relicItemDefinitions) {
            RelicItem relicItem = new RelicItem(def.relicName);
            relicItem.bindTarget(def.targetItem, def.targetModId, def.targetMeta);
            GameRegistry.registerItem(relicItem, def.relicName);
            ClientProxy.registerRelicRenderer(relicItem);
            relicItems.add(relicItem);
        }

        RelicConfigLoader.generateMissingLangEntries(relicBlockDefinitions, relicItemDefinitions, configDir);
        RelicConfigLoader.loadCustomLang(configDir);
    }

    @Mod.EventHandler
    // load "Do your mod setup. Build whatever data structures you care about. Register recipes." (Remove if not needed)
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);

        for (RelicBlock relicBlock : relicBlocks) {
            relicBlock.activateBinding();
        }
        for (RelicItem relicItem : relicItems) {
            relicItem.activateBinding();
        }
    }

    @Mod.EventHandler
    // register server commands in this event handler (Remove if not needed)
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
    }
}
