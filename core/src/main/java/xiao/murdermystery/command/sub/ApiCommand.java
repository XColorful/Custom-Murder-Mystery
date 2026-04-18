package xiao.murdermystery.command.sub;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import xiao.murdermystery.command.sub.api.GameProcessManagerCommand;

import static xiao.murdermystery.command.CommandArg.API;

public class ApiCommand {

    public static LiteralArgumentBuilder<CommandSourceStack> get() {
        LiteralArgumentBuilder<CommandSourceStack> apiCommand = Commands.literal(API);

        // MurderMystery API
        apiCommand.then(GameProcessManagerCommand.get());

        return apiCommand;
    }
}
