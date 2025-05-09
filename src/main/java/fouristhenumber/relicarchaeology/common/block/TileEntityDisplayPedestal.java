package fouristhenumber.relicarchaeology.common.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityDisplayPedestal extends TileEntity implements IInventory {

    private ItemStack inventory;

    @Override
    public int getSizeInventory() {
        return 1;
    }

    public ItemStack getStack() {
        return getStackInSlot(0);
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return inventory;
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack stack) {
        inventory = stack;
        markDirty();
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tag = new NBTTagCompound();
        writeToNBT(tag);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        readFromNBT(pkt.func_148857_g());
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    @Override
    public ItemStack decrStackSize(int i, int count) {
        if (inventory != null) {
            ItemStack itemstack;
            if (inventory.stackSize <= count) {
                itemstack = inventory;
                inventory = null;
            } else {
                itemstack = inventory.splitStack(count);
                if (inventory.stackSize == 0) inventory = null;
            }
            return itemstack;
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        if (inventory != null) {
            ItemStack itemstack = inventory;
            inventory = null;
            return itemstack;
        }
        return null;
    }

    @Override
    public String getInventoryName() {
        return "container.relic_pedestal";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) <= 64;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack) {
        return true;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        if (tag.hasKey("item")) {
            inventory = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("item"));
        } else {
            inventory = null;
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        if (inventory != null) {
            NBTTagCompound itemTag = new NBTTagCompound();
            inventory.writeToNBT(itemTag);
            tag.setTag("item", itemTag);
        }
    }
}
