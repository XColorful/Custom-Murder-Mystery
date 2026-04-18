package xiao.murdermystery.common.game.process.murdermystery;

import org.jetbrains.annotations.NotNull;
import xiao.battleroyale.api.game.IGameManager;
import xiao.battleroyale.common.game.team.GamePlayer;
import xiao.murdermystery.MurderMystery;

import java.util.List;

public class _MMGameManagement {

    /**
     * 直接将所有未被淘汰的游戏玩家(无阵营)设置为生存者
     */
    protected static void setSurvivorRoles(MMGameProcessManager mmGameProcessManager, IGameManager gameManager) {
        // 只取未被淘汰的
        List<GamePlayer> standingGamePlayers = gameManager.getTeamManager().getStandingGamePlayers();
        if (standingGamePlayers.isEmpty()) {
            MurderMystery.LOGGER.debug("There's no available standing game players who doesn't has role, skipped setSurvivorRoles");
            return;
        }
        for (GamePlayer gamePlayer : standingGamePlayers) {
            // 排除已经有阵营的
            if (!mmGameProcessManager.hasRole(gamePlayer)) {
                mmGameProcessManager.setSurvivor(gamePlayer);
            }
        }
    }

    protected static void setDetectiveRoles(MMGameProcessManager mmGameProcessManager, IGameManager gameManager) {
        // 只取未被淘汰的
        List<GamePlayer> survivors = mmGameProcessManager.getStandingSurvivors();
        // 从生存者里随机取
        if (!survivors.isEmpty()) {
            mmGameProcessManager.setDetective(_getRandomPlayerFromList(survivors));
            return;
        }
        // 从未设置阵营的里取
        else {
            List<GamePlayer> standingGamePlayers = gameManager.getTeamManager().getStandingGamePlayers().stream().filter((gp) -> !mmGameProcessManager.hasRole(gp)).toList();
            if (standingGamePlayers.isEmpty()) {
                MurderMystery.LOGGER.debug("There's no available standing game players who doesn't has role, skipped setDetectiveRoles");
                return;
            }
            mmGameProcessManager.setDetective(_getRandomPlayerFromList(standingGamePlayers));
        }
    }

    protected static void setMurderRoles(MMGameProcessManager mmGameProcessManager, IGameManager gameManager) {
        // 只取未被淘汰的，且不是侦探 (侦探属于生存者阵营)
        List<GamePlayer> pureSurvivors = mmGameProcessManager.getStandingSurvivors().stream().filter((gp) -> !mmGameProcessManager.isDetective(gp)).toList();
        // 从生存者里随机取
        if (!pureSurvivors.isEmpty()) {
            mmGameProcessManager.setMurder(_getRandomPlayerFromList(pureSurvivors));
            return;
        }
        // 从未设置阵营的里取
        else {
            List<GamePlayer> standingGamePlayers = gameManager.getTeamManager().getStandingGamePlayers().stream().filter((gp) -> !mmGameProcessManager.hasRole(gp)).toList();
            if (standingGamePlayers.isEmpty()) {
                MurderMystery.LOGGER.debug("There's no available standing game players who doesn't has role, skipped setMurderRoles");
                return;
            }
            mmGameProcessManager.setMurder(_getRandomPlayerFromList(standingGamePlayers));
        }
    }

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

    private static GamePlayer _getRandomPlayerFromList(@NotNull List<GamePlayer> players) {
        if (players.size() == 1) return players.get(0);
        return players.get(MurderMystery.COMMON_RANDOM.nextInt(players.size()));
    }
}
