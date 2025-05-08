package fouristhenumber.relicarchaeology.common.structure;

import static fouristhenumber.relicarchaeology.RelicArchaeology.structureDefinitions;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import cpw.mods.fml.common.IWorldGenerator;

public class StructureGenHandler implements IWorldGenerator {

    @Override
    public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
        IChunkProvider chunkProvider) {
        int x = chunkX * 16 + rand.nextInt(16);
        int z = chunkZ * 16 + rand.nextInt(16);
        int y = world.getTopSolidOrLiquidBlock(x, z);

        for (StructureTemplate template : structureDefinitions) {
            if (rand.nextFloat() > template.rarity) continue;
            if (!template.canSpawnHere(world, x, z)) continue;

            template.placeInWorld(world, x, y, z, rand);
            break;
        }
    }
}
