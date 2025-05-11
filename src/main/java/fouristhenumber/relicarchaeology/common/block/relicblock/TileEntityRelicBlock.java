package fouristhenumber.relicarchaeology.common.block.relicblock;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

public class TileEntityRelicBlock extends TileEntity {

    public final String relicName;
    public final Block relic;
    public final int targetMeta;

    public TileEntityRelicBlock(String relicName, Block relic, int targetMeta) {
        super();
        this.relicName = relicName;
        this.relic = relic;
        this.targetMeta = targetMeta;
    }
}
