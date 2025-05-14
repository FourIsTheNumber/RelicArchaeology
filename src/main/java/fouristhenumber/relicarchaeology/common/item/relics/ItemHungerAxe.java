package fouristhenumber.relicarchaeology.common.item.relics;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemHungerAxe extends ItemAxe {

    public ItemHungerAxe() {
        super(ToolMaterial.EMERALD);
        setTextureName("relicarchaeology:hungerAxe");
        setUnlocalizedName("hungerAxe");
        setMaxDamage(0);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean p_77624_4_) {
        tooltip.add(EnumChatFormatting.BLUE + "Rare" + EnumChatFormatting.GRAY + " Artifact");
        tooltip.add(EnumChatFormatting.AQUA + "Sates the bearer's hunger while held");
        tooltip.add(EnumChatFormatting.AQUA + "Can consume hunger to heal struck entities");
        tooltip.add(EnumChatFormatting.AQUA + "This effect is lethal to most undead");
        super.addInformation(stack, player, tooltip, p_77624_4_);
    }

    // Restore hunger every 2 seconds
    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected) {
        if (entity instanceof EntityPlayer player && isSelected && world.getTotalWorldTime() % 40 == 0) {
            player.getFoodStats()
                .addStats(2, 0.4F);
        }
    }

    @Override
    public boolean isDamageable() {
        return false;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return false;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z,
        EntityLivingBase entity) {
        return true;
    }
}
