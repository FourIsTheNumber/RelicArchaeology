package fouristhenumber.relicarchaeology.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import cpw.mods.fml.common.registry.GameRegistry;

public class RelicBlock extends Block {

    private Block relic;
    private String targetModId;
    private String targetBlock;
    private int relicMeta;

    public RelicBlock(String name) {
        super(Material.rock);
        setBlockName(name);
        setBlockTextureName("relicarchaeology:relic_block");
        setHardness(1.5F);
        setResistance(10.0F);
        setStepSound(soundTypeStone);
    }

    // Run during preInit to populate config file values
    public void bindTarget(String targetBlock, String targetModId, int relicMeta) {
        this.targetBlock = targetBlock;
        this.targetModId = targetModId;
        this.relicMeta = relicMeta;
    }

    // Run during postInit to actually find the relevant blocks
    public void activateBinding() {
        Block block = GameRegistry.findBlock(targetModId, targetBlock);
        if (block == null) {
            System.err.println("Relic target block not found: " + targetModId + ":" + targetBlock);
            return;
        }
        relic = block;
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
