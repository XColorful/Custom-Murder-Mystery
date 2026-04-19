package xiao.murdermystery.api.game.process.murdermystery;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

public interface IMMItemTagReadApi {

    boolean isSurvivorItem(ItemStack itemStack);
    default boolean isSurvivorItem(ItemEntity itemEntity) {
        return isSurvivorItem(itemEntity.getItem());
    }
    boolean isMurdererItem(ItemStack itemStack);
    default boolean isMurdererItem(ItemEntity itemEntity) {
        return isMurdererItem(itemEntity.getItem());
    }
}
