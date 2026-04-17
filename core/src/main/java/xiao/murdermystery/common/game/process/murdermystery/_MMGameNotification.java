package xiao.murdermystery.common.game.process.murdermystery;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.Nullable;
import xiao.battleroyale.BattleRoyale;
import xiao.battleroyale.common.game.team.GamePlayer;
import xiao.battleroyale.common.game.team.GameTeam;
import xiao.battleroyale.util.ChatUtils;
import xiao.battleroyale.util.ColorUtils;
import xiao.battleroyale.util.CommandUtils;
import xiao.battleroyale.util.GameUtils;
import xiao.murdermystery.MurderMystery;
import xiao.murdermystery.api.game.process.murdermystery.IMurderMysteryProcessManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class _MMGameNotification {

    // 发送胜利阵营消息
    public static void sendWinnerResult(IMurderMysteryProcessManager manager, @Nullable ServerLevel serverLevel, Set<GamePlayer> winnerGamePlayers, Set<GameTeam> winnerGameTeams, int gameTime) {
        // 游戏时长
        MutableComponent winnerComponent = Component.empty()
                .append(Component.translatable("battleroyale.message.game_time", gameTime, new GameUtils.GameTimeFormat(gameTime).toFormattedString(true)));

        List<GamePlayer> survivors = new ArrayList<>();
        List<GamePlayer> detectives = new ArrayList<>();
        List<GamePlayer> murders = new ArrayList<>();
        for (GamePlayer gamePlayer : winnerGamePlayers) {
            if (manager.isMurder(gamePlayer)) {
                murders.add(gamePlayer);
            } else if (manager.isDetective(gamePlayer)) { // 先判定Detective，因为同时属于Survivor阵营
                detectives.add(gamePlayer);
            } else {
                survivors.add(gamePlayer);
            }
        }

        // --------聊天栏发送胜利阵营--------
        // 生存者 Survivor
        if (!survivors.isEmpty()) {
            MutableComponent survivorComponent = Component.empty()
                    .append(Component.translatable("murdermystery.label.survivor").withStyle(ChatFormatting.GREEN));
            for (GamePlayer survivor : survivors) {
                TextColor color = TextColor.fromRgb(ColorUtils.parseColorToInt(survivor.getGameTeamColor()));
                survivorComponent.append(Component.literal(" "))
                        .append(CommandUtils.buildIntBracketWithColor(survivor.getGameSingleId(), color))
                        .append(Component.literal(survivor.getPlayerName()).withStyle(survivor.isEliminated() ? ChatFormatting.GRAY : ChatFormatting.GOLD));
            }
            winnerComponent.append(Component.literal("\n")
                    .append(survivorComponent));
        }
        // 侦探 Detective
        if (!detectives.isEmpty()) {
            MutableComponent detectiveComponent = Component.empty()
                    .append(Component.translatable("murdermystery.label.detective").withStyle(ChatFormatting.AQUA));
            for (GamePlayer detective : detectives) {
                TextColor color = TextColor.fromRgb(ColorUtils.parseColorToInt(detective.getGameTeamColor()));
                detectiveComponent.append(Component.literal(" "))
                        .append(CommandUtils.buildIntBracketWithColor(detective.getGameSingleId(), color))
                        .append(Component.literal(detective.getPlayerName()).withStyle(detective.isEliminated() ? ChatFormatting.GRAY : ChatFormatting.GOLD));
            }
            winnerComponent.append(Component.literal("\n")
                    .append(detectiveComponent));
        }
        // 杀手 Murder
        if (!murders.isEmpty()) {
            MutableComponent murderComponent = Component.empty()
                    .append(Component.translatable("murdermystery.label.murder").withStyle(ChatFormatting.RED));
            for (GamePlayer murder : murders) {
                TextColor color = TextColor.fromRgb(ColorUtils.parseColorToInt(murder.getGameTeamColor()));
                murderComponent.append(Component.literal(" "))
                        .append(CommandUtils.buildIntBracketWithColor(murder.getGameSingleId(), color))
                        .append(Component.literal(murder.getPlayerName()).withStyle(murder.isEliminated() ? ChatFormatting.GRAY : ChatFormatting.GOLD));
            }
            winnerComponent.append(Component.literal("\n")
                    .append(murderComponent));
        }

        // 聊天栏发送胜利阵营
        if (serverLevel != null) {
            ChatUtils.sendComponentMessageToAllPlayers(serverLevel, winnerComponent);
        } else {
            MurderMystery.LOGGER.debug("GameManager.serverLevel is null, winner result: {}", winnerComponent);
        }

        // 标题+粒子效果
        for (GamePlayer gamePlayer : winnerGamePlayers) {
            manager.notifyWinner(serverLevel, gamePlayer, BattleRoyale.getGameManager().getGameEntry().winnerParticleId);
        }
    }
}
