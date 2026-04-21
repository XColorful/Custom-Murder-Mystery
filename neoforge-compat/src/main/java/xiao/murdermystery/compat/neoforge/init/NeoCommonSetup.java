package xiao.murdermystery.compat.neoforge.init;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import xiao.battleroyale.api.init.ICommonSetup;
import xiao.murdermystery.MurderMystery;
import xiao.murdermystery.init.CommonSetup;

@Mod.EventBusSubscriber(modid = MurderMystery.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NeoCommonSetup {

    private static final ICommonSetup COMMON_SETUP = CommonSetup.get();

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(COMMON_SETUP::onCommonSetup);
    }
}
