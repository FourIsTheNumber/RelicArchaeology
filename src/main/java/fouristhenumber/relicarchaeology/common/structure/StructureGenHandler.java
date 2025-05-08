package fouristhenumber.relicarchaeology.common.structure;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import cpw.mods.fml.common.IWorldGenerator;

public class StructureGenHandler implements IWorldGenerator {

    private final StructureTest structure = new StructureTest();

    @Override
    public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
        IChunkProvider chunkProvider) {

        int x = chunkX * 16;
        int z = chunkZ * 16;

        if (world.provider.dimensionId != 0) return;

        if (rand.nextInt(20) != 0) return;

        int y = world.getTopSolidOrLiquidBlock(x, z);
        structure.generate(world, rand, x + rand.nextInt(16), y, z + rand.nextInt(16));
    }
}
