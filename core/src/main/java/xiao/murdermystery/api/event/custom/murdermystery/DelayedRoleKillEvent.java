package xiao.murdermystery.api.event.custom.murdermystery;

import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xiao.battleroyale.BattleRoyale;
import xiao.battleroyale.api.event.CustomEvent;
import xiao.battleroyale.api.event.CustomEventType;
import xiao.battleroyale.api.event.ICustomEvent;
import xiao.battleroyale.api.event.ICustomEventHandler;
import xiao.battleroyale.api.game.IGameManager;
import xiao.battleroyale.api.game.process.IGameProcessManager;
import xiao.battleroyale.api.game.team.ITeamManager;
import xiao.battleroyale.api.minecraft.CommandLevel;
import xiao.battleroyale.common.game.team.GamePlayer;
import xiao.battleroyale.event.EventDispatcher;
import xiao.battleroyale.util.GameUtils;
import xiao.murdermystery.api.game.process.murdermystery.IMurderMysteryProcessManager;

import java.util.UUID;

public abstract class DelayedRoleKillEvent extends CustomEvent {

    protected final @NotNull UUID gameId;
    protected final @NotNull IMurderMysteryProcessManager manager;
    protected final @NotNull UUID victimUUID;
    protected final @Nullable UUID attackerUUID;
    protected @Nullable GamePlayer attackerGamePlayer;
    protected @Nullable LivingEntity attacker;
    protected @Nullable GamePlayer victimGamePlayer;
    protected @Nullable LivingEntity victim;
    protected boolean validated = false;
    protected boolean isValid = false;

    public DelayedRoleKillEvent(@NotNull UUID gameId, @NotNull IMurderMysteryProcessManager manager, @NotNull UUID victimUUID, @Nullable UUID attackerUUID) {
        this.gameId = gameId;
        this.manager = manager;
        this.victimUUID = victimUUID;
        this.attackerUUID = attackerUUID;
    }
    public @NotNull IGameProcessManager getGameProcessManager() {
        return getManager();
    }
    public @NotNull IMurderMysteryProcessManager getManager() {
        return manager;
    }
    public boolean isValidated() {
        return validated;
    }
    public boolean isValid() {
        return isValid;
    }
    public void setRoleInfo() {
        validated = true;
        IGameManager gameManager = BattleRoyale.getGameManager();
        // 不为同一局游戏/游戏已结束
        if (!gameManager.isInGame() && gameId.equals(gameManager.getGameId())) return;

        ITeamManager teamManager = gameManager.getTeamManager();
        @Nullable GamePlayer _attackerGamePlayer = teamManager.getGamePlayerByUUID(attackerUUID);
        @Nullable GamePlayer _victimGamePlayer = teamManager.getGamePlayerByUUID(victimUUID);
        if (_attackerGamePlayer == null || _victimGamePlayer == null) return;

        // 阵营关系已经不对应事件本身 (比如数据包写了转换阵营)
        if (!isRoleStillCorrect(_attackerGamePlayer, _victimGamePlayer)) return;

        this.attackerGamePlayer = _attackerGamePlayer;
        this.victimGamePlayer = _victimGamePlayer;
        @Nullable ServerLevel serverLevel = gameManager.getServerLevel();
        if (serverLevel != null) {
            this.attacker = GameUtils.getLivingEntity(serverLevel, attackerUUID);
            this.victim = GameUtils.getLivingEntity(serverLevel, victimUUID);
        }
        isValid = true;
    }
    protected abstract boolean isRoleStillCorrect(@NotNull GamePlayer _attackerGamePlayer, @NotNull GamePlayer _victimGamePlayer);

    @Override
    public @Nullable CommandSourceStack createCommandSourceStack(@Nullable CommandSource source) {
        if (!isValid) return null;
        return new CommandSourceStack(
                source != null ? source : CommandSource.NULL,
                attackerGamePlayer.getLastPos(),
                attacker != null ? attacker.getRotationVector() : Vec2.ZERO,
                BattleRoyale.getGameManager().getServerLevel(),
                CommandLevel.permission(4),
                this.getTextName(),
                this.getDisplayName(),
                BattleRoyale.getMinecraftServer(),
                attacker
        );
    }
    protected abstract String _getTextNameIfNotValid();
    @Override public String getTextName() {
        return !isValid ? _getTextNameIfNotValid()
                : this.attacker != null ? attacker.getName().getString() : attackerGamePlayer.getPlayerName();
    }
    @Override public Component getDisplayName() {
        return isValid && this.attacker != null ? attacker.getDisplayName()
                : Component.literal(getTextName());
    }

    // --------具体事件类--------

    // 生存者击杀
    public static class SurvivorKillEvent extends DelayedRoleKillEvent {
        public SurvivorKillEvent(@NotNull UUID gameId, @NotNull IMurderMysteryProcessManager manager, @NotNull UUID victimUUID, @Nullable UUID attackerUUID) {
            super(gameId, manager, victimUUID, attackerUUID);
        }
        @Override protected boolean isRoleStillCorrect(@NotNull GamePlayer _attackerGamePlayer, @NotNull GamePlayer _victimGamePlayer) {
            return manager.getRole(_attackerGamePlayer).isSurvivor() && manager.getRole(_victimGamePlayer).isMurder();
        }
        @Override protected String _getTextNameIfNotValid() {
            return "MurderMystery SurvivorKillEvent";
        }
        private static final EventDispatcher<ICustomEventHandler, ICustomEvent, CustomEventType> _EVENT_DISPATCHER = BattleRoyale.getEventPoster().getEventDispatcher(SurvivorKillEvent.class);
        @Override public @NotNull EventDispatcher<ICustomEventHandler, ICustomEvent, CustomEventType> getEventDispatcher() {
            return _EVENT_DISPATCHER;
        }
    }
    public static class SurvivorWrongKillEvent extends DelayedRoleKillEvent {
        public SurvivorWrongKillEvent(@NotNull UUID gameId, @NotNull IMurderMysteryProcessManager manager, @NotNull UUID victimUUID, @Nullable UUID attackerUUID) {
            super(gameId, manager, victimUUID, attackerUUID);
        }
        @Override protected boolean isRoleStillCorrect(@NotNull GamePlayer _attackerGamePlayer, @NotNull GamePlayer _victimGamePlayer) {
            return manager.getRole(_attackerGamePlayer).isSurvivor() && manager.getRole(_victimGamePlayer).isSurvivorOrDetective();
        }
        @Override protected String _getTextNameIfNotValid() {
            return "MurderMystery SurvivorWrongKillEvent";
        }
        private static final EventDispatcher<ICustomEventHandler, ICustomEvent, CustomEventType> _EVENT_DISPATCHER = BattleRoyale.getEventPoster().getEventDispatcher(SurvivorWrongKillEvent.class);
        @Override public @NotNull EventDispatcher<ICustomEventHandler, ICustomEvent, CustomEventType> getEventDispatcher() {
            return _EVENT_DISPATCHER;
        }
    }
    // 侦探击杀
    public static class DetectiveKillEvent extends DelayedRoleKillEvent {
        public DetectiveKillEvent(@NotNull UUID gameId, @NotNull IMurderMysteryProcessManager manager, @NotNull UUID victimUUID, @Nullable UUID attackerUUID) {
            super(gameId, manager, victimUUID, attackerUUID);
        }
        @Override protected boolean isRoleStillCorrect(@NotNull GamePlayer _attackerGamePlayer, @NotNull GamePlayer _victimGamePlayer) {
            return manager.getRole(_attackerGamePlayer).isDetective() && manager.getRole(_victimGamePlayer).isMurder();
        }
        @Override protected String _getTextNameIfNotValid() {
            return "MurderMystery DetectiveKillEvent";
        }
        private static final EventDispatcher<ICustomEventHandler, ICustomEvent, CustomEventType> _EVENT_DISPATCHER = BattleRoyale.getEventPoster().getEventDispatcher(DetectiveKillEvent.class);
        @Override public @NotNull EventDispatcher<ICustomEventHandler, ICustomEvent, CustomEventType> getEventDispatcher() {
            return _EVENT_DISPATCHER;
        }
    }
    public static class DetectiveWrongKillEvent extends DelayedRoleKillEvent {
        public DetectiveWrongKillEvent(@NotNull UUID gameId, @NotNull IMurderMysteryProcessManager manager, @NotNull UUID victimUUID, @Nullable UUID attackerUUID) {
            super(gameId, manager, victimUUID, attackerUUID);
        }
        @Override protected boolean isRoleStillCorrect(@NotNull GamePlayer _attackerGamePlayer, @NotNull GamePlayer _victimGamePlayer) {
            return manager.getRole(_attackerGamePlayer).isDetective() && manager.getRole(_victimGamePlayer).isSurvivorOrDetective();
        }
        @Override protected String _getTextNameIfNotValid() {
            return "MurderMystery DetectiveWrongKillEvent";
        }
        private static final EventDispatcher<ICustomEventHandler, ICustomEvent, CustomEventType> _EVENT_DISPATCHER = BattleRoyale.getEventPoster().getEventDispatcher(DetectiveWrongKillEvent.class);
        @Override public @NotNull EventDispatcher<ICustomEventHandler, ICustomEvent, CustomEventType> getEventDispatcher() {
            return _EVENT_DISPATCHER;
        }
    }
    // 杀手击杀
    public static class MurderKillEvent extends DelayedRoleKillEvent {
        public MurderKillEvent(@NotNull UUID gameId, @NotNull IMurderMysteryProcessManager manager, @NotNull UUID victimUUID, @Nullable UUID attackerUUID) {
            super(gameId, manager, victimUUID, attackerUUID);
        }
        @Override protected boolean isRoleStillCorrect(@NotNull GamePlayer _attackerGamePlayer, @NotNull GamePlayer _victimGamePlayer) {
            return manager.getRole(_attackerGamePlayer).isMurder() && manager.getRole(_victimGamePlayer).isSurvivorOrDetective();
        }
        @Override protected String _getTextNameIfNotValid() {
            return "MurderMystery MurderKillEvent";
        }
        private static final EventDispatcher<ICustomEventHandler, ICustomEvent, CustomEventType> _EVENT_DISPATCHER = BattleRoyale.getEventPoster().getEventDispatcher(MurderKillEvent.class);
        @Override public @NotNull EventDispatcher<ICustomEventHandler, ICustomEvent, CustomEventType> getEventDispatcher() {
            return _EVENT_DISPATCHER;
        }
    }
    public static class MurderWrongKillEvent extends DelayedRoleKillEvent {
        public MurderWrongKillEvent(@NotNull UUID gameId, @NotNull IMurderMysteryProcessManager manager, @NotNull UUID victimUUID, @Nullable UUID attackerUUID) {
            super(gameId, manager, victimUUID, attackerUUID);
        }
        @Override protected boolean isRoleStillCorrect(@NotNull GamePlayer _attackerGamePlayer, @NotNull GamePlayer _victimGamePlayer) {
            return manager.getRole(_attackerGamePlayer).isMurder() && manager.getRole(_victimGamePlayer).isMurder();
        }
        @Override protected String _getTextNameIfNotValid() {
            return "MurderMystery MurderWrongKillEvent";
        }
        private static final EventDispatcher<ICustomEventHandler, ICustomEvent, CustomEventType> _EVENT_DISPATCHER = BattleRoyale.getEventPoster().getEventDispatcher(MurderWrongKillEvent.class);
        @Override public @NotNull EventDispatcher<ICustomEventHandler, ICustomEvent, CustomEventType> getEventDispatcher() {
            return _EVENT_DISPATCHER;
        }
    }
}
