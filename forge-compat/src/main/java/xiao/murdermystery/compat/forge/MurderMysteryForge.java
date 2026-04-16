package xiao.murdermystery.compat.forge;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.common.Mod;
import xiao.battleroyale.api.common.McSide;
import xiao.murdermystery.MurderMystery;

@Mod(MurderMystery.MOD_ID)
public class MurderMysteryForge {

    public MurderMysteryForge() {
        Dist dist = FMLLoader.getDist();
        McSide mcSide = dist.isClient() ? McSide.CLIENT : McSide.DEDICATED_SERVER;

        MurderMystery.init(mcSide);
    }
}
