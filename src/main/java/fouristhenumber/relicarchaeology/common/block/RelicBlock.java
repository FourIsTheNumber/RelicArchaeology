package fouristhenumber.relicarchaeology.common.block;

import java.awt.*;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class RelicBlock extends Block {

    private final Block relic;
    private final int relicMeta;

    public RelicBlock(Block relic, int relicMeta) {
        super(Material.rock);
        setBlockName(
            "relic_" + relic.getUnlocalizedName()
                .substring(5));
        setBlockTextureName("relicarchaeology:relic_block");
        setHardness(1.5F);
        setResistance(10.0F);
        setStepSound(soundTypeStone);

        this.relic = relic;
        this.relicMeta = relicMeta;
    }

    @Override
    public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer player, int side, float subX,
        float subY, float subZ) {
        if (relic != null) {
            worldIn.setBlock(x, y, z, relic, relicMeta, 3);
        }
        return true;
    }
}
