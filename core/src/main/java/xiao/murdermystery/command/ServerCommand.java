package xiao.murdermystery.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import xiao.battleroyale.api.minecraft.CommandLevel;
import xiao.murdermystery.command.sub.ApiCommand;

public class ServerCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(get(xiao.battleroyale.command.CommandArg.MOD_ID));
        dispatcher.register(get(xiao.battleroyale.command.CommandArg.MOD_NAME_SHORT));
    }

    public static LiteralArgumentBuilder<CommandSourceStack> get(String rootName) {
        LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal(rootName);
        root.then(ApiCommand.get()
                .requires(CommandLevel.hasPermission(2)));
        return root;
    }
}