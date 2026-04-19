package xiao.murdermystery.common.game.process.murdermystery;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import xiao.battleroyale.BattleRoyale;
import xiao.battleroyale.api.event.*;
import xiao.battleroyale.api.minecraft.TriResult;
import xiao.battleroyale.common.game.team.GamePlayer;
import xiao.murdermystery.MurderMystery;
import xiao.murdermystery.api.game.process.murdermystery.IMMItemTagApi;
import xiao.murdermystery.api.game.process.murdermystery.IMurderMysteryProcessManager;

public class _MMPickupEventHandler implements IEventHandler {

    private static class _MMPickupEventHandlerHolder {
        private static final _MMPickupEventHandler INSTANCE = new _MMPickupEventHandler();
    }

    public static _MMPickupEventHandler get() {
        return _MMPickupEventHandlerHolder.INSTANCE;
    }

    protected  _MMPickupEventHandler() {}

    @Override
    public String getEventHandlerName() {
        return String.format("%s:_MMPickupEventHandler", MurderMystery.MOD_ID);
    }

    public static void register(ICustomEventRegister eventRegister) {
        eventRegister.register(get(), EventType.ITEM_ENTITY_PICKUP_EVENT);
    }
    public static void unregister(ICustomEventRegister eventRegister) {
        eventRegister.unregister(get(), EventType.ITEM_ENTITY_PICKUP_EVENT);
    }

    @Override
    public void handleEvent(EventType eventType, IEvent event) {
        if (eventType == EventType.ITEM_ENTITY_PICKUP_EVENT) {
            onFilterItem((IItemEntityPickupEvent) event, MMGameProcessManager.get());
        } else {
            onReceiveWrongEvent(eventType);
        }
    }

    public void onFilterItem(IItemEntityPickupEvent event, IMurderMysteryProcessManager manager) {
        Player player = event.getPlayer();
        ItemEntity itemEntity = event.getItemEntity();
        IMMItemTagApi itemTagApi = manager.getItemTagApi();
        // 生存者物品
        if (itemTagApi.isSurvivorItem(itemEntity)) {
            @Nullable GamePlayer gamePlayer = BattleRoyale.getGameManager().getTeamManager().getGamePlayerByUUID(player.getUUID());
            if (gamePlayer == null || !manager.isSurvivor(gamePlayer)) {
                event.setCanPickup(TriResult.DENY); // 光有这个似乎不够
                event.setCanceled(true);
            }
        }
        // 杀手物品
        else if (itemTagApi.isMurdererItem(itemEntity)) {
            @Nullable GamePlayer gamePlayer = BattleRoyale.getGameManager().getTeamManager().getGamePlayerByUUID(player.getUUID());
            if (gamePlayer == null || !manager.isMurderer(gamePlayer)) {
                event.setCanPickup(TriResult.DENY); // 光有这个似乎不够
                event.setCanceled(true);
            }
        }
        // 正常物品
        else {
        }
    }
}
