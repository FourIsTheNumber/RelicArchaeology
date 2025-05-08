package fouristhenumber.relicarchaeology;

import java.io.File;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

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
import fouristhenumber.relicarchaeology.common.block.RelicConfigLoader;
import fouristhenumber.relicarchaeology.common.block.RelicBlockDefinition;
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

    public static List<RelicBlockDefinition> relicBlocks;
    public static List<RelicItemDefinition> relicItems;

    @Mod.EventHandler
    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    // GameRegistry." (Remove if not needed)
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);

        File configDir = event.getModConfigurationDirectory();
        relicBlocks = RelicConfigLoader.loadRelicBlocks(configDir);
        relicItems = RelicConfigLoader.loadRelicItems(configDir);

        for (RelicBlockDefinition def : relicBlocks) {
            Block targetBlock = GameRegistry.findBlock(def.targetModId, def.targetBlock);
            if (targetBlock == null) {
                System.err.println("Relic target block not found: " + def.targetModId + ":" + def.targetBlock);
                continue;
            }

            Block relicBlock = new RelicBlock(targetBlock, def.targetMeta);
            GameRegistry.registerBlock(relicBlock, def.relicBlockName);
        }

        for (RelicItemDefinition def : relicItems) {
            Item targetItem = GameRegistry.findItem(def.targetModId, def.targetItem);
            if (targetItem == null) {
                System.err.println("Relic target item not found: " + def.targetModId + ":" + def.targetItem);
                continue;
            }

            Item relicItem = new RelicItem(def.relicName, targetItem, def.targetMeta);
            GameRegistry.registerItem(relicItem, def.relicName);
        }

        RelicConfigLoader.generateMissingLangEntries(relicBlocks, relicItems, configDir);
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
    }

    @Mod.EventHandler
    // register server commands in this event handler (Remove if not needed)
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
    }
}
