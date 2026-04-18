package xiao.murdermystery.common.game.process.murdermystery;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xiao.battleroyale.BattleRoyale;
import xiao.battleroyale.api.event.DelayedEvent;
import xiao.battleroyale.api.event.ILivingDeathEvent;
import xiao.battleroyale.api.game.IGameManager;
import xiao.battleroyale.api.game.team.ITeamManager;
import xiao.battleroyale.common.game.team.GamePlayer;
import xiao.murdermystery.api.event.custom.murdermystery.DelayedRoleKillEvent;
import xiao.murdermystery.api.game.process.murdermystery.IMurderMysteryProcessManager;
import xiao.murdermystery.api.game.process.murdermystery.MurderMysteryRole;

import java.util.function.Consumer;

public class _MMDelayedEventHelper {

    public static final int DELAYED_TICK = 1;
    /**
     * 延迟 1 tick 触发
     */
    public static void postDelayedEvent(@NotNull IMurderMysteryProcessManager manager, @Nullable ILivingDeathEvent event, @NotNull GamePlayer gamePlayer) {
        if (event == null) return; // 没有攻击者

        IGameManager gameManager = BattleRoyale.getGameManager();
        ITeamManager teamManager = gameManager.getTeamManager();
        DamageSource damageSource = event.getSource();
        @Nullable Entity attackerEntity = damageSource.getEntity();
        @Nullable GamePlayer attackerGamePlayer = attackerEntity != null ? teamManager.getGamePlayerByUUID(attackerEntity.getUUID()) : null;
        if (attackerGamePlayer == null) return; // 攻击者不是游戏玩家

        MurderMysteryRole attackerRole = manager.getRole(attackerGamePlayer);
        MurderMysteryRole victimRole = manager.getRole(gamePlayer);

        switch (attackerRole) {
            case SURVIVOR -> {
                // 生存者 -> 杀手阵营
                if (victimRole.isMurder()) {
                    Consumer<DelayedRoleKillEvent.SurvivorKillEvent> delayedTask = delayedEvent -> {
                        delayedEvent.setRoleInfo();
                        if (delayedEvent.isValid()) {
                            BattleRoyale.getEventPoster().postCustomEvent(delayedEvent);
                        }
                    };
                    new DelayedEvent<>(delayedTask, new DelayedRoleKillEvent.SurvivorKillEvent(gameManager.getGameId(), manager, gamePlayer.getPlayerUUID(), attackerGamePlayer.getPlayerUUID()),
                            DELAYED_TICK, "_MMDelayedEventHelper: DelayedRoleKillEvent.SurvivorKillEvent");
                }
                // 生存者 -> 生存者阵营
                else if (victimRole.isSurvivorOrDetective()) {
                    Consumer<DelayedRoleKillEvent.SurvivorWrongKillEvent> delayedTask = delayedEvent -> {
                        delayedEvent.setRoleInfo();
                        if (delayedEvent.isValid()) {
                            BattleRoyale.getEventPoster().postCustomEvent(delayedEvent);
                        }
                    };
                    new DelayedEvent<>(delayedTask, new DelayedRoleKillEvent.SurvivorWrongKillEvent(gameManager.getGameId(), manager, gamePlayer.getPlayerUUID(), attackerGamePlayer.getPlayerUUID()),
                            DELAYED_TICK, "_MMDelayedEventHelper: DelayedRoleKillEvent.SurvivorWrongKillEvent");
                } else {
                }
            }
            case DETECTIVE -> {
                // 侦探 -> 杀手阵营
                if (victimRole.isMurder()) {
                    Consumer<DelayedRoleKillEvent.DetectiveKillEvent> delayedTask = delayedEvent -> {
                        delayedEvent.setRoleInfo();
                        if (delayedEvent.isValid()) {
                            BattleRoyale.getEventPoster().postCustomEvent(delayedEvent);
                        }
                    };
                    new DelayedEvent<>(delayedTask, new DelayedRoleKillEvent.DetectiveKillEvent(gameManager.getGameId(), manager, gamePlayer.getPlayerUUID(), attackerGamePlayer.getPlayerUUID()),
                            DELAYED_TICK, "_MMDelayedEventHelper: DelayedRoleKillEvent.DetectiveKillEvent");
                }
                // 侦探 -> 生存者阵营
                else if (victimRole.isSurvivorOrDetective()) {
                    Consumer<DelayedRoleKillEvent.DetectiveWrongKillEvent> delayedTask = delayedEvent -> {
                        delayedEvent.setRoleInfo();
                        if (delayedEvent.isValid()) {
                            BattleRoyale.getEventPoster().postCustomEvent(delayedEvent);
                        }
                    };
                    new DelayedEvent<>(delayedTask, new DelayedRoleKillEvent.DetectiveWrongKillEvent(gameManager.getGameId(), manager, gamePlayer.getPlayerUUID(), attackerGamePlayer.getPlayerUUID()),
                            DELAYED_TICK, "_MMDelayedEventHelper: DelayedRoleKillEvent.DetectiveWrongKillEvent");
                } else {
                }
            }
            case MURDER -> {
                // 杀手 -> 生存者阵营
                if (victimRole.isSurvivorOrDetective()) {
                    Consumer<DelayedRoleKillEvent.MurderKillEvent> delayedTask = delayedEvent -> {
                        delayedEvent.setRoleInfo();
                        if (delayedEvent.isValid()) {
                            BattleRoyale.getEventPoster().postCustomEvent(delayedEvent);
                        }
                    };
                    new DelayedEvent<>(delayedTask, new DelayedRoleKillEvent.MurderKillEvent(gameManager.getGameId(), manager, gamePlayer.getPlayerUUID(), attackerGamePlayer.getPlayerUUID()),
                            DELAYED_TICK, "_MMDelayedEventHelper: DelayedRoleKillEvent.MurderKillEvent");
                }
                // 杀手 -> 杀手阵营
                else if (victimRole.isMurder()) {
                    Consumer<DelayedRoleKillEvent.MurderWrongKillEvent> delayedTask = delayedEvent -> {
                        delayedEvent.setRoleInfo();
                        if (delayedEvent.isValid()) {
                            BattleRoyale.getEventPoster().postCustomEvent(delayedEvent);
                        }
                    };
                    new DelayedEvent<>(delayedTask, new DelayedRoleKillEvent.MurderWrongKillEvent(gameManager.getGameId(), manager, gamePlayer.getPlayerUUID(), attackerGamePlayer.getPlayerUUID()),
                            DELAYED_TICK, "_MMDelayedEventHelper: DelayedRoleKillEvent.MurderWrongKillEvent");
                } else {
                }
            }
        }
    }
}
