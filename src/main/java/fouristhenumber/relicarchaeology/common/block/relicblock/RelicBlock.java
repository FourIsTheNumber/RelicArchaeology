package fouristhenumber.relicarchaeology.common.block.relicblock;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import cpw.mods.fml.common.registry.GameRegistry;

public class RelicBlock extends Block {

    private Block relic;
    private String targetModId;
    private String targetBlock;
    private int targetMeta;
    private String relicName;

    public RelicBlock(String name) {
        super(Material.rock);
        setBlockName(name);
        this.relicName = name;
        // setBlockTextureName("relicarchaeology:relic_block");
        setHardness(1.5F);
        setResistance(10.0F);
        setStepSound(soundTypeStone);
    }

    // Run during preInit to populate config file values
    public void bindTarget(String targetBlock, String targetModId, int targetMeta) {
        this.targetBlock = targetBlock;
        this.targetModId = targetModId;
        this.targetMeta = targetMeta;
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
            worldIn.setBlock(x, y, z, relic, targetMeta, 3);
        }
        return true;
    }

    public Block getRelic() {
        return relic;
    }

    public int getTargetMeta() {
        return targetMeta;
    }

    public String getRelicName() {
        return relicName;
    }

    @Override
    public int getRenderType() {
        return RenderRelicBlockInventory.renderId;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TileEntityRelicBlock(relicName, relic, targetMeta);
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }
}
