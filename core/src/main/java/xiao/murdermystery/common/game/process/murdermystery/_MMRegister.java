package xiao.murdermystery.common.game.process.murdermystery;

import xiao.battleroyale.BattleRoyale;
import xiao.battleroyale.api.event.CustomEventType;
import xiao.battleroyale.api.event.ICustomEvent;
import xiao.battleroyale.api.event.ICustomEventHandler;
import xiao.battleroyale.api.event.special.RegisterManagerEvent;
import xiao.battleroyale.util.StringUtils;
import xiao.murdermystery.MurderMystery;

public class _MMRegister implements ICustomEventHandler {

    private static class _MMRegisterHolder {
        private static final _MMRegister INSTANCE = new _MMRegister();
    }

    public static _MMRegister get() {
        return _MMRegisterHolder.INSTANCE;
    }

    private _MMRegister() {}

    @Override
    public String getEventHandlerName() {
        return String.format("%s:MMRegister", MurderMystery.MOD_ID);
    }

    @Override
    public void handleEvent(CustomEventType customEventType, ICustomEvent event) {
        if (customEventType == CustomEventType.REGISTER_MANAGER_EVENT) {
            registerMMManager((RegisterManagerEvent) event);
        } else {
            onReceiveWrongEvent(customEventType);
        }
    }

    private void registerMMManager(RegisterManagerEvent event) {
        StringUtils.ProtocolString protocolString = event.getProtocolString();
        if (protocolString.namespace.equals(MurderMystery.MOD_ID) || protocolString.namespace.equals(MurderMystery.MOD_NAME_SHORT)) {
            if (protocolString.name.equals("MMGameProcessManager")) {
                boolean registered = BattleRoyale.getGameManager().setGameProcessManager(MMGameProcessManager.get());
                event.setCanceled(registered);
            }
        }
    }
}