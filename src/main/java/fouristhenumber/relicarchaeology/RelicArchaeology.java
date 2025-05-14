package fouristhenumber.relicarchaeology;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraftforge.common.MinecraftForge;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import fouristhenumber.relicarchaeology.common.block.ItemRelicBlock;
import fouristhenumber.relicarchaeology.common.block.TileEntityDisplayPedestal;
import fouristhenumber.relicarchaeology.common.block.relicblock.RelicBlock;
import fouristhenumber.relicarchaeology.common.block.relicblock.RelicBlockDefinition;
import fouristhenumber.relicarchaeology.common.block.relicblock.TileEntityRelicBlock;
import fouristhenumber.relicarchaeology.common.block.relics.BlockFloating;
import fouristhenumber.relicarchaeology.common.item.BrushItem;
import fouristhenumber.relicarchaeology.common.item.RelicItem;
import fouristhenumber.relicarchaeology.common.item.RelicItemDefinition;
import fouristhenumber.relicarchaeology.common.item.relics.ItemHungerAxe;
import fouristhenumber.relicarchaeology.common.structure.StructureGenHandler;
import fouristhenumber.relicarchaeology.common.structure.StructureParser;
import fouristhenumber.relicarchaeology.common.structure.StructureTemplate;
import fouristhenumber.relicarchaeology.crossmod.waila.Waila;
import fouristhenumber.relicarchaeology.utils.EventHandler;
import fouristhenumber.relicarchaeology.utils.RelicConfigLoader;
import fouristhenumber.relicarchaeology.utils.RelicRegistry;

@Mod(
    modid = RelicArchaeology.MODID,
    version = Tags.VERSION,
    name = "RelicArchaeology",
    acceptedMinecraftVersions = "[1.7.10]")
public class RelicArchaeology {

    public static final String MODID = "relicarchaeology";
    public static final Logger LOG = LogManager.getLogger(MODID);

    public static final Random rand = new Random();

    @SidedProxy(
        clientSide = "fouristhenumber.relicarchaeology.ClientProxy",
        serverSide = "fouristhenumber.relicarchaeology.CommonProxy")
    public static CommonProxy proxy;

    public static List<RelicBlockDefinition> relicBlockDefinitions;
    public static List<RelicItemDefinition> relicItemDefinitions;

    public static Map<String, RelicBlock> relicBlocks = new HashMap<>();
    public static Map<String, RelicItem> relicItems = new HashMap<>();

    public static List<StructureTemplate> structureDefinitions;

    public static File configDir;

    @Mod.EventHandler
    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    // GameRegistry."
    public void preInit(FMLPreInitializationEvent event) {

        configDir = event.getModConfigurationDirectory();

        relicBlockDefinitions = RelicConfigLoader.loadRelicBlocks(configDir);
        relicItemDefinitions = RelicConfigLoader.loadRelicItems(configDir);

        if (relicBlockDefinitions != null) {
            for (RelicBlockDefinition def : relicBlockDefinitions) {
                def.applyDefaults();
                RelicBlock relicBlock = new RelicBlock(def.relicBlockName);
                relicBlock.bindTarget(def.targetBlock, def.targetModId, def.targetMeta);
                GameRegistry.registerBlock(relicBlock, ItemRelicBlock.class, def.relicBlockName);
                for (String s : def.categories) {
                    RelicRegistry.assignRelicToCategory(def.relicBlockName, s);
                }
                relicBlocks.put(def.relicBlockName, relicBlock);
            }
        }

        if (relicItemDefinitions != null) {
            for (RelicItemDefinition def : relicItemDefinitions) {
                def.applyDefaults();
                RelicItem relicItem = new RelicItem(def.relicName);
                relicItem.bindTarget(def.targetItem, def.targetModId, def.targetMeta);
                GameRegistry.registerItem(relicItem, def.relicName);
                for (String s : def.categories) {
                    RelicRegistry.assignRelicToCategory(def.relicName, s);
                }
                relicItems.put(def.relicName, relicItem);
            }
        }

        GameRegistry.registerItem(new BrushItem("brushTier1"), "brushTier1");
        GameRegistry.registerItem(new ItemHungerAxe(), "hungerAxe");
        GameRegistry.registerBlock(new BlockFloating(), BlockFloating.ItemBlockFloating.class, "floatingBlock");

        RelicConfigLoader.generateMissingLangEntries(relicBlockDefinitions, relicItemDefinitions, configDir);
        RelicConfigLoader.loadCustomLang(configDir);

        GameRegistry.registerTileEntity(TileEntityDisplayPedestal.class, "relicDisplayPedestal");
        GameRegistry.registerTileEntity(TileEntityRelicBlock.class, "relicBlock");

        proxy.preInit(event);
    }

    @Mod.EventHandler
    // load "Do your mod setup. Build whatever data structures you care about. Register recipes." (Remove if not needed)
    public void init(FMLInitializationEvent event) {
        proxy.init(event);

        structureDefinitions = StructureParser.loadAll();

        if (Loader.isModLoaded("Waila")) {
            Waila.init();
        }

        GameRegistry.registerWorldGenerator(new StructureGenHandler(), 0);

        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }

    @Mod.EventHandler
    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);

        for (RelicBlock relicBlock : relicBlocks.values()) {
            relicBlock.activateBinding();
        }
        for (RelicItem relicItem : relicItems.values()) {
            relicItem.activateBinding();
        }

    }

    @Mod.EventHandler
    // register server commands in this event handler (Remove if not needed)
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
    }
}
