package fouristhenumber.relicarchaeology.common.block;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cpw.mods.fml.common.registry.LanguageRegistry;

public class RelicConfigLoader {

    public static List<RelicDefinition> loadRelics(File configDir) {
        File file = new File(configDir, "relicarchaeology/relic_blocks.json");

        if (!file.exists()) {
            createDefaultConfig(file);
        }

        try (Reader reader = new FileReader(file)) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<RelicDefinition>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void loadCustomLang(File configDir) {
        File langFile = new File(configDir, "relicarchaeology/lang/en_US.cfg");
        if (!langFile.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(langFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim()
                    .isEmpty() || line.startsWith("#")) continue;
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    LanguageRegistry.instance()
                        .addStringLocalization(parts[0].trim(), "en_US", parts[1].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createDefaultConfig(File file) {
        try {
            file.getParentFile()
                .mkdirs();
            PrintWriter writer = new PrintWriter(file);
            writer.println("[");
            writer.println(
                "  { \"relicBlockName\": \"relic_enchanting_table\", \"targetModId\": \"minecraft\", \"targetBlock\": \"enchanting_table\", \"targetMeta\": 0 }");
            writer.println("]");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generateMissingLangEntries(List<RelicDefinition> relics, File configDir) {
        File langFile = new File(configDir, "relicarchaeology/lang/en_US.cfg");
        langFile.getParentFile()
            .mkdirs();

        Properties props = new Properties();

        if (langFile.exists()) {
            try (FileReader reader = new FileReader(langFile)) {
                props.load(reader);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        boolean modified = false;

        for (RelicDefinition def : relics) {
            String key = "tile." + def.relicBlockName + ".name";
            if (!props.containsKey(key)) {
                props.setProperty(key, "REPLACE_ME");
                modified = true;
            }
        }

        if (modified) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(langFile))) {
                writer.println("# Localization for relic blocks");
                writer.println("# Format: tile.<relicBlockName>.name=Display Name");
                writer.println();

                List<String> keys = new ArrayList<>(props.stringPropertyNames());
                Collections.sort(keys);

                for (String key : keys) {
                    writer.println(key + "=" + props.getProperty(key));
                }

                System.out.println("Generated missing relic localization entries in en_US.cfg.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
