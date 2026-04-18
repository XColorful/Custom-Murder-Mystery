package xiao.murdermystery.api.game.process.murdermystery;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

public interface IMMItemTagWriteApi {

    void addSurvivorTag(ItemStack itemStack);
    default void addSurvivorTag(ItemEntity itemEntity) {
        addSurvivorTag(itemEntity.getItem());
    }
    void addMurderTag(ItemStack itemStack);
    default void addMurderTag(ItemEntity itemEntity) {
        addMurderTag(itemEntity.getItem());
    }

    void removeSurvivorTag(ItemStack itemStack);
    default void removeSurvivorTag(ItemEntity itemEntity) {
        removeSurvivorTag(itemEntity.getItem());
    }
    void removeMurderTag(ItemStack itemStack);
    default void removeMurderTag(ItemEntity itemEntity) {
        removeMurderTag(itemEntity.getItem());
    }
}
