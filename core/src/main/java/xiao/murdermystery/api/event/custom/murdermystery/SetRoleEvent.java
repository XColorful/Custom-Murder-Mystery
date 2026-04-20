package xiao.murdermystery.api.event.custom.murdermystery;

import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xiao.battleroyale.BattleRoyale;
import xiao.battleroyale.api.event.CustomEventType;
import xiao.battleroyale.api.event.ICustomEvent;
import xiao.battleroyale.api.event.ICustomEventHandler;
import xiao.battleroyale.api.minecraft.CommandLevel;
import xiao.battleroyale.common.game.team.GamePlayer;
import xiao.battleroyale.event.EventDispatcher;
import xiao.battleroyale.util.GameUtils;
import xiao.murdermystery.api.game.process.murdermystery.IMurderMysteryProcessManager;

public abstract class SetRoleEvent extends MurderMysteryEvent {

    protected final @NotNull GamePlayer gamePlayer;
    protected final @Nullable LivingEntity livingEntity;

    public SetRoleEvent(@NotNull IMurderMysteryProcessManager manager, @NotNull GamePlayer gamePlayer) {
        super(manager);
        this.gamePlayer = gamePlayer;
        this.livingEntity = GameUtils.getLivingEntity(BattleRoyale.getGameManager().getServerLevel(), gamePlayer.getPlayerUUID());
    }
    public @NotNull GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    @Override
    public @NotNull CommandSourceStack createCommandSourceStack(@Nullable CommandSource source) {
        return new CommandSourceStack(
                source != null ? source : CommandSource.NULL,
                gamePlayer.getLastPos(),
                livingEntity != null ? livingEntity.getRotationVector() : Vec2.ZERO,
                BattleRoyale.getGameManager().getServerLevel(),
                CommandLevel.permission(4),
                this.getTextName(),
                this.getDisplayName(),
                BattleRoyale.getMinecraftServer(),
                livingEntity
        );
    }
    @Override public Component getDisplayName() {
        return livingEntity != null ? livingEntity.getDisplayName() : Component.literal(getTextName());
    }

    public abstract static class SetRoleFinishEvent extends SetRoleEvent {
        public SetRoleFinishEvent(@NotNull IMurderMysteryProcessManager manager, @NotNull GamePlayer gamePlayer) {
            super(manager, gamePlayer);
        }
        @Override public final boolean isCancelable() {
            return false;
        }
    }

    public static class SurvivorRoleEvent extends SetRoleEvent {
        public SurvivorRoleEvent(@NotNull IMurderMysteryProcessManager manager, @NotNull GamePlayer gamePlayer) {
            super(manager, gamePlayer);
        }
        @Override public String getTextName() {
            return livingEntity != null ? livingEntity.getName().getString() : String.format("%s %s SurvivorRoleEvent", manager.getManagerName(), gamePlayer.getNameWithId());
        }
        private static final EventDispatcher<ICustomEventHandler, ICustomEvent, CustomEventType> _EVENT_DISPATCHER = BattleRoyale.getEventPoster().getEventDispatcher(SurvivorRoleEvent.class);
        @Override public @NotNull EventDispatcher<ICustomEventHandler, ICustomEvent, CustomEventType> getEventDispatcher() {
            return _EVENT_DISPATCHER;
        }
    }
    public static class SurvivorRoleFinishEvent extends SetRoleFinishEvent {
        public SurvivorRoleFinishEvent(@NotNull IMurderMysteryProcessManager manager, @NotNull GamePlayer gamePlayer) {
            super(manager, gamePlayer);
        }
        @Override public String getTextName() {
            return livingEntity != null ? livingEntity.getName().getString() : String.format("%s %s SurvivorRoleFinishEvent", manager.getManagerName(), gamePlayer.getNameWithId());
        }
        private static final EventDispatcher<ICustomEventHandler, ICustomEvent, CustomEventType> _EVENT_DISPATCHER = BattleRoyale.getEventPoster().getEventDispatcher(SurvivorRoleFinishEvent.class);
        @Override public @NotNull EventDispatcher<ICustomEventHandler, ICustomEvent, CustomEventType> getEventDispatcher() {
            return _EVENT_DISPATCHER;
        }
    }

    public static class DetectiveRoleEvent extends SetRoleEvent {
        public DetectiveRoleEvent(@NotNull IMurderMysteryProcessManager manager, @NotNull GamePlayer gamePlayer) {
            super(manager, gamePlayer);
        }
        @Override public String getTextName() {
            return livingEntity != null ? livingEntity.getName().getString() : String.format("%s %s DetectiveRoleEvent", manager.getManagerName(), gamePlayer.getNameWithId());
        }
        private static final EventDispatcher<ICustomEventHandler, ICustomEvent, CustomEventType> _EVENT_DISPATCHER = BattleRoyale.getEventPoster().getEventDispatcher(DetectiveRoleEvent.class);
        @Override public @NotNull EventDispatcher<ICustomEventHandler, ICustomEvent, CustomEventType> getEventDispatcher() {
            return _EVENT_DISPATCHER;
        }
    }
    public static class DetectiveRoleFinishEvent extends SetRoleFinishEvent {
        public DetectiveRoleFinishEvent(@NotNull IMurderMysteryProcessManager manager, @NotNull GamePlayer gamePlayer) {
            super(manager, gamePlayer);
        }
        @Override public String getTextName() {
            return livingEntity != null ? livingEntity.getName().getString() : String.format("%s %s DetectiveRoleFinishEvent", manager.getManagerName(), gamePlayer.getNameWithId());
        }
        private static final EventDispatcher<ICustomEventHandler, ICustomEvent, CustomEventType> _EVENT_DISPATCHER = BattleRoyale.getEventPoster().getEventDispatcher(DetectiveRoleFinishEvent.class);
        @Override public @NotNull EventDispatcher<ICustomEventHandler, ICustomEvent, CustomEventType> getEventDispatcher() {
            return _EVENT_DISPATCHER;
        }
    }

    public static class MurdererRoleEvent extends SetRoleEvent {
        public MurdererRoleEvent(@NotNull IMurderMysteryProcessManager manager, @NotNull GamePlayer gamePlayer) {
            super(manager, gamePlayer);
        }
        @Override public String getTextName() {
            return livingEntity != null ? livingEntity.getName().getString() : String.format("%s %s MurdererRoleEvent", manager.getManagerName(), gamePlayer.getNameWithId());
        }
        private static final EventDispatcher<ICustomEventHandler, ICustomEvent, CustomEventType> _EVENT_DISPATCHER = BattleRoyale.getEventPoster().getEventDispatcher(MurdererRoleEvent.class);
        @Override public @NotNull EventDispatcher<ICustomEventHandler, ICustomEvent, CustomEventType> getEventDispatcher() {
            return _EVENT_DISPATCHER;
        }
    }
    public static class MurdererRoleFinishEvent extends SetRoleFinishEvent {
        public MurdererRoleFinishEvent(@NotNull IMurderMysteryProcessManager manager, @NotNull GamePlayer gamePlayer) {
            super(manager, gamePlayer);
        }
        @Override public String getTextName() {
            return livingEntity != null ? livingEntity.getName().getString() : String.format("%s %s MurdererRoleFinishEvent", manager.getManagerName(), gamePlayer.getNameWithId());
        }
        private static final EventDispatcher<ICustomEventHandler, ICustomEvent, CustomEventType> _EVENT_DISPATCHER = BattleRoyale.getEventPoster().getEventDispatcher(MurdererRoleFinishEvent.class);
        @Override public @NotNull EventDispatcher<ICustomEventHandler, ICustomEvent, CustomEventType> getEventDispatcher() {
            return _EVENT_DISPATCHER;
        }
    }
}