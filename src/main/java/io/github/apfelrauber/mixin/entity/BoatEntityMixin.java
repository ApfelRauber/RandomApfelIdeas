package io.github.apfelrauber.mixin.entity;

import io.github.apfelrauber.RandomIdeasMain;
import io.github.apfelrauber.util.IEntityDataSaver;
import io.github.apfelrauber.util.OwnerData;
import io.github.apfelrauber.util.PlacedBoatsData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.UUID;

@Mixin(BoatEntity.class)
public class BoatEntityMixin extends Entity {

    public BoatEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    protected void initDataTracker() {

    }

    @Shadow
    protected void readCustomDataFromNbt(NbtCompound nbt) {

    }

    @Shadow
    protected void writeCustomDataToNbt(NbtCompound nbt) {

    }

    @Override
    public void remove(RemovalReason reason) {
        UUID ownerUUID = OwnerData.getOwnerUUID((IEntityDataSaver) ((BoatEntity)(Object)this));
        PlayerEntity owner = ((BoatEntity)(Object)this).getWorld().getPlayerByUuid(ownerUUID);

        PlacedBoatsData.removeSpecificBoatFromOwner((IEntityDataSaver) owner, ((BoatEntity)(Object)this), (ServerWorld) ((BoatEntity)(Object)this).getWorld());

        ((BoatEntity)(Object)this).setRemoved(reason);
    }
}
