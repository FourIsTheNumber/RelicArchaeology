package fouristhenumber.relicarchaeology.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class TestBlock extends Block {

    protected TestBlock() {
        super(Material.rock);
        setBlockName("testBlock");
        setBlockTextureName("relicarchaeology:test_block");
        setHardness(1.5F);
        setResistance(10.0F);
        setStepSound(soundTypeStone);
    }
}
