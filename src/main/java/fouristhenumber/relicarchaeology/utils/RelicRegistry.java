package fouristhenumber.relicarchaeology.utils;

import static fouristhenumber.relicarchaeology.RelicArchaeology.rand;
import static fouristhenumber.relicarchaeology.RelicArchaeology.relicBlocks;
import static fouristhenumber.relicarchaeology.RelicArchaeology.relicItems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import fouristhenumber.relicarchaeology.common.block.RelicBlock;

public class RelicRegistry {

    private static final Map<String, RelicCategory> CATEGORIES = new HashMap<>();

    public static void registerCategory(String name) {
        CATEGORIES.putIfAbsent(name, new RelicCategory(name));
    }

    public static void assignRelicToCategory(String relicId, String categoryName) {
        registerCategory(categoryName);
        CATEGORIES.get(categoryName)
            .addRelic(relicId);
    }

    public static Set<String> getRelicsInCategory(String name) {
        RelicCategory cat = CATEGORIES.get(name);
        return cat == null ? Collections.emptySet() : cat.relicIds;
    }

    public static boolean isRelicInCategory(String relicId, String categoryName) {
        return CATEGORIES.containsKey(categoryName) && CATEGORIES.get(categoryName)
            .contains(relicId);
    }

    public static Object getRelicInSet(Set<String> set) {
        if (set.isEmpty()) return null;
        String id = new ArrayList<>(set).get(rand.nextInt(set.size()));

        RelicBlock relicBlock = relicBlocks.get(id);
        if (relicBlock != null) return relicBlock;
        return relicItems.get(id);
    }

    public static class RelicCategory {

        public final String name;
        public final Set<String> relicIds = new HashSet<>();

        public RelicCategory(String name) {
            this.name = name;
        }

        public void addRelic(String id) {
            relicIds.add(id);
        }

        public boolean contains(String id) {
            return relicIds.contains(id);
        }
    }
}
