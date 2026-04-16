package xiao.murdermystery.event.custom;

import xiao.battleroyale.api.event.CustomEventType;
import xiao.battleroyale.api.event.EventPriority;
import xiao.battleroyale.api.event.ICustomEventHandler;
import xiao.battleroyale.api.event.ICustomEventRegister;
import xiao.murdermystery.MurderMystery;
import xiao.murdermystery.common.game.process.murdermystery._MMRegister;

public class CustomEventHandler {

    public static void registerAll(ICustomEventRegister customEventRegister) {
        register(customEventRegister, _MMRegister.get(), CustomEventType.REGISTER_MANAGER_EVENT, EventPriority.NORMAL, false);

        if (MurderMystery.getMcSide().isClientSide()) {
            registerClient(customEventRegister);
        }
    }

    public static void registerClient(ICustomEventRegister customEventRegister) {
    }

    private static void register(ICustomEventRegister customEventRegister, ICustomEventHandler eventHandler, CustomEventType customEventType, EventPriority priority, boolean receiveCanceled) {
        if (customEventRegister.register(eventHandler, customEventType, priority, receiveCanceled)) {
            MurderMystery.LOGGER.debug("{} registered to {}", eventHandler.getEventHandlerName(), customEventType);
        } else {
            MurderMystery.LOGGER.debug("Failed to register {} to {}", eventHandler.getEventHandlerName(), customEventType);
        }
    }
}
