package xiao.murdermystery.api.event.custom.murdermystery;

import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xiao.battleroyale.BattleRoyale;
import xiao.battleroyale.api.event.CustomEventType;
import xiao.battleroyale.api.event.ICustomEvent;
import xiao.battleroyale.api.event.ICustomEventHandler;
import xiao.battleroyale.api.minecraft.CommandLevel;
import xiao.battleroyale.common.game.team.GamePlayer;
import xiao.battleroyale.event.EventDispatcher;
import xiao.murdermystery.api.game.process.murdermystery.IMurderMysteryProcessManager;

public class CountdownEvent extends MurderMysteryEvent {

    protected final @NotNull GamePlayer gamePlayer;
    protected final @NotNull ServerPlayer player;
    protected final int countdown;

    public CountdownEvent(@NotNull IMurderMysteryProcessManager manager, @NotNull GamePlayer gamePlayer, @NotNull ServerPlayer player, int countdown) {
        super(manager);
        this.gamePlayer = gamePlayer;
        this.player = player;
        this.countdown = countdown;
    }
    public @NotNull GamePlayer getGamePlayer() {
        return gamePlayer;
    }
    public @NotNull ServerPlayer getPlayer() {
        return player;
    }
    public int getCountdown() {
        return countdown;
    }

    @Override
    public @NotNull CommandSourceStack createCommandSourceStack(@Nullable CommandSource source) {
        return new CommandSourceStack(
                source != null ? source : CommandSource.NULL,
                gamePlayer.getLastPos(),
                player.getRotationVector(),
                BattleRoyale.getGameManager().getServerLevel(),
                CommandLevel.permission(4),
                this.getTextName(),
                this.getDisplayName(),
                BattleRoyale.getMinecraftServer(),
                player
        );
    }
    @Override public String getTextName() {
        return player.getName().getString();
    }
    @Override public Component getDisplayName() {
        return player.getDisplayName();
    }

    private static final EventDispatcher<ICustomEventHandler, ICustomEvent, CustomEventType> _EVENT_DISPATCHER = BattleRoyale.getEventPoster().getEventDispatcher(CountdownEvent.class);
    @Override public @NotNull EventDispatcher<ICustomEventHandler, ICustomEvent, CustomEventType> getEventDispatcher() {
        return _EVENT_DISPATCHER;
    }
}
