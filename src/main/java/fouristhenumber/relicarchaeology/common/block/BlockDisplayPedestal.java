package fouristhenumber.relicarchaeology.common.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDisplayPedestal extends BlockContainer {

    ItemStack cachedItem;

    public BlockDisplayPedestal() {
        super(Material.rock);
        setBlockName("relicDisplayPedestal");
        setBlockTextureName("relicarchaeology:display_pedestal");
        setHardness(1.5F);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityDisplayPedestal();
    }

    public void setCachedItem(ItemStack cachedItem) {
        this.cachedItem = cachedItem;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX,
        float hitY, float hitZ) {
        if (!world.isRemote) {
            TileEntityDisplayPedestal te = (TileEntityDisplayPedestal) world.getTileEntity(x, y, z);
            if (te != null) {
                ItemStack held = player.getHeldItem();
                ItemStack inv = te.getStack();

                if (inv == null && held != null) {
                    te.setInventorySlotContents(0, held.splitStack(1));
                } else if (inv != null) {
                    ItemStack copy = inv.copy();
                    if (!player.inventory.addItemStackToInventory(copy)) {
                        player.dropPlayerItemWithRandomChoice(copy, false);
                    }
                    te.setInventorySlotContents(0, null);
                }
                world.markBlockForUpdate(x, y, z);
                player.inventory.markDirty();
                ((EntityPlayerMP) player).sendContainerToPlayer(player.inventoryContainer);
            }
        }
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

    @Override
    public int getRenderType() {
        return RenderPedestalInventory.renderId;
    }

    @Override
    public boolean isBlockNormalCube() {
        return false;
    }

    @Override
    public boolean hasTileEntity(int meta) {
        return true;
    }

    // Sets icon for Minecraft to use when generating break particles
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return Blocks.cobblestone.getIcon(0, 0);
    }
}
