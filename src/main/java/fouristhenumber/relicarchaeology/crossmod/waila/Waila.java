package fouristhenumber.relicarchaeology.crossmod.waila;

import cpw.mods.fml.common.event.FMLInterModComms;
import fouristhenumber.relicarchaeology.common.block.TileEntityDisplayPedestal;
import mcp.mobius.waila.api.IWailaRegistrar;

public class Waila {

    public static void callbackRegister(IWailaRegistrar registrar) {
        registrar.registerBodyProvider(new WailaPedestalHandler(), TileEntityDisplayPedestal.class);
    }

    public static void init() {
        FMLInterModComms.sendMessage("Waila", "register", Waila.class.getName() + ".callbackRegister");
    }
}
