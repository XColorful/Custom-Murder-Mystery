package xiao.murdermystery.common.game.process.murdermystery;

import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xiao.battleroyale.BattleRoyale;
import xiao.battleroyale.api.common.McSide;
import xiao.battleroyale.api.event.ILivingDamageEvent;
import xiao.battleroyale.api.event.ILivingDeathEvent;
import xiao.battleroyale.api.game.IGameManager;
import xiao.battleroyale.common.game.process.battleroyale.BRGameProcessManager;
import xiao.battleroyale.common.game.team.GamePlayer;
import xiao.battleroyale.common.game.team.GameTeam;
import xiao.murdermystery.api.game.process.murdermystery.IMurderMysteryProcessManager;

import java.util.Set;

public class MMGameProcessManager extends BRGameProcessManager implements IMurderMysteryProcessManager {

    private static class MMGameProcessManagerHolder {
        private static final MMGameProcessManager INSTANCE = new MMGameProcessManager();
    }

    public static MMGameProcessManager get() {
        return MMGameProcessManagerHolder.INSTANCE;
    }

    protected MMGameProcessManager() {}

    public static void init(McSide mcSide) {
    }

    protected final MMData murderMysteryData = new MMData();

    public static final String _MANAGER_NAME = String.format("%s:MMGameProcessManager", BattleRoyale.MOD_ID);
    @Override public String getManagerName() {
        return _MANAGER_NAME;
    }

    @Override
    public void initGameConfig(ServerLevel serverLevel) {
        super.initGameConfig(serverLevel);
        if (!isConfigPrepared()) {
            return;
        }
    }

    @Override
    public void initGame(ServerLevel serverLevel) {
        super.initGame(serverLevel);
    }


    @Override
    public boolean startGame(ServerLevel serverLevel) {
        if (!super.startGame(serverLevel)) {
            return false;
        }

        return true;
    }

    @Override
    public void stopGame(@Nullable ServerLevel serverLevel) {
        super.stopGame(serverLevel);
    }

    @Override
    public void onGameTick(int gameTime) {
        super.onGameTick(gameTime);
    }

    public void finishGameIfShouldEnd(IGameManager gameManager) {
        if (!gameManager.isInGame()) {
            return;
        }
    }

    // --------IGameManagement--------

    @Override public void finishGameAddWinner(boolean hasWinner) {
    }

    // --------IGameNotification--------

    @Override public void sendWinnerResult(@Nullable ServerLevel serverLevel, Set<GamePlayer> winnerGamePlayers, Set<GameTeam> winnerGameTeams, int gameTime) {
    }

    @Override public void notifyWinner(@Nullable ServerLevel serverLevel, @NotNull GamePlayer gamePlayer, int winnerParticleId) {
    }

    @Override public void sendDownMessage(@Nullable ServerLevel serverLevel, @NotNull GamePlayer gamePlayer) {
    }

    @Override public void sendReviveMessage(@Nullable ServerLevel serverLevel, @NotNull GamePlayer gamePlayer) {
    }

    @Override public void sendEliminateMessage(@Nullable ServerLevel serverLevel, @NotNull GamePlayer gamePlayer) {
    }

    // --------IGameEventHandler--------

    @Override public boolean onPlayerDamage(ILivingDamageEvent event, @NotNull GamePlayer gamePlayer) {
        return true;
    }

    @Override public boolean onPlayerDown(ILivingDeathEvent event, @NotNull GamePlayer gamePlayer, boolean removeInvalidTeam) {
        return true;
    }

    @Override public boolean onPlayerDeath(@Nullable ILivingDeathEvent event, @Nullable ServerLevel serverLevel, @NotNull GamePlayer gamePlayer) {
        return true;
    }

}
