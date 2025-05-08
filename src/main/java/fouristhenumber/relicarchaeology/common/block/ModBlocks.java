package fouristhenumber.relicarchaeology.common.block;

import net.minecraft.block.Block;

import cpw.mods.fml.common.registry.GameRegistry;

public final class ModBlocks {

    public static Block testBlock;

    public static void init() {
        testBlock = new TestBlock();
        GameRegistry.registerBlock(testBlock, "testBlock");
    }
}
