package xiao.murdermystery.api.event.custom.murdermystery;

import org.jetbrains.annotations.NotNull;
import xiao.battleroyale.api.event.CustomEvent;
import xiao.battleroyale.api.game.process.IGameProcessManager;
import xiao.murdermystery.api.game.process.murdermystery.IMurderMysteryProcessManager;

public abstract class MurderMysteryEvent extends CustomEvent {

    protected final @NotNull IMurderMysteryProcessManager manager;

    public MurderMysteryEvent(@NotNull IMurderMysteryProcessManager manager) {
        this.manager = manager;
    }
    public @NotNull IGameProcessManager getGameProcessManager() {
        return getManager();
    }
    public @NotNull IMurderMysteryProcessManager getManager() {
        return manager;
    }
}
