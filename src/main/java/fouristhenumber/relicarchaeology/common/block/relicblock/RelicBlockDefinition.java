package fouristhenumber.relicarchaeology.common.block.relicblock;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

public class RelicBlockDefinition {

    public String relicBlockName;
    public String targetModId;
    public String targetBlock;
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
