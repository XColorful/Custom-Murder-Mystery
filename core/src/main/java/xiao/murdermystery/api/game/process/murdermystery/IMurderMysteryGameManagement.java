package xiao.murdermystery.api.game.process.murdermystery;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import xiao.battleroyale.api.game.process.IGameManagement;
import xiao.battleroyale.common.game.team.GamePlayer;

public interface IMurderMysteryGameManagement extends IGameManagement {

    @ApiStatus.Internal
    void onSetRoleTick(int gameTime);

    boolean setSurvivor(@NotNull GamePlayer gamePlayer);
    boolean setDetective(@NotNull GamePlayer gamePlayer);
    boolean setMurderer(@NotNull GamePlayer gamePlayer);

    boolean lootSurvivorHead(@NotNull ServerLevel serverLevel, @NotNull LivingEntity livingEntity, Vec3 lootPos);
    boolean lootMurdererHead(@NotNull ServerLevel serverLevel, @NotNull LivingEntity livingEntity, Vec3 lootPos);
}
