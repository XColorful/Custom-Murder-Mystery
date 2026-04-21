package xiao.murdermystery.compat.neoforge;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;
import xiao.battleroyale.api.common.McSide;
import xiao.murdermystery.MurderMystery;

@Mod(MurderMystery.MOD_ID)
public class MurderMysteryNeoforge {

    public MurderMysteryNeoforge(IEventBus modEventBus) {
        Dist dist = FMLLoader.getDist();
        McSide mcSide = dist.isClient() ? McSide.CLIENT : McSide.DEDICATED_SERVER;

        MurderMystery.init(mcSide);
    }
}
