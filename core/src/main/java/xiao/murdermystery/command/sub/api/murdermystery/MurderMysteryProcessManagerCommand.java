package xiao.murdermystery.command.sub.api.murdermystery;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;
import xiao.battleroyale.BattleRoyale;
import xiao.battleroyale.api.game.IGameMainManager;
import xiao.battleroyale.common.game.team.GamePlayer;
import xiao.murdermystery.api.game.process.murdermystery.IMurderMysteryProcessManager;

import static xiao.murdermystery.command.CommandArg.*;

public class MurderMysteryProcessManagerCommand {

    public static LiteralArgumentBuilder<CommandSourceStack> get() {
        return Commands.literal(MURDER_MYSTERY)
                // IMurderMysteryGameManagement
                .then(Commands.literal(SET_SURVIVOR)
                        .then(Commands.literal(BY_PLAYER)
                                .then(Commands.argument(PLAYER, EntityArgument.entity())
                                        .executes(MurderMysteryProcessManagerCommand::setSurvivorByPlayer)
                                )
                        )
                        .then(Commands.literal(BY_ID)
                                .then(Commands.argument(ID, IntegerArgumentType.integer(0))
                                        .executes(MurderMysteryProcessManagerCommand::setSurvivorByGamePlayerId)
                                )
                        )
                )
                .then(Commands.literal(SET_DETECTIVE)
                        .then(Commands.literal(BY_PLAYER)
                                .then(Commands.argument(PLAYER, EntityArgument.entity())
                                        .executes(MurderMysteryProcessManagerCommand::setDetectiveByPlayer)
                                )
                        )
                        .then(Commands.literal(BY_ID)
                                .then(Commands.argument(ID, IntegerArgumentType.integer(0))
                                        .executes(MurderMysteryProcessManagerCommand::setDetectiveByGamePlayerId)
                                )
                        )
                )
                .then(Commands.literal(SET_MURDER)
                        .then(Commands.literal(BY_PLAYER)
                                .then(Commands.argument(PLAYER, EntityArgument.entity())
                                        .executes(MurderMysteryProcessManagerCommand::setMurderByPlayer)
                                )
                        )
                        .then(Commands.literal(BY_ID)
                                .then(Commands.argument(ID, IntegerArgumentType.integer(0))
                                        .executes(MurderMysteryProcessManagerCommand::setMurderByGamePlayerId)
                                )
                        )
                )
                // IMurderMysteryDataManagement
                // IMurderMysteryInfoGetter
                .then(Commands.literal(IS_TEAM_ELIMINATED)
                        .then(Commands.literal(BY_PLAYER)
                                .then(Commands.argument(PLAYER, EntityArgument.entity())
                                        .executes(MurderMysteryProcessManagerCommand::isTeamEliminatedByPlayer)
                                )
                        )
                        .then(Commands.literal(BY_ID)
                                .then(Commands.argument(ID, IntegerArgumentType.integer(0))
                                        .executes(MurderMysteryProcessManagerCommand::isTeamEliminatedById)
                                )
                        )
                )
                .then(Commands.literal(IS_SURVIVOR)
                        .then(Commands.literal(BY_PLAYER)
                                .then(Commands.argument(PLAYER, EntityArgument.entity())
                                        .executes(MurderMysteryProcessManagerCommand::isSurvivorByPlayer)
                                )
                        )
                        .then(Commands.literal(BY_ID)
                                .then(Commands.argument(ID, IntegerArgumentType.integer(0))
                                        .executes(MurderMysteryProcessManagerCommand::isSurvivorByGamePlayerId)
                                )
                        )
                )
                .then(Commands.literal(IS_DETECTIVE)
                        .then(Commands.literal(BY_PLAYER)
                                .then(Commands.argument(PLAYER, EntityArgument.entity())
                                        .executes(MurderMysteryProcessManagerCommand::isDetectiveByPlayer)
                                )
                        )
                        .then(Commands.literal(BY_ID)
                                .then(Commands.argument(ID, IntegerArgumentType.integer(0))
                                        .executes(MurderMysteryProcessManagerCommand::isDetectiveByGamePlayerId)
                                )
                        )
                )
                .then(Commands.literal(IS_MURDER)
                        .then(Commands.literal(BY_PLAYER)
                                .then(Commands.argument(PLAYER, EntityArgument.entity())
                                        .executes(MurderMysteryProcessManagerCommand::isMurderByPlayer)
                                )
                        )
                        .then(Commands.literal(BY_ID)
                                .then(Commands.argument(ID, IntegerArgumentType.integer(0))
                                        .executes(MurderMysteryProcessManagerCommand::isMurderByGamePlayerId)
                                )
                        )
                )
                .then(Commands.literal(GET_SURVIVOR_SIZE).executes(MurderMysteryProcessManagerCommand::getSurvivorSize))
                .then(Commands.literal(GET_DETECTIVE_SIZE).executes(MurderMysteryProcessManagerCommand::getDetectiveSize))
                .then(Commands.literal(GET_MURDER_SIZE).executes(MurderMysteryProcessManagerCommand::getMurderSize))
                .then(Commands.literal(GET_STANDING_SURVIVOR_COUNT).executes(MurderMysteryProcessManagerCommand::getStandingSurvivorCount))
                .then(Commands.literal(GET_STANDING_DETECTIVE_COUNT).executes(MurderMysteryProcessManagerCommand::getStandingDetectiveCount))
                .then(Commands.literal(GET_STANDING_MURDER_COUNT).executes(MurderMysteryProcessManagerCommand::getStandingMurderCount));
    }

    private static @Nullable IMurderMysteryProcessManager getMMProcessManager(IGameMainManager gameManager) {
        return gameManager.getGameProcessManager() instanceof IMurderMysteryProcessManager mmProcessManager ? mmProcessManager : null;
    }

    // --------IMurderMysteryGameManagement--------

    private static int setSurvivorByPlayer(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        IGameMainManager gameManager = BattleRoyale.getGameManager();
        @Nullable IMurderMysteryProcessManager mmProcessManager = getMMProcessManager(gameManager);
        if (mmProcessManager == null) return -1;
        Entity entity = EntityArgument.getEntity(context, PLAYER);
        @Nullable GamePlayer gamePlayer = gameManager.getTeamManager().getGamePlayerByUUID(entity.getUUID());
        if (gamePlayer == null) return -2;
        return mmProcessManager.setSurvivor(gamePlayer) ? Command.SINGLE_SUCCESS : 0;
    }
    private static int setSurvivorByGamePlayerId(CommandContext<CommandSourceStack> context) {
        IGameMainManager gameManager = BattleRoyale.getGameManager();
        @Nullable IMurderMysteryProcessManager mmProcessManager = getMMProcessManager(gameManager);
        if (mmProcessManager == null) return -1;
        int playerId = IntegerArgumentType.getInteger(context, ID);
        @Nullable GamePlayer gamePlayer = gameManager.getTeamManager().getGamePlayerBySingleId(playerId);
        if (gamePlayer == null) return -2;
        return mmProcessManager.setSurvivor(gamePlayer) ? Command.SINGLE_SUCCESS : 0;
    }
    private static int setDetectiveByPlayer(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        IGameMainManager gameManager = BattleRoyale.getGameManager();
        @Nullable IMurderMysteryProcessManager mmProcessManager = getMMProcessManager(gameManager);
        if (mmProcessManager == null) return -1;
        Entity entity = EntityArgument.getEntity(context, PLAYER);
        @Nullable GamePlayer gamePlayer = gameManager.getTeamManager().getGamePlayerByUUID(entity.getUUID());
        if (gamePlayer == null) return -2;
        return mmProcessManager.setDetective(gamePlayer) ? Command.SINGLE_SUCCESS : 0;
    }
    private static int setDetectiveByGamePlayerId(CommandContext<CommandSourceStack> context) {
        IGameMainManager gameManager = BattleRoyale.getGameManager();
        @Nullable IMurderMysteryProcessManager mmProcessManager = getMMProcessManager(gameManager);
        if (mmProcessManager == null) return -1;
        int playerId = IntegerArgumentType.getInteger(context, ID);
        @Nullable GamePlayer gamePlayer = gameManager.getTeamManager().getGamePlayerBySingleId(playerId);
        if (gamePlayer == null) return -2;
        return mmProcessManager.setDetective(gamePlayer) ? Command.SINGLE_SUCCESS : 0;
    }
    private static int setMurderByPlayer(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        IGameMainManager gameManager = BattleRoyale.getGameManager();
        @Nullable IMurderMysteryProcessManager mmProcessManager = getMMProcessManager(gameManager);
        if (mmProcessManager == null) return -1;
        Entity entity = EntityArgument.getEntity(context, PLAYER);
        @Nullable GamePlayer gamePlayer = gameManager.getTeamManager().getGamePlayerByUUID(entity.getUUID());
        if (gamePlayer == null) return -2;
        return mmProcessManager.setMurder(gamePlayer) ? Command.SINGLE_SUCCESS : 0;
    }
    private static int setMurderByGamePlayerId(CommandContext<CommandSourceStack> context) {
        IGameMainManager gameManager = BattleRoyale.getGameManager();
        @Nullable IMurderMysteryProcessManager mmProcessManager = getMMProcessManager(gameManager);
        if (mmProcessManager == null) return -1;
        int playerId = IntegerArgumentType.getInteger(context, ID);
        @Nullable GamePlayer gamePlayer = gameManager.getTeamManager().getGamePlayerBySingleId(playerId);
        if (gamePlayer == null) return -2;
        return mmProcessManager.setMurder(gamePlayer) ? Command.SINGLE_SUCCESS : 0;
    }

    // --------IMurderMysteryDataManagement--------

    // --------IMurderMysteryInfoGetter--------

    private static int isTeamEliminatedByPlayer(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        IGameMainManager gameManager = BattleRoyale.getGameManager();
        @Nullable IMurderMysteryProcessManager mmProcessManager = getMMProcessManager(gameManager);
        if (mmProcessManager == null) return -1;
        Entity entity = EntityArgument.getEntity(context, PLAYER);
        @Nullable GamePlayer gamePlayer = gameManager.getTeamManager().getGamePlayerByUUID(entity.getUUID());
        if (gamePlayer == null) return -2;
        return mmProcessManager.isTeamEliminated(gamePlayer) ? Command.SINGLE_SUCCESS : 0;
    }
    private static int isTeamEliminatedById(CommandContext<CommandSourceStack> context) {
        IGameMainManager gameManager = BattleRoyale.getGameManager();
        @Nullable IMurderMysteryProcessManager mmProcessManager = getMMProcessManager(gameManager);
        if (mmProcessManager == null) return -1;
        int playerId = IntegerArgumentType.getInteger(context, ID);
        @Nullable GamePlayer gamePlayer = gameManager.getTeamManager().getGamePlayerBySingleId(playerId);
        if (gamePlayer == null) return -2;
        return mmProcessManager.isTeamEliminated(gamePlayer) ? Command.SINGLE_SUCCESS : 0;
    }
    private static int isSurvivorByPlayer(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        IGameMainManager gameManager = BattleRoyale.getGameManager();
        @Nullable IMurderMysteryProcessManager mmProcessManager = getMMProcessManager(gameManager);
        if (mmProcessManager == null) return -1;
        Entity entity = EntityArgument.getEntity(context, PLAYER);
        @Nullable GamePlayer gamePlayer = gameManager.getTeamManager().getGamePlayerByUUID(entity.getUUID());
        if (gamePlayer == null) return -2;
        return mmProcessManager.isSurvivor(gamePlayer) ? Command.SINGLE_SUCCESS : 0;
    }
    private static int isSurvivorByGamePlayerId(CommandContext<CommandSourceStack> context) {
        IGameMainManager gameManager = BattleRoyale.getGameManager();
        @Nullable IMurderMysteryProcessManager mmProcessManager = getMMProcessManager(gameManager);
        if (mmProcessManager == null) return -1;
        int playerId = IntegerArgumentType.getInteger(context, ID);
        @Nullable GamePlayer gamePlayer = gameManager.getTeamManager().getGamePlayerBySingleId(playerId);
        if (gamePlayer == null) return -2;
        return mmProcessManager.isSurvivor(gamePlayer) ? Command.SINGLE_SUCCESS : 0;
    }
    private static int isDetectiveByPlayer(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        IGameMainManager gameManager = BattleRoyale.getGameManager();
        @Nullable IMurderMysteryProcessManager mmProcessManager = getMMProcessManager(gameManager);
        if (mmProcessManager == null) return -1;
        Entity entity = EntityArgument.getEntity(context, PLAYER);
        @Nullable GamePlayer gamePlayer = gameManager.getTeamManager().getGamePlayerByUUID(entity.getUUID());
        if (gamePlayer == null) return -2;
        return mmProcessManager.isDetective(gamePlayer) ? Command.SINGLE_SUCCESS : 0;
    }
    private static int isDetectiveByGamePlayerId(CommandContext<CommandSourceStack> context) {
        IGameMainManager gameManager = BattleRoyale.getGameManager();
        @Nullable IMurderMysteryProcessManager mmProcessManager = getMMProcessManager(gameManager);
        if (mmProcessManager == null) return -1;
        int playerId = IntegerArgumentType.getInteger(context, ID);
        @Nullable GamePlayer gamePlayer = gameManager.getTeamManager().getGamePlayerBySingleId(playerId);
        if (gamePlayer == null) return -2;
        return mmProcessManager.isDetective(gamePlayer) ? Command.SINGLE_SUCCESS : 0;
    }
    private static int isMurderByPlayer(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        IGameMainManager gameManager = BattleRoyale.getGameManager();
        @Nullable IMurderMysteryProcessManager mmProcessManager = getMMProcessManager(gameManager);
        if (mmProcessManager == null) return -1;
        Entity entity = EntityArgument.getEntity(context, PLAYER);
        @Nullable GamePlayer gamePlayer = gameManager.getTeamManager().getGamePlayerByUUID(entity.getUUID());
        if (gamePlayer == null) return -2;
        return mmProcessManager.isMurder(gamePlayer) ? Command.SINGLE_SUCCESS : 0;
    }
    private static int isMurderByGamePlayerId(CommandContext<CommandSourceStack> context) {
        IGameMainManager gameManager = BattleRoyale.getGameManager();
        @Nullable IMurderMysteryProcessManager mmProcessManager = getMMProcessManager(gameManager);
        if (mmProcessManager == null) return -1;
        int playerId = IntegerArgumentType.getInteger(context, ID);
        @Nullable GamePlayer gamePlayer = gameManager.getTeamManager().getGamePlayerBySingleId(playerId);
        if (gamePlayer == null) return -2;
        return mmProcessManager.isMurder(gamePlayer) ? Command.SINGLE_SUCCESS : 0;
    }
    private static int getSurvivorSize(CommandContext<CommandSourceStack> context) {
        @Nullable IMurderMysteryProcessManager mmProcessManager = getMMProcessManager(BattleRoyale.getGameManager());
        if (mmProcessManager == null) return -1;
        return mmProcessManager.getSurvivorSize();
    }
    private static int getDetectiveSize(CommandContext<CommandSourceStack> context) {
        @Nullable IMurderMysteryProcessManager mmProcessManager = getMMProcessManager(BattleRoyale.getGameManager());
        if (mmProcessManager == null) return -1;
        return mmProcessManager.getDetectiveSize();
    }
    private static int getMurderSize(CommandContext<CommandSourceStack> context) {
        @Nullable IMurderMysteryProcessManager mmProcessManager = getMMProcessManager(BattleRoyale.getGameManager());
        if (mmProcessManager == null) return -1;
        return mmProcessManager.getMurderSize();
    }
    private static int getStandingSurvivorCount(CommandContext<CommandSourceStack> context) {
        @Nullable IMurderMysteryProcessManager mmProcessManager = getMMProcessManager(BattleRoyale.getGameManager());
        if (mmProcessManager == null) return -1;
        return mmProcessManager.getStandingSurvivorCount();
    }
    private static int getStandingDetectiveCount(CommandContext<CommandSourceStack> context) {
        @Nullable IMurderMysteryProcessManager mmProcessManager = getMMProcessManager(BattleRoyale.getGameManager());
        if (mmProcessManager == null) return -1;
        return mmProcessManager.getStandingDetectiveCount();
    }
    private static int getStandingMurderCount(CommandContext<CommandSourceStack> context) {
        @Nullable IMurderMysteryProcessManager mmProcessManager = getMMProcessManager(BattleRoyale.getGameManager());
        if (mmProcessManager == null) return -1;
        return mmProcessManager.getStandingMurderCount();
    }
}
