package xiao.murdermystery.common.game.process.murdermystery;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import xiao.battleroyale.BattleRoyale;
import xiao.battleroyale.api.game.IGameIdWriteApi;
import xiao.battleroyale.api.game.IGameManager;
import xiao.murdermystery.api.game.process.murdermystery.IMurderMysteryProcessManager;

import java.util.UUID;

public class _MMHeadMaker {

    protected static boolean lootSurvivorHead(IMurderMysteryProcessManager manager, @NotNull ServerLevel serverLevel, @NotNull LivingEntity livingEntity, Vec3 lootPos) {
        ItemEntity itemEntity = lootHeadItemEntity(BattleRoyale.getGameManager(), serverLevel, livingEntity, lootPos);
        manager.getItemTagApi().addSurvivorTag(itemEntity);
        return serverLevel.addFreshEntity(itemEntity);
    }

    protected static boolean lootMurdererHead(IMurderMysteryProcessManager manager, @NotNull ServerLevel serverLevel, @NotNull LivingEntity livingEntity, Vec3 lootPos) {
        ItemEntity itemEntity = lootHeadItemEntity(BattleRoyale.getGameManager(), serverLevel, livingEntity, lootPos);
        manager.getItemTagApi().addMurdererTag(itemEntity);
        return serverLevel.addFreshEntity(itemEntity);
    }

    private static ItemEntity lootHeadItemEntity(IGameManager gameManager, @NotNull ServerLevel serverLevel, @NotNull LivingEntity livingEntity, Vec3 lootPos) {
        IGameIdWriteApi gameIdWriteApi = gameManager.getGameIdWriteApi();
        UUID gameId = gameManager.getGameId();
        // 头颅物品
        ItemStack headItem = lootHeadItem(livingEntity);
        gameIdWriteApi.addGameId(headItem, gameId);
        // 头颅物品实体
        ItemEntity itemEntity = new ItemEntity(serverLevel, lootPos.x, lootPos.y, lootPos.z, headItem);
        gameIdWriteApi.addGameId(itemEntity, gameId);
        return itemEntity;
    }

    private static ItemStack lootHeadItem(@NotNull LivingEntity livingEntity) {
        ItemStack headItem = new ItemStack(Items.PLAYER_HEAD);
        String name;
        // 玩家头颅
        if (livingEntity instanceof ServerPlayer serverPlayer) {
            name = serverPlayer.getGameProfile().getName();
        } else {
            name = livingEntity.getName().getString();
        }
        CompoundTag tag = headItem.getOrCreateTag();
        tag.putString("SkullOwner", name);
        return headItem;
    }
}
