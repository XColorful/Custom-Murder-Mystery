package xiao.murdermystery.common.game.process.murdermystery;

import com.google.gson.JsonObject;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xiao.battleroyale.BattleRoyale;
import xiao.battleroyale.api.common.McSide;
import xiao.battleroyale.api.config.IConfigSubManager;
import xiao.battleroyale.api.config.IModConfigManager;
import xiao.battleroyale.api.event.ICustomEventPoster;
import xiao.battleroyale.api.event.ILivingDamageEvent;
import xiao.battleroyale.api.event.ILivingDeathEvent;
import xiao.battleroyale.api.game.IGameManager;
import xiao.battleroyale.api.game.zone.IZoneManager;
import xiao.battleroyale.api.game.zone.gamezone.ITickableZone;
import xiao.battleroyale.common.game.process.battleroyale.BRGameProcessManager;
import xiao.battleroyale.common.game.team.GamePlayer;
import xiao.battleroyale.common.game.team.GameTeam;
import xiao.battleroyale.config.common.game.GameConfigManager;
import xiao.battleroyale.config.common.game.gamerule.GameruleConfigManager;
import xiao.battleroyale.config.common.game.gamerule.type.ExtraRuleEntry;
import xiao.battleroyale.util.ChatUtils;
import xiao.battleroyale.util.StringUtils;
import xiao.murdermystery.MurderMystery;
import xiao.murdermystery.api.config.common.game.gamerule.custom.MurderMysteryConfigTag;
import xiao.murdermystery.api.event.custom.murdermystery.SetRoleEvent;
import xiao.murdermystery.api.game.process.murdermystery.IMMItemTagApi;
import xiao.murdermystery.api.game.process.murdermystery.IMurderMysteryProcessManager;
import xiao.murdermystery.api.game.process.murdermystery.MurderMysteryRole;
import xiao.murdermystery.config.common.game.gamerule.custom.MurdermysteryEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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

    protected MurdermysteryEntry configEntry;

    protected final MMData murderMysteryData = new MMData();
    protected final IMMItemTagApi itemTagApi = MMItemTagHelper.get();

    protected boolean isSetRoleFinished = false;
    protected boolean isSetSurvivorFinished = false;
    protected boolean isSetDetectiveFinished = false;
    protected boolean isSetMurderFinished = false;

    public static final String _MANAGER_NAME = String.format("%s:MMGameProcessManager", MurderMystery.MOD_ID);
    @Override public String getManagerName() {
        return _MANAGER_NAME;
    }

    @Override
    public boolean registerGameEventHandler() {
        _MMGameEventRegister.register();
        return super.registerGameEventHandler();
    }

    @Override
    public boolean unregisterGameEventHandler() {
        _MMGameEventRegister.unregister();
        return super.unregisterGameEventHandler();
    }

    @Override
    public String getEventHandlerName() {
        return String.format("%s:MMGameProcessManager", MurderMystery.MOD_ID);
    }

    @Override
    public void initGameConfig(ServerLevel serverLevel) {
        super.initGameConfig(serverLevel);
        if (!isConfigPrepared()) {
            return;
        }

        IGameManager gameManager = BattleRoyale.getGameManager();
        IModConfigManager modConfigManager = BattleRoyale.getModConfigManager();
        IConfigSubManager<?> gameruleConfigManager = modConfigManager.getConfigSubManager(GameConfigManager.get().getNameKey(), GameruleConfigManager.get().getNameKey());
        int configId = gameManager.getGameruleConfigId();
        if (gameruleConfigManager == null || !(gameruleConfigManager.getConfigEntry(configId) instanceof GameruleConfigManager.GameruleConfig gameruleConfig)) {
            ChatUtils.sendTranslatableMessageToAllPlayers(serverLevel, "battleroyale.message.missing_gamerule_config");
            configPrepared = false;
            return;
        }

        ExtraRuleEntry extraRuleEntry = gameruleConfig.getExtraRuleEntry();
        JsonObject jsonTag = extraRuleEntry.jsonTag;
        StringUtils.ProtocolString protocol = extraRuleEntry.protocol;
        boolean isMurderMysteryConfig = (protocol.namespace.equals(MurderMystery.MOD_ID) || protocol.namespace.equals(MurderMystery.MOD_NAME_SHORT))
                && (protocol.name.equals(MurderMysteryConfigTag.PROTOCOL_NAME));
        this.configEntry = isMurderMysteryConfig ? MurdermysteryEntry.fromJson(jsonTag) : new MurdermysteryEntry();
        if (this.configEntry == null) {
            ChatUtils.sendTranslatableMessageToAllPlayers(serverLevel, "battleroyale.message.missing_gamerule_config");
            configPrepared = false;
            return;
        }

        // 游戏时间限制 [gameStartTick, surviveTimeGoal)
        if (this.configEntry.gameStartTick >= this.configEntry.surviveTimeGoal) {
            this.configEntry.surviveTimeGoal = this.configEntry.gameStartTick + 1;
        }
        itemTagApi.setSurvivorTag(this.configEntry.survivorItemTag);
        itemTagApi.setMurderTag(this.configEntry.murderItemTag);

        MurderMystery.LOGGER.debug("MMGameProcessManager complete initGameConfig");
    }

    @Override
    public void initGame(ServerLevel serverLevel) {
        super.initGame(serverLevel);

        this.murderMysteryData.clear();
        this.isSetRoleFinished = false;
        this.isSetSurvivorFinished = false;
        this.isSetDetectiveFinished = false;
        this.isSetMurderFinished = false;

        MurderMystery.LOGGER.debug("MMGameProcessManager complete initGame");
    }


    @Override
    public boolean startGame(ServerLevel serverLevel) {
        if (!super.startGame(serverLevel)) {
            return false;
        }

        this.murderMysteryData.startGame();
        return true;
    }

    @Override
    public void stopGame(@Nullable ServerLevel serverLevel) {
        super.stopGame(serverLevel);

        this.murderMysteryData.endGame();
    }

    @Override
    public void onGameTick(int gameTime) {
        IGameManager gameManager = BattleRoyale.getGameManager();
        UUID gameId = gameManager.getGameId();
        super.onGameTick(gameTime);

        gameManager = BattleRoyale.getGameManager();
        if (gameManager.isInGame() && gameId.equals(gameManager.getGameId())) { // 防止 onGameTick 后结束游戏，又立即重开了游戏 (其他模组修改)
            // 自动设置阵营 tick
            if (!isSetRoleFinished) {
                this.onSetRoleTick(gameTime);
            }

            if (this.notReachGameStartTick(gameTime)) return; // 游戏开始的延迟，此时不保证已经确定杀手
            else if (this.reachSurviveTimeGoal(gameTime)) { // 达到最大生存时间 (surviveTimeGoal)
                gameManager.finishGame(true);
                return;
            }

            // 游戏时间限制 [gameStartTick, surviveTimeGoal)
        }
    }

    @Override
    public void onSetRoleTick(int gameTime) {
        IGameManager gameManager = BattleRoyale.getGameManager();
        // 先生存者后侦探
        if (!isSetSurvivorFinished && gameTime >= this.configEntry.survivorDelay) {
            _MMGameManagement.setSurvivorRoles(this, gameManager);
            isSetSurvivorFinished = true;
        }
        if (!isSetDetectiveFinished && gameTime >= this.configEntry.detectiveDelay) {
            _MMGameManagement.setDetectiveRoles(this, gameManager);
            isSetDetectiveFinished = true;
        }
        // 最后杀手
        if (!isSetMurderFinished && gameTime >= this.configEntry.murderDelay) {
            _MMGameManagement.setMurderRoles(this, gameManager);
            isSetMurderFinished = true;
        }
        this.isSetRoleFinished = isSetSurvivorFinished && isSetDetectiveFinished && isSetMurderFinished;
    }

    /**
     * 谁是杀手判定：仅剩生存者或杀手阵营存活
     */
    @Override
    public void finishGameIfShouldEnd(IGameManager gameManager) {
        if (!gameManager.isInGame()) {
            return;
        }

        // 游戏开始的延迟，此时不保证已经确定杀手
        int gameTime = gameManager.getGameTime();
        if (this.notReachGameStartTick(gameTime)) {
            return;
        }
        // 达到最大生存时间
        else if (this.reachSurviveTimeGoal(gameTime)) {
            gameManager.finishGame(true);
            return;
        }

        int standingSurvivorCount = this.getStandingSurvivorCount();
        int standingMurderCount = this.getStandingMurderCount();
        // 没有生存者或杀手存活
        if (standingSurvivorCount == 0 && standingMurderCount == 0) {
            gameManager.finishGame(false);
            return;
        }
        // 生存者胜利或杀手胜利
        else if (standingSurvivorCount > 0 && standingMurderCount == 0
                || standingSurvivorCount == 0 && standingMurderCount > 0) {
            gameManager.finishGame(true);
            return;
        }

        if (!gameManager.getGameEntry().allowRemainingBot) { // 不允许只剩人机继续打架，即无真人玩家时提前终止游戏
            if (gameManager.getTeamManager().onlyRemainBotTeam()) {
                gameManager.finishGame(false);
                MurderMystery.LOGGER.debug("BRGameProcessManager: Finished game with no winner for there's no two team has non-eliminated non-bot game player");
            }
        }
    }

    private boolean notReachGameStartTick(int gameTime) {
        return gameTime < this.configEntry.gameStartTick;
    }
    private boolean reachSurviveTimeGoal(int gameTime) {
        return this.configEntry.surviveTimeGoal <= gameTime;
    }

    // --------IMurderMysteryApiGetter--------

    @Override public IMMItemTagApi getItemTagApi() {
        return itemTagApi;
    }

    // --------IGameManagement--------

    @Override public void finishGameAddWinner(boolean hasWinner) {
        _MMGameManagement.finishGameAddWinner(this, BattleRoyale.getGameManager(), hasWinner);
    }

    // --------IMurderMysteryGameManagement--------

    @Override public boolean setSurvivor(@NotNull GamePlayer gamePlayer) {
        ICustomEventPoster eventPoster = BattleRoyale.getEventPoster();
        if (eventPoster.postCustomEvent(new SetRoleEvent.SurvivorRoleEvent(this, gamePlayer))) {
            MurderMystery.LOGGER.debug("SurvivorRoleEvent canceled, skipped GamePlayer {}", gamePlayer.getNameWithId());
            return false;
        }
        if (this.murderMysteryData.setSurvivor(gamePlayer)) {
            IGameManager gameManager = BattleRoyale.getGameManager();
            List<Integer> tickedFunc = new ArrayList<>();
            tickZoneFunc(gameManager.getZoneManager(), gameManager.getServerLevel(), gamePlayer, this.configEntry.survivorFuncs, tickedFunc);
            MurderMystery.LOGGER.debug("Re-ticked survivorFuncs {} for GamePlayer {}", tickedFunc, gamePlayer.getNameWithId());
            eventPoster.postCustomEvent(new SetRoleEvent.SurvivorRoleFinishEvent(this, gamePlayer));
            return true;
        } else {
            return false;
        }
    }
    @Override public boolean setDetective(@NotNull GamePlayer gamePlayer) {
        ICustomEventPoster eventPoster = BattleRoyale.getEventPoster();
        if (eventPoster.postCustomEvent(new SetRoleEvent.DetectiveRoleEvent(this, gamePlayer))) {
            MurderMystery.LOGGER.debug("DetectiveRoleEvent canceled, skipped GamePlayer {}", gamePlayer.getNameWithId());
            return false;
        }
        if (this.murderMysteryData.setDetective(gamePlayer)) {
            IGameManager gameManager = BattleRoyale.getGameManager();
            List<Integer> tickedFunc = new ArrayList<>();
            tickZoneFunc(gameManager.getZoneManager(), gameManager.getServerLevel(), gamePlayer, this.configEntry.detectiveFuncs, tickedFunc);
            MurderMystery.LOGGER.debug("Re-ticked detectiveFuncs {} for GamePlayer {}", tickedFunc, gamePlayer.getNameWithId());
            eventPoster.postCustomEvent(new SetRoleEvent.DetectiveRoleFinishEvent(this, gamePlayer));
            return true;
        } else {
            return false;
        }
    }
    @Override public boolean setMurder(@NotNull GamePlayer gamePlayer) {
        ICustomEventPoster eventPoster = BattleRoyale.getEventPoster();
        if (eventPoster.postCustomEvent(new SetRoleEvent.MurderRoleEvent(this, gamePlayer))) {
            MurderMystery.LOGGER.debug("MurderRoleEvent canceled, skipped GamePlayer {}", gamePlayer.getNameWithId());
            return false;
        }
        if (this.murderMysteryData.setMurder(gamePlayer)) {
            IGameManager gameManager = BattleRoyale.getGameManager();
            List<Integer> tickedFunc = new ArrayList<>();
            tickZoneFunc(gameManager.getZoneManager(), gameManager.getServerLevel(), gamePlayer, this.configEntry.murderFuncs, tickedFunc);
            MurderMystery.LOGGER.debug("Re-ticked murderFuncs {} for GamePlayer {}", tickedFunc, gamePlayer.getNameWithId());
            eventPoster.postCustomEvent(new SetRoleEvent.MurderRoleFinishEvent(this, gamePlayer));
            return true;
        } else {
            return false;
        }
    }

    // --------IGameNotification--------

    @Override public void sendWinnerResult(@Nullable ServerLevel serverLevel, Set<GamePlayer> winnerGamePlayers, Set<GameTeam> winnerGameTeams, int gameTime) {
        _MMGameNotification.sendWinnerResult(this, serverLevel, winnerGamePlayers, winnerGameTeams, gameTime);
    }

    @Override public void notifyWinner(@Nullable ServerLevel serverLevel, @NotNull GamePlayer gamePlayer, int winnerParticleId) {
        super.notifyWinner(serverLevel, gamePlayer, winnerParticleId);
    }

    @Override public void sendDownMessage(@Nullable ServerLevel serverLevel, @NotNull GamePlayer gamePlayer) {
        if (this.configEntry.sendGamePlayerNotificationMessage) super.sendDownMessage(serverLevel, gamePlayer);
    }

    @Override public void sendReviveMessage(@Nullable ServerLevel serverLevel, @NotNull GamePlayer gamePlayer) {
        if (this.configEntry.sendGamePlayerNotificationMessage) super.sendReviveMessage(serverLevel, gamePlayer);
    }

    @Override public void sendEliminateMessage(@Nullable ServerLevel serverLevel, @NotNull GamePlayer gamePlayer) {
        if (this.configEntry.sendGamePlayerNotificationMessage) super.sendEliminateMessage(serverLevel, gamePlayer);
    }

    // --------IGameEventHandler--------

    @Override public boolean onPlayerDamage(ILivingDamageEvent event, @NotNull GamePlayer gamePlayer) {
        return _MMGameEventHandler.onPlayerDamage(this, event, gamePlayer);
    }

    @Override public boolean onPlayerDown(ILivingDeathEvent event, @NotNull GamePlayer gamePlayer, boolean removeInvalidTeam) {
        return _MMGameEventHandler.onPlayerDown(this, event, gamePlayer, removeInvalidTeam);
    }

    @Override public boolean onPlayerDeath(@Nullable ILivingDeathEvent event, @Nullable ServerLevel serverLevel, @NotNull GamePlayer gamePlayer) {
        _MMDelayedEventHelper.postDelayedEvent(this, event, gamePlayer);
        return _MMGameEventHandler.onPlayerDeath(this, event, serverLevel, gamePlayer);
    }

    // --------IMurderMysteryDataManagement--------

    // --------IMurderMysteryInfoGetter--------

    @Override public @NotNull MurderMysteryRole getRole(@NotNull GamePlayer gamePlayer) {
        return this.murderMysteryData.getRole(gamePlayer);
    }
    @Override public boolean isSurvivor(@NotNull GamePlayer gamePlayer) {
        return this.murderMysteryData.isSurvivor(gamePlayer);
    }
    @Override public boolean isDetective(@NotNull GamePlayer gamePlayer) {
        return this.murderMysteryData.isDetective(gamePlayer);
    }
    @Override public boolean isMurder(@NotNull GamePlayer gamePlayer) {
        return this.murderMysteryData.isMurder(gamePlayer);
    }
    @Override public List<GamePlayer> getMurders() {
        return murderMysteryData.getMurders();
    }
    @Override public List<GamePlayer> getDetectives() {
        return murderMysteryData.getDetectives();
    }
    @Override public List<GamePlayer> getSurvivors() {
        return murderMysteryData.getSurvivors();
    }
    @Override public int getSurvivorSize() {
        return murderMysteryData.getSurvivorSize();
    }
    @Override public int getDetectiveSize() {
        return murderMysteryData.getDetectiveSize();
    }
    @Override public int getMurderSize() {
        return murderMysteryData.getMurderSize();
    }
    @Override public List<GamePlayer> getStandingSurvivors() {
        return murderMysteryData.getStandingSurvivors();
    }
    @Override public List<GamePlayer> getStandingDetectives() {
        return murderMysteryData.getStandingDetectives();
    }
    @Override public List<GamePlayer> getStandingMurders() {
        return murderMysteryData.getStandingMurders();
    }
    @Override public int getStandingSurvivorCount() {
        return murderMysteryData.getStandingSurvivorCount();
    }
    @Override public int getStandingDetectiveCount() {
        return murderMysteryData.getStandingDetectiveCount();
    }
    @Override public int getStandingMurderCount() {
        return murderMysteryData.getStandingMurderCount();
    }

    private void tickZoneFunc(IZoneManager zoneManager, ServerLevel serverLevel, GamePlayer gamePlayer, List<Integer> zoneFunc, List<Integer> tickedFunc) {
        if (serverLevel == null) return;
        for (Integer zoneId : zoneFunc) {
            @Nullable ITickableZone tickableZone = zoneManager.getGameZone(zoneId); // 只需要 func 而不需要 shape
            if (tickableZone != null && tickableZone.isReady()) {
                tickableZone.playerFunc(serverLevel, gamePlayer);
                tickedFunc.add(zoneId);
            }
        }
    }
}
