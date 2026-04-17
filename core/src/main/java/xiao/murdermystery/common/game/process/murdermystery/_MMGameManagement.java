package xiao.murdermystery.common.game.process.murdermystery;

import xiao.battleroyale.api.game.IGameManager;
import xiao.battleroyale.common.game.team.GamePlayer;

import java.util.List;

public class _MMGameManagement {

    protected static void finishGameAddWinner(MMGameProcessManager mmGameProcessManager, IGameManager gameManager, boolean hasWinner) {
        gameManager.setHasWinner(hasWinner);
        if (hasWinner) {
            int standingSurvivorCount = mmGameProcessManager.getStandingSurvivorCount();
            int standingMurderCount = mmGameProcessManager.getStandingMurderCount();
            List<GamePlayer> winnerGamePlayers;

            // 没有生存者或杀手存活
            if (standingSurvivorCount == 0 && standingMurderCount == 0) return;
            // 生存者胜利
            else if (standingSurvivorCount > 0) {
                winnerGamePlayers = mmGameProcessManager.getSurvivors();
            }
            // 杀手胜利
            else {
                winnerGamePlayers = mmGameProcessManager.getMurders();
            }

            for (GamePlayer gamePlayer : winnerGamePlayers) {
                gameManager.addWinnerGamePlayer(gamePlayer);
                // 不添加 GameTeam (独立的阵营)
            }
        }
    }
}
