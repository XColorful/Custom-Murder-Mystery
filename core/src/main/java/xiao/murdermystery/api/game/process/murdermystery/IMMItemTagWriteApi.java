package xiao.murdermystery.api.game.process.murdermystery;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

public interface IMMItemTagWriteApi {

    void addSurvivorTag(ItemStack itemStack);
    default void addSurvivorTag(ItemEntity itemEntity) {
        addSurvivorTag(itemEntity.getItem());
    }
    void addMurdererTag(ItemStack itemStack);
    default void addMurdererTag(ItemEntity itemEntity) {
        addMurdererTag(itemEntity.getItem());
    }

    void removeSurvivorTag(ItemStack itemStack);
    default void removeSurvivorTag(ItemEntity itemEntity) {
        removeSurvivorTag(itemEntity.getItem());
    }
    void removeMurdererTag(ItemStack itemStack);
    default void removeMurdererTag(ItemEntity itemEntity) {
        removeMurdererTag(itemEntity.getItem());
    }
}
