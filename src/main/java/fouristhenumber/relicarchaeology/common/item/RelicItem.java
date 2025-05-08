package fouristhenumber.relicarchaeology.common.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class RelicItem extends Item {

    private final String relicName;
    private final Item targetItem;
    private final int targetMeta;

    public RelicItem(String relicName, Item targetItem, int targetMeta) {
        this.relicName = relicName;
        this.targetItem = targetItem;
        this.targetMeta = targetMeta;
        this.setUnlocalizedName("relicarchaeology." + relicName);
    }

    public String getRelicName() {
        return relicName;
    }

    public ItemStack getRestoredItem() {
        return new ItemStack(targetItem, 1, targetMeta);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer player) {
        itemStackIn = getRestoredItem();
        return itemStackIn;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean par4) {
        list.add(EnumChatFormatting.GRAY + "An ancient item...");
    }

    @Override
    public void registerIcons(IIconRegister register) {
        this.itemIcon = register.registerIcon("relicmod:generated/" + relicName);
    }
}
