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
    protected String MURDERER_TAG_NAME = "murdererItem";

    @Override public void setSurvivorTag(String survivorTagName) {
        this.SURVIVOR_TAG_NAME = survivorTagName;
    }
    @Override public void setMurdererTag(String murdererTagName) {
        this.MURDERER_TAG_NAME = murdererTagName;
    }

    @Override public boolean isSurvivorItem(ItemStack itemStack) {
        if (itemStack.hasTag()) {
            CompoundTag tag = itemStack.getTag();
            return tag != null && tag.contains(SURVIVOR_TAG_NAME);
        }
        return false;
    }
    @Override public boolean isMurdererItem(ItemStack itemStack) {
        if (itemStack.hasTag()) {
            CompoundTag tag = itemStack.getTag();
            return tag != null && tag.contains(MURDERER_TAG_NAME)
                    && !isSurvivorItem(itemStack);
        }
        return false;
    }

    @Override public void addSurvivorTag(ItemStack itemStack) {
        itemStack.getOrCreateTag().put(SURVIVOR_TAG_NAME,
                ByteTag.valueOf(true)
        );
        removeMurdererTag(itemStack);
    }
    @Override public void addMurdererTag(ItemStack itemStack) {
        itemStack.getOrCreateTag().put(MURDERER_TAG_NAME,
                ByteTag.valueOf(true)
        );
        removeSurvivorTag(itemStack);
    }

    @Override public void removeSurvivorTag(ItemStack itemStack) {
        itemStack.getOrCreateTag().remove(SURVIVOR_TAG_NAME);
    }
    @Override public void removeMurdererTag(ItemStack itemStack) {
        itemStack.getOrCreateTag().remove(MURDERER_TAG_NAME);
    }
}
