package xiao.murdermystery.config.common.game.gamerule.custom;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xiao.battleroyale.api.config.common.game.gamerule.IGameruleEntry;
import xiao.murdermystery.api.config.common.game.gamerule.custom.MurderMysteryConfigTag;
import xiao.battleroyale.util.JsonUtils;

import java.util.ArrayList;
import java.util.List;

public class MurdermysteryEntry implements IGameruleEntry {

    public boolean filterItemPickup;
    public String survivorItemTag;
    public static final String DEFAULT_SURVIVOR_ITEM_TAG = "survivorItem";
    public String murderItemTag;
    public static final String DEFAULT_MURDER_ITEM_TAG = "murderItem";
    public @NotNull List<Integer> survivorFuncs;
    public int detectiveDelay;
    public @NotNull List<Integer> detectiveFuncs;
    public int murderDelay;
    public @NotNull List<Integer> murderFuncs;

    public MurdermysteryEntry() {
        this(true, DEFAULT_SURVIVOR_ITEM_TAG, DEFAULT_MURDER_ITEM_TAG,
                null,
                20 * 10, null,
                20 * 10, null);
    }
    public MurdermysteryEntry(boolean filterItemPickup, String survivorItemTag, String murderItemTag,
                              @Nullable List<Integer> survivorFuncs,
                              int detectiveDelay, @Nullable List<Integer> detectiveFuncs,
                              int murderDelay, @Nullable List<Integer> murderFuncs) {
        this.filterItemPickup = filterItemPickup;
        this.survivorItemTag = survivorItemTag;
        this.murderItemTag = murderItemTag;
        this.survivorFuncs = survivorFuncs != null ? survivorFuncs : new ArrayList<>();
        this.detectiveDelay = Math.max(0, detectiveDelay);
        this.detectiveFuncs = detectiveFuncs != null ? detectiveFuncs : new ArrayList<>();
        this.murderDelay = Math.max(0, murderDelay);
        this.murderFuncs = murderFuncs != null ? murderFuncs : new ArrayList<>();
    }
    @Override public @NotNull MurdermysteryEntry copy() {
        return new MurdermysteryEntry(filterItemPickup, survivorItemTag, murderItemTag,
                new ArrayList<>(survivorFuncs),
                detectiveDelay, new ArrayList<>(detectiveFuncs),
                murderDelay, new ArrayList<>(murderFuncs));
    }

    @Override
    public String getType() {
        return "murdermysteryEntry";
    }

    @Override
    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(MurderMysteryConfigTag.FILTER_ITEM_PICKUP, filterItemPickup);
        jsonObject.addProperty(MurderMysteryConfigTag.SURVIVOR_ITEM_TAG, survivorItemTag);
        jsonObject.addProperty(MurderMysteryConfigTag.MURDER_ITEM_TAG, murderItemTag);
        jsonObject.add(MurderMysteryConfigTag.SURVIVOR_FUNCS, JsonUtils.writeIntListToJson(survivorFuncs));
        jsonObject.addProperty(MurderMysteryConfigTag.DETECTIVE_DELAY, detectiveDelay);
        jsonObject.add(MurderMysteryConfigTag.DETECTIVE_FUNCS, JsonUtils.writeIntListToJson(detectiveFuncs));
        jsonObject.addProperty(MurderMysteryConfigTag.MURDER_DELAY, murderDelay);
        jsonObject.add(MurderMysteryConfigTag.MURDER_FUNCS, JsonUtils.writeIntListToJson(murderFuncs));
        return jsonObject;
    }

    @NotNull
    public static MurdermysteryEntry fromJson(JsonObject jsonObject) {
        boolean filterItemPickup = JsonUtils.getJsonBool(jsonObject, MurderMysteryConfigTag.FILTER_ITEM_PICKUP, true);
        String survivorItemTag = JsonUtils.getJsonString(jsonObject, MurderMysteryConfigTag.SURVIVOR_ITEM_TAG, DEFAULT_SURVIVOR_ITEM_TAG);
        String murderItemTag = JsonUtils.getJsonString(jsonObject, MurderMysteryConfigTag.MURDER_ITEM_TAG, DEFAULT_MURDER_ITEM_TAG);

        List<Integer> survivorFuncs = JsonUtils.getJsonIntList(jsonObject, MurderMysteryConfigTag.SURVIVOR_FUNCS);
        int detectiveDelay = JsonUtils.getJsonInt(jsonObject, MurderMysteryConfigTag.DETECTIVE_DELAY, 200);
        List<Integer> detectiveFuncs = JsonUtils.getJsonIntList(jsonObject, MurderMysteryConfigTag.DETECTIVE_FUNCS);
        int murderDelay = JsonUtils.getJsonInt(jsonObject, MurderMysteryConfigTag.MURDER_DELAY, 200);
        List<Integer> murderFuncs = JsonUtils.getJsonIntList(jsonObject, MurderMysteryConfigTag.MURDER_FUNCS);

        return new MurdermysteryEntry(filterItemPickup, survivorItemTag, murderItemTag,
                survivorFuncs, detectiveDelay, detectiveFuncs, murderDelay, murderFuncs
        );
    }
}