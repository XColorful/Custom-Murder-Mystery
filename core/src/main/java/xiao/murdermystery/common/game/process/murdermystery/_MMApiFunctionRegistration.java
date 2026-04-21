package xiao.murdermystery.common.game.process.murdermystery;

import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.functions.CommandFunction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.ServerFunctionManager;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import xiao.battleroyale.BattleRoyale;
import xiao.battleroyale.api.minecraft.CommandLevel;
import xiao.battleroyale.util.CommandUtils;
import xiao.murdermystery.MurderMystery;

import java.util.Optional;

public class _MMApiFunctionRegistration {

    private static class _MMApiFunctionRegistrationHolder {
        private static final _MMApiFunctionRegistration INSTANCE = new _MMApiFunctionRegistration();
    }

    public static _MMApiFunctionRegistration get() {
        return _MMApiFunctionRegistrationHolder.INSTANCE;
    }

    protected _MMApiFunctionRegistration() {}

    public static void register(MMGameProcessManager manager) {
        CommandSourceStack sourceStack = createEmptyCommandSourceStack("_MMApiFunctionRegistration::register");
        execute(sourceStack, manager.configEntry.apiFunctionRegister);
    }
    public static void unregister(MMGameProcessManager manager) {
        CommandSourceStack sourceStack = createEmptyCommandSourceStack("_MMApiFunctionRegistration::unregister");
        execute(sourceStack, manager.configEntry.apiFunctionUnregister);
    }

    private static void execute(@NotNull final CommandSourceStack sourceStack, String functionRl) {
        if (functionRl.isEmpty()) {
            MurderMystery.LOGGER.debug("Skipped empty functionRl registration");
        }

        final int[] executedLines = {0};
        ServerFunctionManager serverFunctionManager = sourceStack.getServer().getFunctions();
        var rl = BattleRoyale.getMcRegistry().createResourceLocation(functionRl);
        if (rl == null) {
            MurderMystery.LOGGER.debug("Failed to create functionRl from {}", functionRl);
            return;
        }

        Optional<CommandFunction<CommandSourceStack>> function = serverFunctionManager.get(rl);
        if (function.isPresent()) {
            CommandUtils.executeCommand(serverFunctionManager, function.get(), sourceStack);
        } else {
            MurderMystery.LOGGER.debug("functionRl {} does not have present function", rl);
        }
    }

    private static @NotNull CommandSourceStack createEmptyCommandSourceStack(String comment) {
        return new CommandSourceStack(
                CommandSource.NULL,
                Vec3.ZERO,
                Vec2.ZERO,
                BattleRoyale.getGameManager().getServerLevel(),
                CommandLevel.permission(4),
                comment,
                Component.literal(comment),
                BattleRoyale.getMinecraftServer(),
                null
        );
    }
}
