package xiao.murdermystery.api.game.process.murdermystery;

import org.jetbrains.annotations.NotNull;
import xiao.battleroyale.common.game.team.GamePlayer;

import java.util.ArrayList;
import java.util.List;

public interface IMurderMysteryInfoGetter {

    /**
     * 返回所属阵营成员
     */
    default @NotNull List<GamePlayer> getTeamMembers(@NotNull GamePlayer gamePlayer) {
        if (isMurder(gamePlayer)) {
            return getMurders();
        } else if (isSurvivor(gamePlayer) || isDetective(gamePlayer)) { // 实际上侦探属于生存者阵营
            return getSurvivors();
        } else {
            return new ArrayList<>();
        }
    }
    /**
     * 返回 TeamManager 过滤后的所属阵营成员
     */
    default @NotNull List<GamePlayer> getStandingTeamMembers(@NotNull GamePlayer gamePlayer) {
        if (isMurder(gamePlayer)) {
            return getStandingMurders();
        } else if (isSurvivor(gamePlayer) || isDetective(gamePlayer)) { // 实际上侦探属于生存者阵营
            return getStandingSurvivors();
        } else {
            return new ArrayList<>();
        }
    }
    default boolean isTeamEliminated(@NotNull GamePlayer gamePlayer) {
        List<GamePlayer> teamMembers;
        if (isMurder(gamePlayer)) {
            teamMembers = getStandingMurders();
        } else if (isSurvivor(gamePlayer) || isDetective(gamePlayer)) { // 实际上侦探属于生存者阵营
            teamMembers = getStandingSurvivors();
        } else {
            return false;
        }

        if (teamMembers.isEmpty()) {
            return true;
        }
        for (GamePlayer member : teamMembers) {
            if (member.isAlive() && !member.isEliminated()) {
                return false;
            }
        }
        return true;
    }

    boolean isSurvivor(@NotNull GamePlayer gamePlayer);
    boolean isDetective(@NotNull GamePlayer gamePlayer);
    boolean isMurder(@NotNull GamePlayer gamePlayer);

    List<GamePlayer> getSurvivors();
    List<GamePlayer> getDetectives();
    List<GamePlayer> getMurders();
    int getSurvivorSize();
    int getDetectiveSize();
    int getMurderSize();

    List<GamePlayer> getStandingSurvivors();
    List<GamePlayer> getStandingDetectives();
    List<GamePlayer> getStandingMurders();
    int getStandingSurvivorCount();
    int getStandingDetectiveCount();
    int getStandingMurderCount();
}
