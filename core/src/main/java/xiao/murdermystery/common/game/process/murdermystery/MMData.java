package xiao.murdermystery.common.game.process.murdermystery;

import org.jetbrains.annotations.NotNull;
import xiao.battleroyale.BattleRoyale;
import xiao.battleroyale.api.game.team.ITeamManager;
import xiao.battleroyale.common.game.AbstractGameManagerData;
import xiao.battleroyale.common.game.team.GamePlayer;
import xiao.battleroyale.util.ClassUtils;
import xiao.murdermystery.MurderMystery;
import xiao.murdermystery.api.game.process.murdermystery.IMurderMysteryDataManagement;
import xiao.murdermystery.api.game.process.murdermystery.MurderMysteryRole;

import java.util.ArrayList;
import java.util.List;

public class MMData extends AbstractGameManagerData implements IMurderMysteryDataManagement {

    private static final String DATA_NAME = "MurderMysteryData";

    private final ClassUtils.ArraySet<GamePlayer> survivorGamePlayers;
    private final ClassUtils.ArraySet<GamePlayer> detectiveGamePlayers;
    private final ClassUtils.ArraySet<GamePlayer> murdererGamePlayers;

    public MMData() {
        super(DATA_NAME);
        this.survivorGamePlayers = new ClassUtils.ArraySet<>();
        this.detectiveGamePlayers = new ClassUtils.ArraySet<>();
        this.murdererGamePlayers = new ClassUtils.ArraySet<>();
    }

    @Override
    public void clear() {
        if (locked) return;

        this.survivorGamePlayers.clear();
        this.detectiveGamePlayers.clear();
        this.murdererGamePlayers.clear();
    }

    @Override
    public void startGame() {
        if (locked) return;

        clear(); // 内部保证，无需关心外部是否手动清理

        lockData();
    }

    @Override
    public void endGame() {
        if (locked) {
            unlockData();
        }

        // 游戏结束后仍需要查询信息
//        clear();
    }

    public boolean setSurvivor(@NotNull GamePlayer gamePlayer) {
        if (!locked) return false;
        if (!BattleRoyale.getGameManager().getTeamManager().hasStandingGamePlayer(gamePlayer.getPlayerUUID())) {
            MurderMystery.LOGGER.debug("MMData: GamePlayer {} is not standing game player, reject to set survivor", gamePlayer.getNameWithId());
            return false;
        }
        if (!survivorGamePlayers.add(gamePlayer)) {
            MurderMystery.LOGGER.debug("MMData: GamePlayer {} is already survivor", gamePlayer.getNameWithId());
            return false;
        }
        detectiveGamePlayers.remove(gamePlayer);
        murdererGamePlayers.remove(gamePlayer);
        return true;
    }

    public boolean setDetective(@NotNull GamePlayer gamePlayer) {
        if (!locked) return false;
        if (!BattleRoyale.getGameManager().getTeamManager().hasStandingGamePlayer(gamePlayer.getPlayerUUID())) {
            MurderMystery.LOGGER.debug("MMData: GamePlayer {} is not standing game player, reject to set detective", gamePlayer.getNameWithId());
            return false;
        }
        if (!detectiveGamePlayers.add(gamePlayer)) {
            MurderMystery.LOGGER.debug("MMData: GamePlayer {} is already detective", gamePlayer.getNameWithId());
            return false;
        }
        survivorGamePlayers.add(gamePlayer);
        murdererGamePlayers.remove(gamePlayer);
        return true;
    }

    public boolean setMurderer(@NotNull GamePlayer gamePlayer) {
        if (!locked) return false;
        if (!BattleRoyale.getGameManager().getTeamManager().hasStandingGamePlayer(gamePlayer.getPlayerUUID())) {
            MurderMystery.LOGGER.debug("MMData: GamePlayer {} is not standing game player, reject to set murder", gamePlayer.getNameWithId());
            return false;
        }
        if (!murdererGamePlayers.add(gamePlayer)) {
            MurderMystery.LOGGER.debug("MMData: GamePlayer {} is already murder", gamePlayer.getNameWithId());
            return false;
        }
        survivorGamePlayers.remove(gamePlayer);
        detectiveGamePlayers.remove(gamePlayer);
        return true;
    }

    // --------IMurderMysteryDataManagement--------

    // --------IMurderMysteryInfoGetter--------

    @Override public @NotNull MurderMysteryRole getRole(@NotNull GamePlayer gamePlayer) {
        if (isDetective(gamePlayer)) { // 侦探同属于生存者阵营
            return MurderMysteryRole.DETECTIVE;
        } else if (isSurvivor(gamePlayer)) {
            return MurderMysteryRole.SURVIVOR;
        } else if (isMurderer(gamePlayer)) {
            return MurderMysteryRole.MURDERER;
        } else {
            return MurderMysteryRole.NONE;
        }
    }
    @Override public boolean isSurvivor(@NotNull GamePlayer gamePlayer) {
        return survivorGamePlayers.contains(gamePlayer);
    }
    @Override public boolean isDetective(@NotNull GamePlayer gamePlayer) {
        return detectiveGamePlayers.contains(gamePlayer);
    }
    @Override public boolean isMurderer(@NotNull GamePlayer gamePlayer) {
        return murdererGamePlayers.contains(gamePlayer);
    }
    @Override public List<GamePlayer> getSurvivors() {
        return survivorGamePlayers.asList();
    }
    @Override public List<GamePlayer> getDetectives() {
        return detectiveGamePlayers.asList();
    }
    @Override public List<GamePlayer> getMurderers() {
        return murdererGamePlayers.asList();
    }
    @Override public int getSurvivorSize() {
        return survivorGamePlayers.size();
    }
    @Override public int getDetectiveSize() {
        return detectiveGamePlayers.size();
    }
    @Override public int getMurdererSize() {
        return murdererGamePlayers.size();
    }
    @Override public List<GamePlayer> getStandingSurvivors() {
        return getStandingPlayers(survivorGamePlayers);
    }
    @Override public List<GamePlayer> getStandingDetectives() {
        return getStandingPlayers(detectiveGamePlayers);
    }
    @Override public List<GamePlayer> getStandingMurderers() {
        return getStandingPlayers(murdererGamePlayers);
    }
    @Override public int getStandingSurvivorCount() {
        return countStandingPlayers(survivorGamePlayers);
    }
    @Override public int getStandingDetectiveCount() {
        return countStandingPlayers(detectiveGamePlayers);
    }
    @Override public int getStandingMurdererCount() {
        return countStandingPlayers(murdererGamePlayers);
    }

    private int countStandingPlayers(ClassUtils.ArraySet<GamePlayer> gamePlayers) {
        ITeamManager teamManager = BattleRoyale.getGameManager().getTeamManager();
        int count = 0;
        for (GamePlayer gamePlayer : gamePlayers) {
            if (teamManager.hasStandingGamePlayer(gamePlayer.getPlayerUUID())) count++;
        }
        return count;
    }
    private List<GamePlayer> getStandingPlayers(ClassUtils.ArraySet<GamePlayer> gamePlayers) {
        ITeamManager teamManager = BattleRoyale.getGameManager().getTeamManager();
        List<GamePlayer> tmp = new ArrayList<>();
        for (GamePlayer gamePlayer : gamePlayers) {
            if (teamManager.hasStandingGamePlayer(gamePlayer.getPlayerUUID())) tmp.add(gamePlayer);
        }
        return tmp;
    }
}
