package xiao.murdermystery.api.game.process.murdermystery;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

public interface IMMItemTagReadApi {

    boolean isSurvivorItem(ItemStack itemStack);
    default boolean isSurvivorItem(ItemEntity itemEntity) {
        return isSurvivorItem(itemEntity.getItem());
    }
    boolean isMurderItem(ItemStack itemStack);
    default boolean isMurderItem(ItemEntity itemEntity) {
        return isMurderItem(itemEntity.getItem());
    }
}
