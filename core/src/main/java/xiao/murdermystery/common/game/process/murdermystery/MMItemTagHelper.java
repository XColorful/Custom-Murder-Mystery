package xiao.murdermystery.common.game.process.murdermystery;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import xiao.murdermystery.api.game.process.murdermystery.IMMItemTagApi;

public class MMItemTagHelper implements IMMItemTagApi {

    private static class MMItemTagHelperHolder {
        private static final MMItemTagHelper INSTANCE = new MMItemTagHelper();
    }

    public static MMItemTagHelper get() {
        return MMItemTagHelperHolder.INSTANCE;
    }

    protected MMItemTagHelper() {}

    protected String SURVIVOR_TAG_NAME = "survivorItem";
    protected String MURDERER_TAG_NAME = "murdererItem";

    @Override public void setSurvivorTag(String survivorTagName) {
        this.SURVIVOR_TAG_NAME = survivorTagName;
    }
    @Override public void setMurdererTag(String murdererTagName) {
        this.MURDERER_TAG_NAME = murdererTagName;
    }

    @Override public boolean isSurvivorItem(ItemStack itemStack) {
        CustomData customData = itemStack.get(DataComponents.CUSTOM_DATA);
        if (customData != null) {
            CompoundTag tag = customData.copyTag();
            return tag.contains(SURVIVOR_TAG_NAME);
        }
        return false;
    }
    @Override public boolean isMurdererItem(ItemStack itemStack) {
        CustomData customData = itemStack.get(DataComponents.CUSTOM_DATA);
        if (customData != null) {
            CompoundTag tag = customData.copyTag();
            return tag.contains(MURDERER_TAG_NAME);
        }
        return false;
    }

    @Override public void addSurvivorTag(ItemStack itemStack) {
        CustomData customData = itemStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        CompoundTag tag = customData.copyTag();
        tag.put(SURVIVOR_TAG_NAME, ByteTag.valueOf(true));
        itemStack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
        removeMurdererTag(itemStack);
    }
    @Override public void addMurdererTag(ItemStack itemStack) {
        CustomData customData = itemStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        CompoundTag tag = customData.copyTag();
        tag.put(MURDERER_TAG_NAME, ByteTag.valueOf(true));
        itemStack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
        removeSurvivorTag(itemStack);
    }

    @Override public void removeSurvivorTag(ItemStack itemStack) {
        CustomData customData = itemStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        CompoundTag tag = customData.copyTag();
        tag.remove(SURVIVOR_TAG_NAME);
        itemStack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
    }
    @Override public void removeMurdererTag(ItemStack itemStack) {
        CustomData customData = itemStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        CompoundTag tag = customData.copyTag();
        tag.remove(MURDERER_TAG_NAME);
        itemStack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
    }
}
