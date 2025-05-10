package fouristhenumber.relicarchaeology.common.structure;

import static fouristhenumber.relicarchaeology.common.block.ModBlocks.displayPedestalBlock;
import static fouristhenumber.relicarchaeology.utils.RelicRegistry.getRelicInSet;
import static fouristhenumber.relicarchaeology.utils.RelicRegistry.getRelicsInCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import com.google.common.collect.ImmutableSet;

import cpw.mods.fml.common.registry.GameRegistry;
import fouristhenumber.relicarchaeology.common.block.RelicBlock;
import fouristhenumber.relicarchaeology.common.item.RelicItem;

public class StructureTemplate {

    public String name;
    public Set<String> allowedBiomes;
    public Set<Integer> allowedDimensions;
    public float rarity;
    public Set<String> allowedCategories;

    public Map<Character, String> palette = new HashMap<>();
    public List<List<String>> structure = new ArrayList<>();

    public void applyDefaults() {
        if (allowedCategories == null) allowedCategories = ImmutableSet.of("DEFAULT");
    }

    public void placeInWorld(World world, int x, int y, int z, Random rand) {
        int height = structure.size();

        for (int layer = 0; layer < height; layer++) {
            List<String> rows = structure.get(layer);
            for (int row = 0; row < rows.size(); row++) {
                String line = rows.get(row);
                for (int col = 0; col < line.length(); col++) {
                    char key = line.charAt(col);
                    Block blockToPlace = null;
                    int meta = 0;

                    ItemStack relicItem = null;

                    // Special cases:
                    // R = random relic block
                    // space = air
                    if (key == 'R') {
                        Set<String> allowedRelics = new HashSet<>();
                        for (String category : allowedCategories) {
                            allowedRelics.addAll(getRelicsInCategory(category));
                        }
                        Object relic = getRelicInSet(allowedRelics);
                        if (relic instanceof RelicBlock rb) {
                            blockToPlace = rb;
                        } else if (relic instanceof RelicItem ri) {
                            relicItem = new ItemStack(ri, 1);
                            blockToPlace = displayPedestalBlock;
                        }
                    } else if (key == ' ') {
                        continue;
                    } else {
                        String blockId = palette.get(key);
                        if (blockId == null || blockId.equals("air")) continue;
                        if (blockId.contains(":")) {
                            String[] parts = blockId.split(":");
                            if (parts.length == 3) {
                                blockToPlace = GameRegistry.findBlock(parts[0], parts[1]);
                                meta = parseMeta(parts[2]);
                            } else {
                                blockToPlace = GameRegistry.findBlock(parts[0], parts[1]);
                            }
                        } else {
                            blockToPlace = GameRegistry.findBlock("minecraft", blockId);
                        }
                    }

                    if (blockToPlace == null || blockToPlace == Blocks.air) continue;

                    int worldX = x + col;
                    int worldY = y + layer;
                    int worldZ = z + row;

                    world.setBlock(worldX, worldY, worldZ, blockToPlace, meta, 2);

                    // If trying to place a relicItem, find the inventory and deposit it
                    if (relicItem != null) {
                        TileEntity tile = world.getTileEntity(worldX, worldY, worldZ);
                        if (tile instanceof IInventory invTile) {
                            invTile.setInventorySlotContents(0, relicItem);
                        }
                    }
                }
            }
        }
    }

    private int parseMeta(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public boolean canSpawnHere(World world, int x, int z) {
        if (!allowedDimensions.isEmpty() && !allowedDimensions.contains(world.provider.dimensionId)) return false;

        BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
        return allowedBiomes.isEmpty() || allowedBiomes.contains(
            biome.biomeName.toLowerCase()
                .replace(" ", ""));
    }

    public static class BlockInfo {

        public Block block;
        public int meta;
        public NBTTagCompound tag;
    }
}
