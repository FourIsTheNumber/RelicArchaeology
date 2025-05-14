package fouristhenumber.relicarchaeology.common.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemRelicBlock extends ItemBlock {

    public ItemRelicBlock(Block block) {
        super(block);
        this.setMaxStackSize(1);
    }
}
