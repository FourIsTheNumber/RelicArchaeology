package fouristhenumber.relicarchaeology.common.structure;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class StructureParser {

    private static final Gson GSON = new Gson();
    private static final File STRUCTURE_DIR = new File("config/RelicArchaeology/structures");

    public static List<StructureTemplate> loadAll() {
        List<StructureTemplate> templates = new ArrayList<>();

        if (!STRUCTURE_DIR.exists()) {
            STRUCTURE_DIR.mkdirs();
            System.out.println("[RelicArchaeology] Created structure config directory.");
            return templates;
        }

        File[] files = STRUCTURE_DIR.listFiles((dir, name) -> name.endsWith(".json"));
        if (files == null) return templates;

        for (File file : files) {
            try (Reader reader = new FileReader(file)) {
                StructureTemplate template = GSON.fromJson(reader, StructureTemplate.class);

                if (template.name == null || template.structure.isEmpty() || template.palette.isEmpty()) {
                    System.err.println("[RelicArchaeology] Skipping invalid structure file: " + file.getName());
                    continue;
                }

                templates.add(template);
                System.out.println("[RelicArchaeology] Loaded structure: " + template.name);
            } catch (IOException | JsonSyntaxException e) {
                System.err.println("[RelicArchaeology] Failed to load structure file: " + file.getName());
                e.printStackTrace();
            }
        }

        return templates;
    }
}
