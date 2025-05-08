package fouristhenumber.relicarchaeology.common.structure;

import java.util.Random;

import net.minecraft.world.World;

import fouristhenumber.relicarchaeology.common.block.ModBlocks;

public class StructureTest {

    public void generate(World world, Random rand, int x, int y, int z) {
        // 3x3x3 cube
        for (int dx = 0; dx < 3; dx++) {
            for (int dy = 0; dy < 3; dy++) {
                for (int dz = 0; dz < 3; dz++) {
                    if (dx == 1 && dy == 1 && dz == 1) {
                        // world.setBlock(x + dx, y + dy, z + dz, ModBlocks.enchantmentRelic);
                    } else {
                        world.setBlock(x + dx, y + dy, z + dz, ModBlocks.testBlock);
                    }
                }
            }
        }
    }
}
