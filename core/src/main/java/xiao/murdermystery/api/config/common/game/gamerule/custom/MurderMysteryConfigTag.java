package xiao.murdermystery.api.config.common.game.gamerule.custom;

import xiao.battleroyale.api.config.sub.ConfigEntryTag;

public class MurderMysteryConfigTag extends ConfigEntryTag {

    public static final String PROTOCOL_NAME = "murdermystery";
    public static final String GAME_START_TICK = "gameStartTick";
    public static final String COUNTDOWN_SECONDS = "countdownSeconds";
    public static final String SURVIVE_TIME_GOAL = "surviveTimeGoal";
    public static final String SEND_GAME_PLAYER_NOTIFICATION_MESSAGE = "sendGamePlayerNotificationMessage";
    public static final String FILTER_ITEM_PICKUP = "filterItemPickup";
    public static final String SURVIVOR_ITEM_TAG = "survivorItemTag";
    public static final String MURDER_ITEM_TAG = "murderItemTag";
    public static final String SURVIVOR_FUNCS = "survivorFuncs";
    public static final String DETECTIVE_DELAY = "detectiveDelay";
    public static final String DETECTIVE_FUNCS = "detectiveFuncs";
    public static final String MURDER_DELAY = "murderDelay";
    public static final String MURDER_FUNCS = "murderFuncs";

    private MurderMysteryConfigTag() {}
}