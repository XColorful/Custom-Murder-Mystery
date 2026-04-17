package xiao.murdermystery.common.game.process.murdermystery;

import com.google.gson.JsonObject;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xiao.battleroyale.BattleRoyale;
import xiao.battleroyale.api.common.McSide;
import xiao.battleroyale.api.config.IConfigSubManager;
import xiao.battleroyale.api.config.IModConfigManager;
import xiao.battleroyale.api.event.ILivingDamageEvent;
import xiao.battleroyale.api.event.ILivingDeathEvent;
import xiao.battleroyale.api.game.IGameManager;
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
import xiao.murdermystery.api.game.process.murdermystery.IMurderMysteryProcessManager;
import xiao.murdermystery.config.common.game.gamerule.custom.MurdermysteryEntry;

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

    protected MurdermysteryEntry configEntry;

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
        boolean isMurderMysteryConfig = (protocol.namespace.equals(BattleRoyale.MOD_ID) || protocol.namespace.equals(BattleRoyale.MOD_NAME_SHORT))
                && (protocol.name.equals(MurderMysteryConfigTag.PROTOCOL_NAME));
        this.configEntry = isMurderMysteryConfig ? MurdermysteryEntry.fromJson(jsonTag) : new MurdermysteryEntry();
        if (this.configEntry == null) {
            ChatUtils.sendTranslatableMessageToAllPlayers(serverLevel, "battleroyale.message.missing_gamerule_config");
            configPrepared = false;
            return;
        }

        MurderMystery.LOGGER.debug("MMGameProcessManager complete initGameConfig");
    }

    @Override
    public void initGame(ServerLevel serverLevel) {
        super.initGame(serverLevel);

        this.murderMysteryData.clear();

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
