package xiao.murdermystery.common.game.process.murdermystery;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xiao.battleroyale.BattleRoyale;
import xiao.battleroyale.api.event.ILivingDamageEvent;
import xiao.battleroyale.api.event.ILivingDeathEvent;
import xiao.battleroyale.api.game.IGameManager;
import xiao.battleroyale.api.game.team.ITeamManager;
import xiao.battleroyale.common.game.GameMessageManager;
import xiao.battleroyale.common.game.team.GamePlayer;
import xiao.battleroyale.compat.playerrevive.PlayerRevive;
import xiao.battleroyale.util.GameUtils;
import xiao.murdermystery.MurderMystery;

import java.util.List;

public class _MMGameEventHandler {

    protected static boolean onPlayerDamage(MMGameProcessManager mmGameProcessManager, ILivingDamageEvent event, @NotNull GamePlayer gamePlayer) {
        return true;
    }

    protected static boolean onPlayerDown(MMGameProcessManager mmGameProcessManager, ILivingDeathEvent event, @NotNull GamePlayer gamePlayer, boolean removeInvalidTeam) {
        IGameManager gameManager = BattleRoyale.getGameManager();

        List<GamePlayer> teamMembers = mmGameProcessManager.getStandingTeamMembers(gamePlayer) // 自动 TeamManager 级别过滤
                .stream().filter(GamePlayer::isAlive).toList(); // 相当于手动 GameTeam::getAlivePlayers
        boolean hasAliveMember = false;
        for (GamePlayer member : teamMembers) {
            if (member.getGameSingleId() == gamePlayer.getGameSingleId()) {
                continue;
            }
            if (removeInvalidTeam && !member.isActiveEntity()) { // 队友离线算作倒地 && 队友离线
                continue;
            }
            hasAliveMember = true;
            break;
        }
        // 没有存活队友就判定为无法救援，直接判死亡
        if (!hasAliveMember) {
            MurderMystery.LOGGER.debug("GamePlayer {} is down and has no alive member, switch to onPlayerDeath", gamePlayer.getPlayerName());
            gameManager.onPlayerDeath(event, gamePlayer); // onPlayerDeath 里会在本次 onPlayerDownFinish 前设置好 eliminated
            return false;
        }

        LivingEntity player = event.getEntity();
        PlayerRevive playerRevive = PlayerRevive.get();

        // PlayerRevive倒地机制：取消事件并设置为流血状态
        if (playerRevive.isBleeding(player)) {
            gamePlayer.setAlive(false);
            playerRevive.addBleedingPlayer(player);
            mmGameProcessManager.sendDownMessage(gameManager.getServerLevel(), gamePlayer);
            return true;
        }

        if (!gamePlayer.isAlive()) { // 倒地，但是不为存活状态
            MurderMystery.LOGGER.debug("GamePlayer {} is down but not alive, switch to onPlayerDeath", gamePlayer.getPlayerName());
            gameManager.onPlayerDeath(event, gamePlayer);
            return false;
        }

        // 没检测到 PlayerRevive 就认为是其他手段自救
        gamePlayer.setAlive(true); // 其实应该不需要设置
        MurderMystery.LOGGER.debug("Not detected GamePlayer {} PlayerRevive, may be revived by any method", gamePlayer.getNameWithId());
        return true;
    }

    protected static boolean onPlayerDeath(MMGameProcessManager mmGameProcessManager, @Nullable ILivingDeathEvent event, @Nullable ServerLevel serverLevel, @NotNull GamePlayer gamePlayer) {
        IGameManager gameManager = BattleRoyale.getGameManager();
        ITeamManager teamManager = gameManager.getTeamManager();
        boolean teamEliminatedBefore = mmGameProcessManager.isTeamEliminated(gamePlayer);
        boolean playerEliminatedBefore = !teamManager.hasStandingGamePlayer(gamePlayer.getPlayerUUID()); // 不看 GamePlayer eliminated 标志位
        if (teamEliminatedBefore && playerEliminatedBefore) {
            MurderMystery.LOGGER.debug("GamePlayer {} and corresponding team {} already eliminated, skipped onPlayerDeath", gamePlayer.getPlayerName(), gamePlayer.getTeam().getGameTeamId());
            return true; // 只是避免重复 eliminate，但仍然要 GamePlayerDeathFinishEvent 事件
        }

        PlayerRevive playerRevive = PlayerRevive.get();

        // 死亡事件本身已经跳过非 standingPlayer
        // 单独淘汰，连带淘汰放在后面进行
        if (!playerEliminatedBefore) { // 第一次淘汰才尝试kill，避免重复kill
            gamePlayer.setEliminated(true); // GamePlayer 内部会自动让 GameTeam 更新 eliminated，但是不需要
            teamManager.forceEliminatePlayerSilence(gamePlayer); // 提醒 TeamManager 内部更新 standingPlayer 信息
            // ↑ 这里拦截掉了，下面 PlayerRevive 的 kill 触发的 onPlayerDeath 会被 TeamManager 级别的 eliminated 过滤掉
            mmGameProcessManager.sendEliminateMessage(serverLevel, gamePlayer);

            // 最后再 kill，此时再触发 onPlayerDeath 已提前被 eliminated 拦截
            @Nullable LivingEntity player = serverLevel != null ? GameUtils.getLivingEntity(serverLevel, gamePlayer.getPlayerUUID()) : null;
            if (player != null && playerRevive.isBleeding(player)) {
                MurderMystery.LOGGER.debug("Detected GamePlayer {} PlayerRevive.isBleeding, force kill", gamePlayer.getPlayerName());
                playerRevive.kill(player);
            }

            GameMessageManager.notifyTeamChange(gamePlayer.getGameTeamId());
            GameMessageManager.notifyAliveChange();
        }

        // 连带淘汰在同一个 onPlayerDeath 里处理，连带触发的都在开头标志位提前拦截
        if (!teamEliminatedBefore && mmGameProcessManager.isTeamEliminated(gamePlayer)) { // 再判定一次，刚才的eliminate可能会更新状态
            MurderMystery.LOGGER.info("GamePlayer {} corresponding team has been eliminated, updating member to eliminated", gamePlayer.getPlayerName());
            // 已经包含 TeamManager 级别 standing 判定，所以不过滤
            List<GamePlayer> nonEliminatedMember = mmGameProcessManager.getStandingTeamMembers(gamePlayer);

            // 阵营队伍淘汰则倒地队友全部 kill
            nonEliminatedMember.forEach(member -> member.setEliminated(true)); // 提前设置 eliminate 以跳过下一次 kill 触发的 onPlayerDeath 开头检查
            for (GamePlayer member : nonEliminatedMember) {
                mmGameProcessManager.sendEliminateMessage(serverLevel, member);

                // 有倒地状态就让 PlayerRevive 的 kill，连带触发的 onPlayerDeath 会在开头被拦截
                @Nullable LivingEntity player = serverLevel != null ? GameUtils.getLivingEntity(serverLevel, member.getPlayerUUID()) : null;
                if (player != null && playerRevive.isBleeding(player)) {
                    playerRevive.kill(player);
                } else { // 否则手动通知 onPlayerDeath
                    gameManager.onPlayerDeath(null, member);
                }

                // 对每个玩家发送队伍淘汰消息，虽然不影响判定
                GameMessageManager.notifyTeamChange(gamePlayer.getGameTeamId());
            }
            // TeamManager 级别的 eliminate 放在之后，不然 onPlayerDeath 会被过滤掉
            nonEliminatedMember.forEach(teamManager::forceEliminatePlayerSilence); // 提醒 TeamManager 内部更新 standingPlayer 信息
            GameMessageManager.notifyAliveChange(); // Alive 更新仍然放在最后
        }

        gameManager.addFinishCheckAfterDeathEvent();
        return true;
    }
}
