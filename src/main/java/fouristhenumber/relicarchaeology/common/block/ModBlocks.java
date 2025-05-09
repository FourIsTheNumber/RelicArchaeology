package fouristhenumber.relicarchaeology.common.block;

import cpw.mods.fml.common.registry.GameRegistry;

public final class ModBlocks {

    public static BlockDisplayPedestal displayPedestalBlock;

    public static void init() {
        displayPedestalBlock = new BlockDisplayPedestal();
        GameRegistry.registerBlock(displayPedestalBlock, "relicDisplayPedestal");
    }
}
