package fouristhenumber.relicarchaeology.common.item;

public class RelicItemDefinition {

    public String relicName;
    public String targetModId;
    public String targetItem;
    public int targetMeta;

    public RelicItemDefinition(String relicName, String targetModId, String targetItem, int targetMeta) {
        this.relicName = relicName;
        this.targetModId = targetModId;
        this.targetItem = targetItem;
        this.targetMeta = targetMeta;
    }
}
