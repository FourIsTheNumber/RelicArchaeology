package fouristhenumber.relicarchaeology.common.item;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

public class RelicItemDefinition {

    public String relicName;
    public String targetModId;
    public String targetItem;
    public int targetMeta;
    public Set<String> categories;

    public void applyDefaults() {
        if (targetModId == null) {
            targetModId = "minecraft";
        }
        if (categories == null) {
            categories = ImmutableSet.of("DEFAULT");
        }
    }
}
