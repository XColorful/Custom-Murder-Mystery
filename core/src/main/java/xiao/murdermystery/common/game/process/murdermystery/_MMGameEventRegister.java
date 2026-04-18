package xiao.murdermystery.common.game.process.murdermystery;

import xiao.battleroyale.BattleRoyale;
import xiao.battleroyale.api.event.CustomEventType;
import xiao.battleroyale.api.event.ICustomEvent;
import xiao.battleroyale.api.event.ICustomEventHandler;
import xiao.battleroyale.api.event.ICustomEventRegister;
import xiao.murdermystery.MurderMystery;

public class _MMGameEventRegister implements ICustomEventHandler {

    private static class _MMGameEventRegisterHolder {
        private static final _MMGameEventRegister INSTANCE = new _MMGameEventRegister();
    }

    public static _MMGameEventRegister get() {
        return _MMGameEventRegisterHolder.INSTANCE;
    }

    protected _MMGameEventRegister() {}

    @Override
    public String getEventHandlerName() {
        return String.format("%s:_MMGameEventRegister", MurderMystery.MOD_ID);
    }

    public static void register() {
        ICustomEventRegister eventRegister = BattleRoyale.getEventRegister();
        eventRegister.register(get(), CustomEventType.GAME_START_FINISH_EVENT);
        eventRegister.register(get(), CustomEventType.GAME_STOP_FINISH_EVENT);
    }
    public static void unregister() {
        ICustomEventRegister eventRegister = BattleRoyale.getEventRegister();
        eventRegister.unregister(get(), CustomEventType.GAME_START_FINISH_EVENT);
        eventRegister.unregister(get(), CustomEventType.GAME_STOP_FINISH_EVENT);
        _MMPickupEventHandler.unregister(eventRegister);
        _MMApiFunctionRegistration.unregister(MMGameProcessManager.get());
    }

    @Override
    public void handleEvent(CustomEventType customEventType, ICustomEvent event) {
        switch (customEventType) {
            case GAME_START_FINISH_EVENT -> {
                _MMPickupEventHandler.register(BattleRoyale.getEventRegister());
                _MMApiFunctionRegistration.register(MMGameProcessManager.get());
            }
            case GAME_STOP_FINISH_EVENT -> {
                _MMPickupEventHandler.unregister(BattleRoyale.getEventRegister());
                _MMApiFunctionRegistration.unregister(MMGameProcessManager.get());
            }
            default -> {
                onReceiveWrongEvent(customEventType);
            }
        }
    }
}
