package xiao.murdermystery.api.game.process.murdermystery;

import org.jetbrains.annotations.NotNull;
import xiao.battleroyale.api.game.process.IGameManagement;
import xiao.battleroyale.common.game.team.GamePlayer;

public interface IMurderMysteryGameManagement extends IGameManagement {

    boolean setSurvivor(@NotNull GamePlayer gamePlayer);
    boolean setDetective(@NotNull GamePlayer gamePlayer);
    boolean setMurder(@NotNull GamePlayer gamePlayer);
}
