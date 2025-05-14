package fouristhenumber.relicarchaeology.common.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import cpw.mods.fml.common.registry.GameRegistry;

public class RelicItem extends Item {

    private final String relicName;
    private String targetModId;
    private String targetItem;
    private int targetMeta;

    private Item relic;

    public RelicItem(String relicName) {
        this.relicName = relicName;
        this.setUnlocalizedName("relicarchaeology." + relicName);
        setMaxStackSize(1);
    }

    public String getRelicName() {
        return relicName;
    }

    public Item getRelic() {
        return relic;
    }

    public int getTargetMeta() {
        return targetMeta;
    }

    // Run during preInit to populate config file values
    public void bindTarget(String targetItem, String targetModId, int targetMeta) {
        this.targetItem = targetItem;
        this.targetModId = targetModId;
        this.targetMeta = targetMeta;
    }

    // Run during postInit to actually find the relevant blocks
    public void activateBinding() {
        Item item = GameRegistry.findItem(targetModId, targetItem);
        if (item == null) {
            System.err.println("Relic target item not found: " + targetModId + ":" + targetItem);
            return;
        }
        relic = item;
    }

    public ItemStack getRestoredItem() {
        return new ItemStack(relic, 1, targetMeta);
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
        this.itemIcon = register.registerIcon("relicarchaeology:generated/" + relicName);
    }
}
