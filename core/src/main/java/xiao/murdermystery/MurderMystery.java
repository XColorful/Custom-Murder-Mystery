package xiao.murdermystery;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;
import xiao.battleroyale.api.common.McSide;

import java.util.Random;

public class MurderMystery {
    public static final String MOD_ID = "murdermystery";
    public static final String MOD_NAME_SHORT = "cmm";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final Random COMMON_RANDOM = new Random();

    protected static boolean initialized;
    protected static McSide mcSide = McSide.CLIENT;

    public static void init(McSide mcSide) {
        if (initialized) return;

        MurderMystery.mcSide = mcSide;

        initialized = true;
    }

    public static McSide getMcSide() {
        return mcSide;
    }
}
