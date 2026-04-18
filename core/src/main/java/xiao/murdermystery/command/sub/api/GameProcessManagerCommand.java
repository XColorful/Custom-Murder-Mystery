package xiao.murdermystery.command.sub.api;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import xiao.murdermystery.command.sub.api.murdermystery.MurderMysteryProcessManagerCommand;

import static xiao.murdermystery.command.CommandArg.GAME_PROCESS_MANAGER;

public class GameProcessManagerCommand {

    public static LiteralArgumentBuilder<CommandSourceStack> get() {
        return Commands.literal(GAME_PROCESS_MANAGER)
                // IMurderMysteryProcessManager
                .then(MurderMysteryProcessManagerCommand.get());
    }
}
