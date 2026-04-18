package xiao.murdermystery.common.game.process.murdermystery;

import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
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
    protected String MURDER_TAG_NAME = "murderItem";

    @Override public void setSurvivorTag(String survivorTagName) {
        this.SURVIVOR_TAG_NAME = survivorTagName;
    }
    @Override public void setMurderTag(String murderTagName) {
        this.MURDER_TAG_NAME = murderTagName;
    }

    @Override public boolean isSurvivorItem(ItemStack itemStack) {
        if (itemStack.hasTag()) {
            CompoundTag tag = itemStack.getTag();
            return tag != null && tag.contains(SURVIVOR_TAG_NAME);
        }
        return false;
    }
    @Override public boolean isMurderItem(ItemStack itemStack) {
        if (itemStack.hasTag()) {
            CompoundTag tag = itemStack.getTag();
            return tag != null && tag.contains(MURDER_TAG_NAME)
                    && !isSurvivorItem(itemStack);
        }
        return false;
    }

    @Override public void addSurvivorTag(ItemStack itemStack) {
        itemStack.getOrCreateTag().put(SURVIVOR_TAG_NAME,
                ByteTag.valueOf(true)
        );
        removeMurderTag(itemStack);
    }
    @Override public void addMurderTag(ItemStack itemStack) {
        itemStack.getOrCreateTag().put(MURDER_TAG_NAME,
                ByteTag.valueOf(true)
        );
        removeSurvivorTag(itemStack);
    }

    @Override public void removeSurvivorTag(ItemStack itemStack) {
        itemStack.getOrCreateTag().remove(SURVIVOR_TAG_NAME);
    }
    @Override public void removeMurderTag(ItemStack itemStack) {
        itemStack.getOrCreateTag().remove(MURDER_TAG_NAME);
    }
}
