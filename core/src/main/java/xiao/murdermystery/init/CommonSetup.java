package xiao.murdermystery.init;

import xiao.battleroyale.api.init.ICommonSetup;
import xiao.murdermystery.MurderMystery;
import xiao.murdermystery.common.game.process.murdermystery.MMGameProcessManager;

public class CommonSetup implements ICommonSetup {

    private static final CommonSetup INSTANCE = new CommonSetup();

    public static CommonSetup get() {
        return INSTANCE;
    }

    private CommonSetup() {}

    @Override
    public void onCommonSetup() {
        MMGameProcessManager.init(MurderMystery.getMcSide());
    }
}
