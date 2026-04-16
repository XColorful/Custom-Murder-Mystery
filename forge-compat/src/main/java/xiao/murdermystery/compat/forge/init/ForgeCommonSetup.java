package xiao.murdermystery.compat.forge.init;

import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import xiao.battleroyale.api.init.ICommonSetup;
import xiao.murdermystery.MurderMystery;
import xiao.murdermystery.init.CommonSetup;

@Mod.EventBusSubscriber(modid = MurderMystery.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeCommonSetup {

    private static final ICommonSetup COMMON_SETUP = CommonSetup.get();

    @SubscribeEvent(priority = EventPriority.LOW) // 扩展模组在主模组之后
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(COMMON_SETUP::onCommonSetup);
    }
}
