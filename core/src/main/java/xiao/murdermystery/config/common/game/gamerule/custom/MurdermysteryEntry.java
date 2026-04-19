package xiao.murdermystery.config.common.game.gamerule.custom;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xiao.battleroyale.api.config.common.game.gamerule.IGameruleEntry;
import xiao.murdermystery.MurderMystery;
import xiao.murdermystery.api.config.common.game.gamerule.custom.MurderMysteryConfigTag;
import xiao.battleroyale.util.JsonUtils;

import java.util.ArrayList;
import java.util.List;

public class MurdermysteryEntry implements IGameruleEntry {

    public int gameStartTick;
    public int countdownSeconds;
    public int surviveTimeGoal;
    public boolean sendGamePlayerNotificationMessage;
    public boolean filterItemPickup;
    public String survivorItemTag;
    public static final String DEFAULT_SURVIVOR_ITEM_TAG = "survivorItem";
    public String murdererItemTag;
    public static final String DEFAULT_MURDERER_ITEM_TAG = "murdererItem";
    public int survivorDelay;
    public @NotNull List<Integer> survivorFuncs;
    public int detectiveDelay;
    public @NotNull List<Integer> detectiveFuncs;
    public int murdererDelay;
    public @NotNull List<Integer> murdererFuncs;
    public @NotNull String apiFunctionRegister;
    public static final String DEFAULT_REGISTER_FUNCTION = String.format("%s:register", MurderMystery.MOD_ID);
    public @NotNull String apiFunctionUnregister;
    public static final String DEFAULT_UNREGISTER_FUNCTION = String.format("%s:unregister", MurderMystery.MOD_ID);

    public MurdermysteryEntry() {
        this(15 * 20, 10, 10 * 60 * 20, false,
                true, DEFAULT_SURVIVOR_ITEM_TAG, DEFAULT_MURDERER_ITEM_TAG,
                0, null,
                20 * 10, null,
                20 * 10, null,
                DEFAULT_REGISTER_FUNCTION, DEFAULT_UNREGISTER_FUNCTION);
    }
    public MurdermysteryEntry(int gameStartTick, int countdownSeconds, int surviveTimeGoal, boolean sendGamePlayerNotificationMessage,
                              boolean filterItemPickup, String survivorItemTag, String murdererItemTag,
                              int survivorDelay, @Nullable List<Integer> survivorFuncs,
                              int detectiveDelay, @Nullable List<Integer> detectiveFuncs,
                              int murdererDelay, @Nullable List<Integer> murdererFuncs,
                              @NotNull String apiFunctionRegister, @NotNull String apiFunctionUnregister) {
        this.gameStartTick = gameStartTick;
        this.countdownSeconds = countdownSeconds;
        this.surviveTimeGoal = surviveTimeGoal;
        this.sendGamePlayerNotificationMessage = sendGamePlayerNotificationMessage;
        this.filterItemPickup = filterItemPickup;
        this.survivorItemTag = survivorItemTag;
        this.murdererItemTag = murdererItemTag;
        this.survivorDelay = survivorDelay;
        this.survivorFuncs = survivorFuncs != null ? survivorFuncs : new ArrayList<>();
        this.detectiveDelay = Math.max(0, detectiveDelay);
        this.detectiveFuncs = detectiveFuncs != null ? detectiveFuncs : new ArrayList<>();
        this.murdererDelay = Math.max(0, murdererDelay);
        this.murdererFuncs = murdererFuncs != null ? murdererFuncs : new ArrayList<>();
        this.apiFunctionRegister = apiFunctionRegister;
        this.apiFunctionUnregister = apiFunctionUnregister;
    }
    @Override public @NotNull MurdermysteryEntry copy() {
        return new MurdermysteryEntry(gameStartTick, countdownSeconds, surviveTimeGoal, sendGamePlayerNotificationMessage,
                filterItemPickup, survivorItemTag, murdererItemTag,
                survivorDelay, new ArrayList<>(survivorFuncs),
                detectiveDelay, new ArrayList<>(detectiveFuncs),
                murdererDelay, new ArrayList<>(murdererFuncs),
                apiFunctionRegister, apiFunctionUnregister);
    }

    @Override
    public String getType() {
        return "murdermysteryEntry";
    }

    @Override
    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(MurderMysteryConfigTag.GAME_START_TICK, gameStartTick);
        jsonObject.addProperty(MurderMysteryConfigTag.COUNTDOWN_SECONDS, countdownSeconds);
        jsonObject.addProperty(MurderMysteryConfigTag.SURVIVE_TIME_GOAL, surviveTimeGoal);
        jsonObject.addProperty(MurderMysteryConfigTag.SEND_GAME_PLAYER_NOTIFICATION_MESSAGE, sendGamePlayerNotificationMessage);
        jsonObject.addProperty(MurderMysteryConfigTag.FILTER_ITEM_PICKUP, filterItemPickup);
        jsonObject.addProperty(MurderMysteryConfigTag.SURVIVOR_ITEM_TAG, survivorItemTag);
        jsonObject.addProperty(MurderMysteryConfigTag.MURDERER_ITEM_TAG, murdererItemTag);
        jsonObject.addProperty(MurderMysteryConfigTag.SURVIVOR_DELAY, survivorDelay);
        jsonObject.add(MurderMysteryConfigTag.SURVIVOR_FUNCS, JsonUtils.writeIntListToJson(survivorFuncs));
        jsonObject.addProperty(MurderMysteryConfigTag.DETECTIVE_DELAY, detectiveDelay);
        jsonObject.add(MurderMysteryConfigTag.DETECTIVE_FUNCS, JsonUtils.writeIntListToJson(detectiveFuncs));
        jsonObject.addProperty(MurderMysteryConfigTag.MURDERER_DELAY, murdererDelay);
        jsonObject.add(MurderMysteryConfigTag.MURDERER_FUNCS, JsonUtils.writeIntListToJson(murdererFuncs));
        jsonObject.addProperty(MurderMysteryConfigTag.API_FUNCTION_REGISTER, apiFunctionRegister);
        jsonObject.addProperty(MurderMysteryConfigTag.API_FUNCTION_UNREGISTER, apiFunctionUnregister);
        return jsonObject;
    }

    @NotNull
    public static MurdermysteryEntry fromJson(JsonObject jsonObject) {
        int initialDelay = JsonUtils.getJsonInt(jsonObject, MurderMysteryConfigTag.GAME_START_TICK, 15 * 20);
        int countdownSeconds = JsonUtils.getJsonInt(jsonObject, MurderMysteryConfigTag.COUNTDOWN_SECONDS, 10);
        int surviveTimeGoal = JsonUtils.getJsonInt(jsonObject, MurderMysteryConfigTag.SURVIVE_TIME_GOAL, 10 * 60 * 20);
        boolean sendGamePlayerNotificationMessage = JsonUtils.getJsonBool(jsonObject, MurderMysteryConfigTag.SEND_GAME_PLAYER_NOTIFICATION_MESSAGE, false);

        boolean filterItemPickup = JsonUtils.getJsonBool(jsonObject, MurderMysteryConfigTag.FILTER_ITEM_PICKUP, true);
        String survivorItemTag = JsonUtils.getJsonString(jsonObject, MurderMysteryConfigTag.SURVIVOR_ITEM_TAG, DEFAULT_SURVIVOR_ITEM_TAG);
        String murderItemTag = JsonUtils.getJsonString(jsonObject, MurderMysteryConfigTag.MURDERER_ITEM_TAG, DEFAULT_MURDERER_ITEM_TAG);

        int survivorDelay = JsonUtils.getJsonInt(jsonObject, MurderMysteryConfigTag.SURVIVOR_DELAY, 0);
        List<Integer> survivorFuncs = JsonUtils.getJsonIntList(jsonObject, MurderMysteryConfigTag.SURVIVOR_FUNCS);
        int detectiveDelay = JsonUtils.getJsonInt(jsonObject, MurderMysteryConfigTag.DETECTIVE_DELAY, 200);
        List<Integer> detectiveFuncs = JsonUtils.getJsonIntList(jsonObject, MurderMysteryConfigTag.DETECTIVE_FUNCS);
        int murderDelay = JsonUtils.getJsonInt(jsonObject, MurderMysteryConfigTag.MURDERER_DELAY, 200);
        List<Integer> murderFuncs = JsonUtils.getJsonIntList(jsonObject, MurderMysteryConfigTag.MURDERER_FUNCS);

        String apiFunctionRegister = JsonUtils.getJsonString(jsonObject, MurderMysteryConfigTag.API_FUNCTION_REGISTER, DEFAULT_REGISTER_FUNCTION);
        String apiFunctionUnregister = JsonUtils.getJsonString(jsonObject, MurderMysteryConfigTag.API_FUNCTION_UNREGISTER, DEFAULT_UNREGISTER_FUNCTION);

        return new MurdermysteryEntry(initialDelay, countdownSeconds, surviveTimeGoal, sendGamePlayerNotificationMessage,
                filterItemPickup, survivorItemTag, murderItemTag,
                survivorDelay, survivorFuncs,
                detectiveDelay, detectiveFuncs,
                murderDelay, murderFuncs,
                apiFunctionRegister, apiFunctionUnregister
        );
    }
}