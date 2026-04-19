package xiao.murdermystery.command.sub.api.murdermystery;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
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
                .then(Commands.literal(SET_MURDERER)
                        .then(Commands.literal(BY_PLAYER)
                                .then(Commands.argument(PLAYER, EntityArgument.entity())
                                        .executes(MurderMysteryProcessManagerCommand::setMurdererByPlayer)
                                )
                        )
                        .then(Commands.literal(BY_ID)
                                .then(Commands.argument(ID, IntegerArgumentType.integer(0))
                                        .executes(MurderMysteryProcessManagerCommand::setMurdererByGamePlayerId)
                                )
                        )
                )
                .then(Commands.literal(LOOT_SURVIVOR_HEAD)
                        .then(Commands.argument(PLAYER, EntityArgument.entity())
                                .then(Commands.argument(POS, Vec3Argument.vec3())
                                        .executes(MurderMysteryProcessManagerCommand::lootSurvivorHead)
                                )
                        )
                )
                .then(Commands.literal(LOOT_MURDERER_HEAD)
                        .then(Commands.argument(PLAYER, EntityArgument.entity())
                                .then(Commands.argument(POS, Vec3Argument.vec3())
                                        .executes(MurderMysteryProcessManagerCommand::lootMurdererHead)
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
                .then(Commands.literal(HAS_ROLE)
                        .then(Commands.literal(BY_PLAYER)
                                .then(Commands.argument(PLAYER, EntityArgument.entity())
                                        .executes(MurderMysteryProcessManagerCommand::hasRoleByPlayer)
                                )
                        )
                        .then(Commands.literal(BY_ID)
                                .then(Commands.argument(ID, IntegerArgumentType.integer(0))
                                        .executes(MurderMysteryProcessManagerCommand::hasRoleByGamePlayerId)
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
                .then(Commands.literal(IS_MURDERER)
                        .then(Commands.literal(BY_PLAYER)
                                .then(Commands.argument(PLAYER, EntityArgument.entity())
                                        .executes(MurderMysteryProcessManagerCommand::isMurdererByPlayer)
                                )
                        )
                        .then(Commands.literal(BY_ID)
                                .then(Commands.argument(ID, IntegerArgumentType.integer(0))
                                        .executes(MurderMysteryProcessManagerCommand::isMurdererByGamePlayerId)
                                )
                        )
                )
                .then(Commands.literal(GET_SURVIVOR_SIZE).executes(MurderMysteryProcessManagerCommand::getSurvivorSize))
                .then(Commands.literal(GET_DETECTIVE_SIZE).executes(MurderMysteryProcessManagerCommand::getDetectiveSize))
                .then(Commands.literal(GET_MURDERER_SIZE).executes(MurderMysteryProcessManagerCommand::getMurdererSize))
                .then(Commands.literal(GET_STANDING_SURVIVOR_COUNT).executes(MurderMysteryProcessManagerCommand::getStandingSurvivorCount))
                .then(Commands.literal(GET_STANDING_DETECTIVE_COUNT).executes(MurderMysteryProcessManagerCommand::getStandingDetectiveCount))
                .then(Commands.literal(GET_STANDING_MURDERER_COUNT).executes(MurderMysteryProcessManagerCommand::getStandingMurdererCount));
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
    private static int setMurdererByPlayer(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        IGameMainManager gameManager = BattleRoyale.getGameManager();
        @Nullable IMurderMysteryProcessManager mmProcessManager = getMMProcessManager(gameManager);
        if (mmProcessManager == null) return -1;
        Entity entity = EntityArgument.getEntity(context, PLAYER);
        @Nullable GamePlayer gamePlayer = gameManager.getTeamManager().getGamePlayerByUUID(entity.getUUID());
        if (gamePlayer == null) return -2;
        return mmProcessManager.setMurderer(gamePlayer) ? Command.SINGLE_SUCCESS : 0;
    }
    private static int setMurdererByGamePlayerId(CommandContext<CommandSourceStack> context) {
        IGameMainManager gameManager = BattleRoyale.getGameManager();
        @Nullable IMurderMysteryProcessManager mmProcessManager = getMMProcessManager(gameManager);
        if (mmProcessManager == null) return -1;
        int playerId = IntegerArgumentType.getInteger(context, ID);
        @Nullable GamePlayer gamePlayer = gameManager.getTeamManager().getGamePlayerBySingleId(playerId);
        if (gamePlayer == null) return -2;
        return mmProcessManager.setMurderer(gamePlayer) ? Command.SINGLE_SUCCESS : 0;
    }
    private static int lootSurvivorHead(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        IGameMainManager gameManager = BattleRoyale.getGameManager();
        @Nullable IMurderMysteryProcessManager mmProcessManager = getMMProcessManager(gameManager);
        if (mmProcessManager == null) return -1;
        ServerLevel serverLevel = gameManager.getServerLevel();
        if (serverLevel == null) return -2;
        Entity entity = EntityArgument.getEntity(context, PLAYER);
        if (!(entity instanceof LivingEntity livingEntity)) return -3;
        Vec3 pos = Vec3Argument.getVec3(context, POS);
        return mmProcessManager.lootSurvivorHead(serverLevel, livingEntity, pos) ? Command.SINGLE_SUCCESS : 0;
    }
    private static int lootMurdererHead(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        IGameMainManager gameManager = BattleRoyale.getGameManager();
        @Nullable IMurderMysteryProcessManager mmProcessManager = getMMProcessManager(gameManager);
        if (mmProcessManager == null) return -1;
        ServerLevel serverLevel = gameManager.getServerLevel();
        if (serverLevel == null) return -2;
        Entity entity = EntityArgument.getEntity(context, PLAYER);
        if (!(entity instanceof LivingEntity livingEntity)) return -3;
        Vec3 pos = Vec3Argument.getVec3(context, POS);
        return mmProcessManager.lootMurdererHead(serverLevel, livingEntity, pos) ? Command.SINGLE_SUCCESS : 0;
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
    private static int hasRoleByPlayer(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        IGameMainManager gameManager = BattleRoyale.getGameManager();
        @Nullable IMurderMysteryProcessManager mmProcessManager = getMMProcessManager(gameManager);
        if (mmProcessManager == null) return -1;
        Entity entity = EntityArgument.getEntity(context, PLAYER);
        @Nullable GamePlayer gamePlayer = gameManager.getTeamManager().getGamePlayerByUUID(entity.getUUID());
        if (gamePlayer == null) return -2;
        return mmProcessManager.hasRole(gamePlayer) ? Command.SINGLE_SUCCESS : 0;
    }
    private static int hasRoleByGamePlayerId(CommandContext<CommandSourceStack> context) {
        IGameMainManager gameManager = BattleRoyale.getGameManager();
        @Nullable IMurderMysteryProcessManager mmProcessManager = getMMProcessManager(gameManager);
        if (mmProcessManager == null) return -1;
        int playerId = IntegerArgumentType.getInteger(context, ID);
        @Nullable GamePlayer gamePlayer = gameManager.getTeamManager().getGamePlayerBySingleId(playerId);
        if (gamePlayer == null) return -2;
        return mmProcessManager.hasRole(gamePlayer) ? Command.SINGLE_SUCCESS : 0;
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
    private static int isMurdererByPlayer(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        IGameMainManager gameManager = BattleRoyale.getGameManager();
        @Nullable IMurderMysteryProcessManager mmProcessManager = getMMProcessManager(gameManager);
        if (mmProcessManager == null) return -1;
        Entity entity = EntityArgument.getEntity(context, PLAYER);
        @Nullable GamePlayer gamePlayer = gameManager.getTeamManager().getGamePlayerByUUID(entity.getUUID());
        if (gamePlayer == null) return -2;
        return mmProcessManager.isMurderer(gamePlayer) ? Command.SINGLE_SUCCESS : 0;
    }
    private static int isMurdererByGamePlayerId(CommandContext<CommandSourceStack> context) {
        IGameMainManager gameManager = BattleRoyale.getGameManager();
        @Nullable IMurderMysteryProcessManager mmProcessManager = getMMProcessManager(gameManager);
        if (mmProcessManager == null) return -1;
        int playerId = IntegerArgumentType.getInteger(context, ID);
        @Nullable GamePlayer gamePlayer = gameManager.getTeamManager().getGamePlayerBySingleId(playerId);
        if (gamePlayer == null) return -2;
        return mmProcessManager.isMurderer(gamePlayer) ? Command.SINGLE_SUCCESS : 0;
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
    private static int getMurdererSize(CommandContext<CommandSourceStack> context) {
        @Nullable IMurderMysteryProcessManager mmProcessManager = getMMProcessManager(BattleRoyale.getGameManager());
        if (mmProcessManager == null) return -1;
        return mmProcessManager.getMurdererSize();
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
    private static int getStandingMurdererCount(CommandContext<CommandSourceStack> context) {
        @Nullable IMurderMysteryProcessManager mmProcessManager = getMMProcessManager(BattleRoyale.getGameManager());
        if (mmProcessManager == null) return -1;
        return mmProcessManager.getStandingMurdererCount();
    }
}
